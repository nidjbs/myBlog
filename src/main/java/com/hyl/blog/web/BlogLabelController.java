package com.hyl.blog.web;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hyl.blog.pojo.Blog;
import com.hyl.blog.pojo.Label;
import com.hyl.blog.service.LabelService;

@Controller
public class BlogLabelController {
    @Autowired
    private LabelService labelService;
    @GetMapping("/label")
    public String firstLabel(@PageableDefault(size = 8,direction =Sort.Direction.DESC,sort = "b.update_time")
                                    Pageable pageable, Model model){
        List<Label> allLabel = labelService.allLabelsAndOrderByCountBlog();
        model.addAttribute("labels",allLabel);
        model.addAttribute("labelId",allLabel.get(0).getId());
        model.addAttribute("labelIndex",1);
        Page<Blog> page = labelService.findBlogListByLabelId(pageable,allLabel.get(0).getId());
        model.addAttribute("page",page);
        return "labels";
    }

    @PostMapping("/ajaxlabel")
    public String label(@PageableDefault(size = 8,direction = Sort.Direction.DESC,sort = "b.update_time")
                                Pageable pageable,Integer id, Model model){
        model.addAttribute("labels",labelService.allLabels());
        model.addAttribute("page",labelService.findBlogListByLabelId(pageable,id));
        return "labels :: blogList";
    }
    @PostMapping("/label")
    public String index(@PageableDefault(size = 8,direction = Sort.Direction.DESC,sort = "b.update_time")
                                    Pageable pageable, Integer id,Model model ){
        List<Label> allLabel = labelService.allLabelsAndOrderByCountBlog();
        boolean haveIndex = false;
        for(Label label : allLabel){
            if(label.getId() == id){
                model.addAttribute("labelIndex",label.getIndex());
                haveIndex = true;
                break;
            }
        }
        if(!haveIndex){
            model.addAttribute("labelIndex",1);
        }
        model.addAttribute("labels",allLabel);
        model.addAttribute("labelId",id);
        model.addAttribute("page",labelService.findBlogListByLabelId(pageable,id));
        return "/labels";
    }
}
