package com.hyl.blog.web.admin;

import com.hyl.blog.pojo.Type;
import com.hyl.blog.service.TypeService;
import net.bytebuddy.TypeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;
import javax.validation.Valid;
import java.awt.*;

@Controller
@RequestMapping("/admin/types")
public class TypeController {
    @Autowired
    private TypeService typeService;

    //接收前端来的pageable对象如page=？和指定的排序规则封装进Page对象，返回分页信息Page类型对象
    @GetMapping
    public String types(@PageableDefault(size = 12,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("typePage",typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("type",new Type());
        return "admin/types_input";
    }

    @PostMapping("/doAdd")
    public String doAdd(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        return doOperate(type,result,attributes,null);
    }

    @PostMapping("/delete")
    @ResponseBody
    public String delete(Integer id){
        Type type = typeService.findOneById(id);
        if(type!=null){
            try{
                typeService.deleteType(type);
            }catch (DataIntegrityViolationException e){
                return "this type has connected!";
            }
            return "ok";
        }else {
            return "illegal operation!";
        }
    }
    @GetMapping("/{id}/update")
    public String update(@PathVariable Integer id, Model model){
        model.addAttribute("type",typeService.findOneById(id));
        return "/admin/types_input";
    }
    @PostMapping("/{id}/doUpdate")
    public String doUpdate(@Valid Type type, BindingResult result,@PathVariable Integer id, RedirectAttributes attributes) {
       return doOperate(type,result,attributes,id);
    }

    private String doOperate(Type type, BindingResult result, RedirectAttributes attributes,Integer id){
        if (result.hasErrors()) {
            return "/admin/types_input";
        }
        Type type1 = typeService.findByName(type.getName());
        System.out.println(type1);
        if (type1 != null) {
            result.rejectValue("name", "nameDuplicate", "Duplicate name!");
            return "/admin/types_input";
        }
        Type type2 = null;
        if(id != null){//更新操作
            type2 = typeService.updateType(type,id);
        }else {
            type2 = typeService.saveType(type);
        }
        if (type2 != null) {
            attributes.addFlashAttribute("message", "Operation Success !");
        } else {
            attributes.addFlashAttribute("message", "Operation Fail !");
        }
        return "redirect:/admin/types";
    }
}
