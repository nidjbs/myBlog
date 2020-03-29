package com.hyl.blog.service;

import com.hyl.blog.dao.BlogContentRepository;
import com.hyl.blog.dao.BlogRepository;
import com.hyl.blog.exception.NotFoundException;
import com.hyl.blog.pojo.Blog;
import com.hyl.blog.pojo.BlogContent;
import com.hyl.blog.pojo.Label;
import com.hyl.blog.pojo.Type;
import com.hyl.blog.util.MarkdownUtils;
import com.hyl.blog.vo.BlogQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private BlogContentRepository contentRepository;

    @Transactional
    @CacheEvict(value = {"blogTop"},allEntries = true)
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() != null) {//更新操作;
            return updateBlog(blog, blog.getId());
        } else {//新增操作
            blog.setUpdateTime(new Date());
            blog.setViewedCount(0);
            blog.setCreationTime(new Date());
            return blogRepository.save(blog);
        }
    }

    @Transactional
    @CacheEvict(value = {"blogTop","blogContent"},allEntries = true)
    @Override
    public void deleteBlog(Integer id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Blog findOneById(Integer id) {
        return blogRepository.getOne(id);
    }

    @CacheEvict(value = {"blogContent"},allEntries = true)
    @Override
    public Blog updateBlog(Blog blog, Integer id) {
        blog.setUpdateTime(new Date());
        Blog b = blogRepository.getOne(id);
        if (b == null) throw new NotFoundException("该博客不存在！");
        blog.setViewedCount(b.getViewedCount());
        blog.setCreationTime(b.getCreationTime());
        return blogRepository.save(blog);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery, Boolean isSearch) {
        if (blogQuery != null && isSearch) {
            return blogRepository.findAll(new Specification<Blog>() {
                //指定条件查询（可动态拼接非空数据）
                //root: 指定的查询类型，cq :执行查询的对象,cp :需要添加查询条件的对象
                @Override
                public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<>();
                    //指定需要添加的条件
                    if (!"".equals(blogQuery.getTitle()) && blogQuery.getTitle() != null)//标题
                        predicates.add(cb.like(root.<String>get("title"), "%" + blogQuery.getTitle() + "%"));
                    if (blogQuery.getTypeId() != null)//类型
                        predicates.add(cb.equal(root.<Type>get("type").get("id"), blogQuery.getTypeId()));
                    if (blogQuery.getRecommend() != null)//是否推荐
                        predicates.add(cb.equal(root.<Boolean>get("recommend"), blogQuery.getRecommend()));
                    cq.where(predicates.toArray(new Predicate[predicates.size()]));
                    return null;
                }
            }, pageable);
        } else {
            return blogRepository.findAll(pageable);
        }
    }

    @Cacheable(value = "blogSort",key = "'PageBlog:' + #page")
    @Override
    public Page<Blog> listBlog(Pageable pageable,Integer page) {
        return blogRepository.findAllPublish(pageable);
    }

//    @Override
//    public Page<Blog> listBlog(Pageable pageable, Integer labelId) {
//            return blogRepository.findBlogListByLabelId(labelId,pageable);
//    }

    //推荐的最新size条数据
    @Cacheable(value = "blogTop",key = "'recommendedBlogTop'",unless = "#result == null")
    @Override
    public List<Blog> listBlogTop(Integer size) {
        return blogRepository.findTop(PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "creationTime")));
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery("%"+query+"%",pageable);
    }

    @Override
    public Map<String, List<Blog>> archivesBlog() {
        List<String> years = blogRepository.findCroupYear();
        Map<String, List<Blog>> listMap = new TreeMap<>((o1, o2) -> o2.hashCode()-o1.hashCode());
        for(String year : years){
            listMap.put(year,findByYear(year));
        }
        return listMap;
    }

    @Override
    public List<String> findCroupYear() {
        return blogRepository.findCroupYear();
    }

    @Override
    public List<Blog> findByYear(String year) {
        return blogRepository.findByYear(year);
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Cacheable(value = "blogContent",key = "'blog-id:' + #id + '-content' ",unless = "#result.blog.viewedCount < 5")
    @Override
    public BlogContent getAndConvert(Integer id) {
        BlogContent blogContent = contentRepository.findBlogContentByBlog(id);
        if(blogContent == null) throw new  NotFoundException("the blog is not found!");
        BlogContent blogContent1 = new BlogContent();
        blogContent1.setContent(MarkdownUtils.markdownToHtmlExtensions(blogContent.getContent()));
        blogContent1.setContentId(blogContent.getContentId());
        blogContent1.setBlog(blogContent.getBlog());
        return blogContent1;
    }

    @Transactional
    @Override
    public Blog updateViewCount(Integer id) {
        Blog blog = blogRepository.getOne(id);
        if (blog == null) throw new  NotFoundException("the blog is not found!");
        blog.setViewedCount(blog.getViewedCount()+1);
        return blogRepository.save(blog);
    }
}
