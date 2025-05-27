package com.piehouse.wooreadmin.dashboard.controller;


import com.piehouse.wooreadmin.dashboard.entity.Estate;
import com.piehouse.wooreadmin.dashboard.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public String DashboardView(Model model) {
        model.addAttribute("currentpage", "dashboard");
        Optional<List<Estate>> estateList = dashboardService.getAllEstate();
        estateList.ifPresent(estate -> model.addAttribute("estates", estate));

        return "dashboard";
    }

    @PostMapping("/approve/{estateId}")
    public String approve(@PathVariable Long estateId, RedirectAttributes redirectAttributes) {
        Boolean result = dashboardService.approveEstate(estateId);

        if (result) {
            redirectAttributes.addFlashAttribute("successMessage", "매물이 성공적으로 승인되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "매물 승인 중 오류가 발생했습니다.");
        }

        return "redirect:/";
    }

    @PostMapping("/reject/{estateId}")
    public String reject(@PathVariable Long estateId, RedirectAttributes redirectAttributes) {
        Boolean result = dashboardService.rejectEstate(estateId);

        if (result) {
            redirectAttributes.addFlashAttribute("successMessage", "매물이 성공적으로 거부되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "매물 거부 중 오류가 발생했습니다.");
        }

        return "redirect:/";
    }
}
