package com.hyl.blog.service;

import com.hyl.blog.pojo.Blog;
import com.hyl.blog.pojo.Label;
import com.hyl.blog.pojo.Label;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface LabelService {
    Label saveLabel(Label Label);

    void deleteLabel(Label Label);

    Label findOneById(Integer id);

    Label updateLabel(Label Label, Integer id);

    Page<Label> listLabel(Pageable pageable);

    Label findByName(String name);

    List<Label> allLabels();

    List<Label> allLabelsAndOrderByCountBlog();

    List<Label> findLabelsByIds(String labelIds);

    List<Label> findLabelTop(Integer size);

    Page<Blog> findBlogListByLabelId(Pageable pageable,Integer id);

    Integer countPublishBlogByLabelId(Integer id);
}
