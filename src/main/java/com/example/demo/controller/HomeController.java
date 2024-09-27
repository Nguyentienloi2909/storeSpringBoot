package com.example.demo.controller;

import com.example.demo.entity.CustomUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping("/loginPage")
    public String login(Model model) {
        // Thêm thông tin vào model (ví dụ: thông báo lỗi)
        model.addAttribute("loginError", false);
        return "index"; // Tên của file HTML cho trang login tùy chỉnh
    }
    @RequestMapping ("/home")
    public String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetail) {
            CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
            model.addAttribute("user", customUserDetail.getUser());

        }

        // Thêm thông tin vào model để hiển thị trên trang home

        return "index";
    }

    @GetMapping("/admin/home")
    public String  homeAdmin(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetail) {
            CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
            model.addAttribute("user", customUserDetail.getUser());

        }
        return "/admin/index";}

    @GetMapping("/access-denied")
    public String  messageAccessDenied(){return "/error/403";}

    @GetMapping("/showPage403")
    public String showPage403(){
        return "/error/403";
    }

}
