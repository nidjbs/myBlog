package com.hyl.blog.service;

import com.hyl.blog.pojo.Blog;
import com.hyl.blog.pojo.Type;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Type saveType(Type type);

    void deleteType(Type type);

    Type findOneById(Integer id);

    Type updateType(Type type, Integer id);

    Page<Type> listType(Pageable pageable);

    Type findByName(String name);

    List<Type> allTypes();

    List<Type> listTypeTop(Integer size);

    Page<Blog> findBlogListByTypeId(Pageable pageable, Integer id);

    List<Type> allLabelsAndOrderByCountBlog();

    Integer countPublishBlogByTypeId(Integer id);

}
