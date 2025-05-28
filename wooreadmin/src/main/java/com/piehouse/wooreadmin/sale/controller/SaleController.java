package com.piehouse.wooreadmin.sale.controller;

import com.piehouse.wooreadmin.estate.entity.Estate;
import com.piehouse.wooreadmin.sale.service.SaleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sale")
@Slf4j
@AllArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @GetMapping("")
    public String SaleView(Model model) {
        model.addAttribute("currentpage", "sale");
        List<Estate> estates = saleService.getSuccessEstateList();
        model.addAttribute("estates", estates);
        return "sale";
    }

    // @PostMapping("/approve/{saleId}")
    // public String approve(@PathVariable Long saleId, RedirectAttributes redirectAttributes) {
    //     SaleItem sale = saleService.findById(saleId);
    //     // 실제 승인 처리 로직 필요 (예: 상태 변경)
    //     SaleCompleteEvent event = new SaleCompleteEvent();
    //     event.setEstateId(sale.getEstateId());
    //     kafkaProducerService.sendSaleCompleteEvent(event);
    //     redirectAttributes.addFlashAttribute("successMessage", "매각이 승인되었습니다.");
    //     return "redirect:/sale";
    // }

    // @PostMapping("/reject/{saleId}")
    // public String reject(@PathVariable Long saleId, RedirectAttributes redirectAttributes) {
    //     // 실제 거부 처리 로직 필요 (예: 상태 변경)
    //     redirectAttributes.addFlashAttribute("errorMessage", "매각이 거부되었습니다.");
    //     return "redirect:/sale";
    // }
}
