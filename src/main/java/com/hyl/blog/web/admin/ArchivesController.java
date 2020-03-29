package com.hyl.blog.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyl.blog.service.BlogService;

@Controller
@RequestMapping("/admin/archives")
public class ArchivesController {
    @Autowired
    private BlogService blogService;
    @GetMapping()
    public String archives(Model model){
        model.addAttribute("archivesBlogMap",blogService.archivesBlog());
        model.addAttribute("countBlog",blogService.countBlog());
        return "admin/archives";
    }
}
