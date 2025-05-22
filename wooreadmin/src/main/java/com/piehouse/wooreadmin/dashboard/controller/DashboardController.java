package com.piehouse.wooreadmin.dashboard.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DashboardController {

    @GetMapping
    public String DashboardView(Model model) {
        model.addAttribute("currentpage", "dashboard");
        return "dashboard";
    }

    @PostMapping("/approve")
    public
}
