package com.piehouse.wooreadmin.dividend.controller;

import com.piehouse.wooreadmin.dividend.service.DividendService;
import com.piehouse.wooreadmin.estate.entity.Estate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/dividend")
@RequiredArgsConstructor
public class    DividendController {

    private final DividendService dividendService;

    @GetMapping
    public String DividendView(Model model) {
        model.addAttribute("currentpage", "dividend");
        List<Estate> estates = dividendService.getSuccessEstateList();
        model.addAttribute("estates", estates);
        return "dividend";
    }

    @PostMapping("/approve")
    public String approve(@RequestParam("estateId") Long estateId, @RequestParam("dividend") Integer dividend,
                          RedirectAttributes redirectAttributes) {
        if(dividendService.approveDividend(estateId, dividend)){
            redirectAttributes.addFlashAttribute("successMessage", "배당금이 승인되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "배당금이 승인 오류가 발생했습니다.");
        }
        return "redirect:/dividend";
    }

}
