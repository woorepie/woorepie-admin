package com.piehouse.wooreadmin.dividend.controller;

import com.piehouse.wooreadmin.dashboard.dto.EstateApproveRequest;
import com.piehouse.wooreadmin.dividend.entity.Dividend;
import com.piehouse.wooreadmin.dividend.repository.DividendRepository;
import com.piehouse.wooreadmin.global.kafka.dto.DividendCompleteMessage;
import com.piehouse.wooreadmin.global.kafka.service.KafkaProducerService;
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

    private final DividendRepository dividendRepository;
    private final KafkaProducerService kafkaProducerService;

    @GetMapping("")
    public String DividendView(Model model) {
        model.addAttribute("currentpage", "dividend");
        List<Dividend> dividendList = dividendRepository.findALlDividends();
        model.addAttribute("dividends", dividendList);
        return "dividend";
    }

    @PostMapping("/approve")
    public String approve(@RequestBody EstateApproveRequest dto, RedirectAttributes redirectAttributes) {
        Dividend dividend = dividendRepository.findById(dto.getEstateId()).orElseThrow();
        // 실제 승인 처리 로직 필요 (예: 상태 변경)
        DividendCompleteMessage message = new DividendCompleteMessage();
        message.setEstate_id(dividend.getEstate().getEstateId());
        message.setDividend_yield(dividend.getDividendYield().floatValue());
        kafkaProducerService.sendDividendCompleteEvent(message);
        redirectAttributes.addFlashAttribute("successMessage", "배당금이 승인되었습니다.");
        return "redirect:/dividend";
    }

    @PostMapping("/reject/{dividendId}")
    public String reject(@RequestBody EstateApproveRequest dto, RedirectAttributes redirectAttributes) {
        // 실제 거부 처리 로직 필요 (예: 상태 변경)
        redirectAttributes.addFlashAttribute("errorMessage", "배당금이 거부되었습니다.");
        return "redirect:/dividend";
    }
}
