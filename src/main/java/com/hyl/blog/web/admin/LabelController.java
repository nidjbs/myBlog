package com.hyl.blog.web.admin;

import com.hyl.blog.pojo.Label;
import com.hyl.blog.service.LabelService;
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

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/labels")
public class LabelController {
    @Autowired
    private LabelService labelService;

    //接收前端来的pageable对象如page=？和指定的排序规则封装进Page对象，返回分页信息Page类型对象
    @GetMapping
    public String labels(@PageableDefault(size = 12,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model){
        model.addAttribute("labelPage",labelService.listLabel(pageable));
        return "admin/labels";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("label",new Label());
        return "admin/labels_input";
    }

    @PostMapping("/doAdd")
    public String doAdd(@Valid Label label, BindingResult result, RedirectAttributes attributes) {
        return doOperate(label,result,attributes,null);
    }

    @PostMapping("/delete")
    @ResponseBody
    public String delete(Integer id){
        Label label = labelService.findOneById(id);
        if(label != null){
            try {
                labelService.deleteLabel(label);
            }catch (DataIntegrityViolationException e){
                return "this label has connected!";
            }
            return "ok";
        }else {
            return "illegal operation!";
        }
    }
    @GetMapping("/{id}/update")
    public String update(@PathVariable Integer id, Model model){
        model.addAttribute("label",labelService.findOneById(id));
        return "/admin/labels_input";
    }
    @PostMapping("/{id}/doUpdate")
    public String doUpdate(@Valid Label label, BindingResult result, @PathVariable Integer id, RedirectAttributes attributes) {
        return doOperate(label,result,attributes,id);
    }

    private String doOperate(Label label, BindingResult result, RedirectAttributes attributes,Integer id){
        if (result.hasErrors()) {
            return "/admin/labels_input";
        }
        Label label1 = labelService.findByName(label.getName());
        if (label1 != null) {
            result.rejectValue("name", "nameDuplicate", "Duplicate name!");
            return "/admin/labels_input";
        }
        Label label2 = null;
        if(id != null){//更新操作
            label2 = labelService.updateLabel(label,id);
        }else {
            label2 = labelService.saveLabel(label);
        }
        if (label2 != null) {
            attributes.addFlashAttribute("message", "Operation Success !");
        } else {
            attributes.addFlashAttribute("message", "Operation Fail !");
        }
        return "redirect:/admin/labels";
    }
}
