package com.hyl.blog.web.admin;

import static org.springframework.data.domain.Sort.by;

import java.util.ArrayList;
import java.util.List;

import com.hyl.blog.pojo.Blog;
import com.hyl.blog.pojo.BlogContent;
import com.hyl.blog.pojo.User;
import com.hyl.blog.service.BlogContentService;
import com.hyl.blog.service.BlogService;
import com.hyl.blog.service.LabelService;
import com.hyl.blog.service.TypeService;
import com.hyl.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private BlogContentService blogContentService;
    @GetMapping
    public String blogs(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable,BlogQuery blogQuery,Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery,false));
        model.addAttribute("types",typeService.allTypes());
        return "admin/blogs";
    }
    @GetMapping("/add")
    public String add(Model model){
       return hasError(model);
    }
    @PostMapping("/doAdd")
    public String doAdd(@Valid Blog blog,@Valid BlogContent blogContent,BindingResult result, HttpSession session, RedirectAttributes attributes, Model model){
        if(result.hasErrors()){
           return hasError(model);
        }
        blog.setUser((User)session.getAttribute("user"));
        blog.setType(typeService.findOneById(blog.getType().getId()));
        blog.setLabelList(labelService.findLabelsByIds(blog.getLabelIds()));
        blogContent.setBlog(blog);
        BlogContent b = blogContentService.save(blogContent);
        if(b==null){ attributes.addFlashAttribute("message","add blog failed !");
        return "redirect:/admin/blogs/add";
        }else{
            attributes.addFlashAttribute("message","add blog success !");
            return  "redirect:/admin/blogs";
        }
    }
    @GetMapping("/{id}/update")
    public String update(@PathVariable Integer id, Model model){
        Blog blog = blogService.findOneById(id);
        blog.init();
        model.addAttribute("blogContent",blogContentService.findOne(blog.getId()));
        model.addAttribute("types",typeService.allTypes());
        model.addAttribute("blog",blog);
        model.addAttribute("labels",labelService.allLabels());
        return "/admin/blogs_input";
    }
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8,sort ={"updateTime"},direction = Sort.Direction.DESC)
                                     Pageable pageable, BlogQuery blogQuery,Boolean isSearch,Model model){
        if(blogQuery.getTypeName()!=null && !"".equals(blogQuery.getTypeName())){
            blogQuery.setTypeId(typeService.findByName(blogQuery.getTypeName()).getId());
        }
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery,isSearch));
        return "/admin/blogs :: blogList";
    }
    @ResponseBody
    @PostMapping("/delete")
    public String delete(Integer id){
        try{
            blogService.deleteBlog(id);
        }catch (Exception e){
            return "delete fail!";
        }
        return "ok";
    }

    public String hasError(Model model){
        model.addAttribute("blog",new Blog());
        model.addAttribute("blogContent",new BlogContent());
        model.addAttribute("types",typeService.allTypes());
        model.addAttribute("labels",labelService.allLabels());
        return "/admin/blogs_input";
    }
}
