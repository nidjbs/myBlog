package com.hyl.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyl.blog.dao.BlogContentRepository;
import com.hyl.blog.pojo.BlogContent;

@Service
@CacheConfig
public class BlogContentServiceImpl implements BlogContentService {

    @Autowired
    private BlogContentRepository blogContentRepository;
    @Autowired
    private BlogService blogService;

    @Transactional
    @Override
    public BlogContent save(BlogContent blogContent) {
        blogService.saveBlog(blogContent.getBlog());
        return blogContentRepository.save(blogContent);
    }

    @Transactional
    @Override
    public BlogContent update(Integer id,BlogContent blogContent) {
        return blogContentRepository.save(blogContent);
    }

    @Override
    public BlogContent findOne(Integer id) {
        System.out.println("从数据库中获取博客内容...");
        return blogContentRepository.findBlogContentByBlog(id);
    }


    @Transactional
    @Override
    public void delete(Integer id) {
        blogContentRepository.deleteById(id);
    }
}
