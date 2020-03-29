package com.hyl.blog.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import com.hyl.blog.pojo.Type;
import com.hyl.blog.service.TypeService;

@Controller
public class BlogTypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/type")
    public String firstType(@PageableDefault(size = 8,direction = Sort.Direction.DESC,sort = "updateTime")
                                     Pageable pageable, Model model){
        List<Type> allType = typeService.allLabelsAndOrderByCountBlog();
        model.addAttribute("typeIndex",1);
        model.addAttribute("types",allType);
        model.addAttribute("typeId",allType.get(0).getId());
        model.addAttribute("page",typeService.findBlogListByTypeId(pageable,allType.get(0).getId()));
        return "types";
    }

    @PostMapping("/ajaxtype")
    public String type(@PageableDefault(size = 8,direction = Sort.Direction.DESC,sort = "updateTime")
                                Pageable pageable,Integer id, Model model){
        model.addAttribute("types",typeService.allLabelsAndOrderByCountBlog());
        model.addAttribute("page",typeService.findBlogListByTypeId(pageable,id));
        return "types :: blogList";
    }
    @PostMapping("/type")
    public String index(@PageableDefault(size = 8,direction = Sort.Direction.DESC,sort = "updateTime")
                                Pageable pageable, Integer id,Model model){
        System.out.println("重定向type");
        List<Type> allType = typeService.allLabelsAndOrderByCountBlog();
        boolean haveIndex = false;
        for(Type type : allType){
            if(type.getId() == id){
                model.addAttribute("typeIndex",type.getIndex());
                haveIndex = true;
                break;
            }
        }
        if(!haveIndex){
            model.addAttribute("typeIndex",1);
        }
        model.addAttribute("types",allType);
        model.addAttribute("typeId",id);
        model.addAttribute("page",typeService.findBlogListByTypeId(pageable,id));
        return "types";
    }

}
