package com.piehouse.wooreadmin.sale.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sale")
@Slf4j
public class SaleController {

    @GetMapping("")
    public String SaleView(Model model) {
        model.addAttribute("currentpage", "sale");
        return "sale";
    }


}
