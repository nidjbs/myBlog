package com.hyl.blog.web.admin;

import com.hyl.blog.pojo.User;
import com.hyl.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public String adminToLogin(){
        return "/admin/login";
    }

    @GetMapping({"/login","/login.html"})
    public String login(){
        return "/admin/login";
    }
    @PostMapping("/doLogin")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          RedirectAttributes attributes,
                          Model model){
        User user = userService.checkUserByUsernameAndPassword(username,password);
        if(user!=null){
            user.setPassword(null);
            session.setAttribute("user",user);
            session.setMaxInactiveInterval(Integer.MAX_VALUE);
            //attributes.addFlashAttribute("user",user);
            return "redirect:/admin/blogs";
        }else {
            attributes.addFlashAttribute("message","username or password error！");
            return "redirect:/admin/login";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin/login";
    }
}
