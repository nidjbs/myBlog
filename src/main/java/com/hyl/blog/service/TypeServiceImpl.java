package com.hyl.blog.service;

import com.hyl.blog.dao.BlogRepository;
import com.hyl.blog.dao.TypeRepository;
import com.hyl.blog.exception.NotFoundException;
import com.hyl.blog.pojo.Blog;
import com.hyl.blog.pojo.Type;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @CacheEvict(value = "typeTop",allEntries = true)
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @CacheEvict(value = "typeTop",allEntries = true)
    @Override
    public void deleteType(Type type) {
        typeRepository.delete(type);
    }

    @Override
    public Type findOneById(Integer id) {
        return typeRepository.getOne(id);
    }

    @Transactional
    @CacheEvict(value = "typeTop",allEntries = true)
    @Override
    public Type updateType(Type type, Integer id) {
        Type t = typeRepository.getOne(id);
        if (t == null) {
            throw new NotFoundException("not found typeId!");
        } else {
            BeanUtils.copyProperties(type, t);
            return typeRepository.save(t);
        }
    }

    @Override
    public Page listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public Type findByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    public List<Type> allTypes() {
        return typeRepository.findAll();
    }

    @Cacheable(value = "typeTop",key = "'type'+ #size",unless = "#result == null")
    @Override
    public List<Type> listTypeTop(Integer size) {
        List<Type> types = typeRepository.findTop(size);
        for(Type type : types){
            type.setCountpb(typeRepository.countPublishBlogByTypeId(type.getId()));
        }
        return types;
    }

    @Override
    public Page<Blog> findBlogListByTypeId(Pageable pageable, Integer id) {
        return blogRepository.findBlogListByTypeId(id,pageable);
    }

    @Override
    public List<Type> allLabelsAndOrderByCountBlog() {
        List<Type> list = typeRepository.findAllAndBlogPublish();
        Integer index = 1;
        for(Type type : list){
            type.setCountpb(typeRepository.countPublishBlogByTypeId(type.getId()));
            type.setIndex(index++);
        }
        return list;
    }

    @Override
    public Integer countPublishBlogByTypeId(Integer id) {
        return typeRepository.countPublishBlogByTypeId(id);
    }
}
