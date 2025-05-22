package com.piehouse.wooreadmin.notice.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/disclosure")
public class NoticeController {

    @GetMapping("")
    public String DisclosureView(Model model){
        model.addAttribute("currentpage", "disclosure");
        return "disclosure";
    }

}
