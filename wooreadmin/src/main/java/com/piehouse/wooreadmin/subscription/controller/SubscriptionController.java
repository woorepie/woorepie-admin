package com.piehouse.wooreadmin.subscription.controller;

import com.piehouse.wooreadmin.dashboard.dto.EstateApproveRequest;
import com.piehouse.wooreadmin.dashboard.entity.Estate;
import com.piehouse.wooreadmin.global.kafka.service.KafkaProducerService;
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
    private final KafkaProducerService kafkaProducerService;

    @GetMapping
    public String SubscriptionView(Model model) {
        model.addAttribute("currentpage", "subscription");
        List<Estate> estateList = subscriptionService.getEstateList();
        model.addAttribute("estates", estateList);
        return "subscription";
    }

    @PostMapping("/approve")
    public String approve(@RequestBody EstateApproveRequest dto, RedirectAttributes redirectAttributes) {
        Boolean result = subscriptionService.approveSubscription(dto);

        if (result) {
            redirectAttributes.addFlashAttribute("successMessage", "매물이 성공적으로 승인되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "매물 승인 중 오류가 발생했습니다.");
        }

        return "redirect:/subscription";
    }

    @PostMapping("/reject")
    public String reject(@RequestBody EstateApproveRequest dto, RedirectAttributes redirectAttributes) {
        Boolean result = subscriptionService.rejectSubscription(dto);

        if (result) {
            redirectAttributes.addFlashAttribute("successMessage", "매물이 성공적으로 거부되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "매물 거부 중 오류가 발생했습니다.");
        }

        return "redirect:/subscription";
    }

}
