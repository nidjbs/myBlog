package com.hyl.blog.service;


import com.hyl.blog.pojo.BlogContent;

public interface BlogContentService {

    BlogContent save(BlogContent blogContent);

    BlogContent update(Integer id,BlogContent blogContent);

    BlogContent findOne(Integer id);

    void delete(Integer id);

}
