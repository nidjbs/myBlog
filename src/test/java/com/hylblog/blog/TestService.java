package com.hylblog.blog;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.hyl.blog.BlogApplication;
import com.hyl.blog.dao.BlogRepository;
import com.hyl.blog.pojo.Type;
import com.hyl.blog.service.BlogContentService;
import com.hyl.blog.service.BlogService;
import com.hyl.blog.service.LabelService;
import com.hyl.blog.service.TypeService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApplication.class)
public class TestService {
    @Autowired
    private TypeService typeService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogContentService blogContentService;

    @Test
    public void test(){
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(0,2,sort);
//        System.out.println(typeService.findBlogList(pageable,6).getTotalPages());
        //System.out.println(typeService.listTypeTop(10).size());
      // System.out.println(labelService.findLabelTop(10).size());
        //System.out.println(typeService.findBlogListByTypeId(pageable,6));
      //  System.out.println(labelService.findBlogListByLabelId(pageable,25).getTotalElements());
//        while (iterator.hasNext()){
//            System.out.println(((Type)(iterator.next())).getName());
//        }
       // System.out.println(typeService.allLabelsAndOrderByCountBlog().size());
        //System.out.println(blogContentService.findOne(148).getBlog().getLabelList().size());
        System.out.println(blogService.getAndConvert(148).getBlog().getLabelList().size());
    }
}
