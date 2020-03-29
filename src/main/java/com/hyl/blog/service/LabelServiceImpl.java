package com.hyl.blog.service;

import com.hyl.blog.dao.BlogRepository;
import com.hyl.blog.dao.LabelRepository;
import com.hyl.blog.exception.IllegalOperationException;
import com.hyl.blog.pojo.Blog;
import com.hyl.blog.pojo.Label;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class LabelServiceImpl implements LabelService {
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @CacheEvict(value = "labelTop",allEntries = true)
    @Override
    public Label saveLabel(Label Label) {
        return labelRepository.save(Label);
    }

    @Transactional
    @CacheEvict(value = "labelTop",allEntries = true)
    @Override
    public void deleteLabel(Label Label) {
        labelRepository.delete(Label);
    }

    @Override
    public Label findOneById(Integer id) {
        return labelRepository.getOne(id);
    }

    @Transactional
    @CacheEvict(value = "labelTop",allEntries = true)
    @Override
    public Label updateLabel(Label Label, Integer id) {
        Label t = labelRepository.getOne(id);
        if (t == null) {
            throw new IllegalOperationException();
        } else {
            BeanUtils.copyProperties(Label, t);
            return labelRepository.save(t);
        }
    }

    @Override
    public Page listLabel(Pageable pageable) {
        return labelRepository.findAll(pageable);
    }

    @Override
    public Label findByName(String name) {
        return labelRepository.findByName(name);
    }

    @Override
    public List<Label> allLabels() {
        return labelRepository.findAll();
    }

    @Override
    public List<Label> allLabelsAndOrderByCountBlog() {
        List<Label> labels = labelRepository.findAllAndBlogPublish();
       countBlog(labels);
        return labels;
    }

    private void countBlog(List<Label> labels){
        Integer index = 1;
        for(Label label : labels){
            label.setCountBlog(labelRepository.countPublishBlogByLabelId(label.getId()));
            label.setIndex(index++);
        }
    }

    @Override
    public List<Label> findLabelsByIds(String labelIds) {
        return labelRepository.findAllById(stringToIds(labelIds));
    }

    @Cacheable(value = "labelTop",key = "'label'", unless = "#result == null")
    @Override
    public List<Label> findLabelTop(Integer size) {
        List<Label> labels = labelRepository.findTop(size);
        countBlog(labels);
        return labels;
    }

    @Override
    public Page<Blog> findBlogListByLabelId(Pageable pageable, Integer id) {

        return blogRepository.findBlogListByLabelId(id,pageable);
    }

    @Override
    public Integer countPublishBlogByLabelId(Integer id) {
        return labelRepository.countPublishBlogByLabelId(id);
    }

    private List<Integer> stringToIds(String ids) {
        List<Integer> integerList = new ArrayList<>();
        String[] strings = ids.split(",");
        if (!"".equals(ids) && ids != null) {
            for (int i = 0; i < strings.length; i++) {
                integerList.add(Integer.parseInt(strings[i]));
            }
        }
        return integerList;
    }

}
