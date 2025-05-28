package com.piehouse.wooreadmin.subscription.controller;

import com.piehouse.wooreadmin.estate.entity.Estate;
import com.piehouse.wooreadmin.global.kafka.service.KafkaProducerService;
import com.piehouse.wooreadmin.subscription.dto.SubEstateRequest;
import com.piehouse.wooreadmin.subscription.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/subscription")
@AllArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public String SubscriptionView(Model model) {
        model.addAttribute("currentpage", "subscription");
        List<SubEstateRequest> estateList = subscriptionService.getEstateList();
        System.out.println(estateList.size());
        model.addAttribute("estates", estateList);
        return "subscription";
    }

    @PostMapping("/approve")
    public String approve(@RequestParam("estateId") Long estateId, RedirectAttributes redirectAttributes) {
        Boolean result = subscriptionService.approveSubscription(estateId);

        if (result) {
            redirectAttributes.addFlashAttribute("successMessage", "매물이 성공적으로 승인되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "매물 승인 중 오류가 발생했습니다.");
        }

        return "redirect:/subscription";
    }

    @PostMapping("/reject")
    public String reject(@RequestParam("estateId") Long estateId, RedirectAttributes redirectAttributes) {
        Boolean result = subscriptionService.rejectSubscription(estateId);

        if (result) {
            redirectAttributes.addFlashAttribute("successMessage", "매물이 성공적으로 거부되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "매물 거부 중 오류가 발생했습니다.");
        }

        return "redirect:/subscription";
    }

}
