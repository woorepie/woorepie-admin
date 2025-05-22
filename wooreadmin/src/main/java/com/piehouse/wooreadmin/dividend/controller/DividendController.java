package com.piehouse.wooreadmin.dividend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dividend")
public class DividendController {

    @GetMapping("")
    public String DividendView(Model model) {
        model.addAttribute("currentpage", "dividend");
        return "dividend";
    }
}
