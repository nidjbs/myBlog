package com.hyl.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyl.blog.pojo.BlogContent;
import com.hyl.blog.service.BlogService;
import com.hyl.blog.service.LabelService;
import com.hyl.blog.service.TypeService;

@Controller
public class BlogIndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private LabelService labelService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 2,sort ={"viewedCount"},direction = Sort.Direction.DESC)
                                    Pageable pageable,@RequestParam(required = false) Integer page,Model model){
        if(page == null){page = 0;}
        model.addAttribute("page",blogService.listBlog(pageable,page));
        model.addAttribute("types",typeService.listTypeTop(5));
        model.addAttribute("labels",labelService.findLabelTop(5));
        model.addAttribute("recommendBlogs",blogService.listBlogTop(5));
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Integer id,Model model){
        BlogContent blogContent = blogService.getAndConvert(id);
        model.addAttribute("blogContent",blogContent);
        model.addAttribute("blog",blogService.updateViewCount(id));
        return "blog";
    }

    @PostMapping("/blog/search")
    public String search(@PageableDefault(size = 8,sort ={"viewedCount"},direction = Sort.Direction.DESC)
                                     Pageable pageable, Model model,@RequestParam String query){
        model.addAttribute("page",blogService.listBlog(query,pageable));
        model.addAttribute("query",query);
        return "search_result";
    }
}
