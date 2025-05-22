package com.piehouse.wooreadmin.subscription.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/subscription")
public class SubscriptionController {

    @GetMapping("")
    public String SubscriptionView(Model model) {
        model.addAttribute("currentpage", "subscription");
        return "subscription";
    }


}
