package com.piehouse.wooreadmin.estate.controller;


import com.piehouse.wooreadmin.estate.dto.EstateApproveRequest;
import com.piehouse.wooreadmin.estate.entity.Estate;
import com.piehouse.wooreadmin.estate.service.EstateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class EstateController {

    private final EstateService estateService;

    @GetMapping
    public String DashboardView(Model model) {
        model.addAttribute("currentpage", "dashboard");
        List<Estate> estateList = estateService.getAllEstate();
        model.addAttribute("estates", estateList);

        return "dashboard";
    }

    // 매물 승인
    @PostMapping("/estate/approve")
    public String approve(@RequestBody EstateApproveRequest dto, RedirectAttributes redirectAttributes) {
        Boolean result = estateService.approveEstate(dto);

        if (result) {
            redirectAttributes.addFlashAttribute("successMessage", "매물이 성공적으로 승인되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "매물 승인 중 오류가 발생했습니다.");
        }

        return "redirect:/";
    }

    @PostMapping("/reject")
    public String reject(@RequestBody EstateApproveRequest dto, RedirectAttributes redirectAttributes) {
        Boolean result = estateService.rejectEstate(dto);

        if (result) {
            redirectAttributes.addFlashAttribute("successMessage", "매물이 성공적으로 거부되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "매물 거부 중 오류가 발생했습니다.");
        }

        return "redirect:/";
    }
}
