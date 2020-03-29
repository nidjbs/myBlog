package com.hyl.blog.service;

import java.util.List;
import java.util.Map;

import com.hyl.blog.pojo.Blog;
import com.hyl.blog.pojo.BlogContent;
import com.hyl.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Blog saveBlog(Blog blog);

    void deleteBlog(Integer id);

    Blog findOneById(Integer id);

    Blog updateBlog(Blog blog, Integer id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery, Boolean isSearch);

    Page<Blog> listBlog(Pageable pageable,Integer page);

    List<Blog> listBlogTop(Integer size);

    Page<Blog> listBlog(String query,Pageable pageable);

    Map<String,List<Blog>> archivesBlog();

    List<String> findCroupYear();

    List<Blog> findByYear(String year);

    Long countBlog();

    BlogContent getAndConvert(Integer id);

    Blog updateViewCount(Integer id);

}
