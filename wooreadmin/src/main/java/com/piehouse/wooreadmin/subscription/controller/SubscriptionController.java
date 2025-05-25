package com.piehouse.wooreadmin.subscription.controller;

import com.piehouse.wooreadmin.dashboard.entity.Estate;
import com.piehouse.wooreadmin.global.kafka.service.KafkaProducerService;
import com.piehouse.wooreadmin.subscription.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/subscription")
@AllArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final KafkaProducerService kafkaProducerService;

    @GetMapping("")
    public String SubscriptionView(Model model) {
        model.addAttribute("currentpage", "subscription");
        return "subscription";
    }

    @PostMapping("/approve/{estateId}")
    public String approve(@PathVariable Long estateId, RedirectAttributes redirectAttributes) {
        Boolean result = subscriptionService.approveSubscription(estateId);

        if (result) {
            redirectAttributes.addFlashAttribute("successMessage", "매물이 성공적으로 승인되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "매물 승인 중 오류가 발생했습니다.");
        }

        return "redirect:/subscription";
    }

    @PostMapping("/reject/{estateId}")
    public String reject(@PathVariable Long estateId, RedirectAttributes redirectAttributes) {
        Boolean result = subscriptionService.rejectSubscription(estateId);

        if (result) {
            redirectAttributes.addFlashAttribute("successMessage", "매물이 성공적으로 거부되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "매물 거부 중 오류가 발생했습니다.");
        }

        return "redirect:/subscription";
    }



}
