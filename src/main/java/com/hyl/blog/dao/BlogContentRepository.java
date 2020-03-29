package com.hyl.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hyl.blog.pojo.BlogContent;

public interface BlogContentRepository extends JpaSpecificationExecutor<BlogContent>, JpaRepository<BlogContent,Integer> {
    @Query("select c from BlogContent c where c.blog.id = ?1 ")
    BlogContent findBlogContentByBlog(Integer id);
}
