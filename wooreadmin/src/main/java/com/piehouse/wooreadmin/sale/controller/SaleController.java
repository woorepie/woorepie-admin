package com.piehouse.wooreadmin.sale.controller;

import com.piehouse.wooreadmin.estate.entity.Estate;
import com.piehouse.wooreadmin.sale.service.SaleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

     @PostMapping("/approve")
     public String approve(@RequestParam("estateId") Long estateId, RedirectAttributes redirectAttributes) {

        if(saleService.approveSaleEstate(estateId)) {
            redirectAttributes.addFlashAttribute("successMessage", "매물 매각이 성공적으로 승인되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "매물 매각 중 오류가 발생했습니다.");
        }

         return "redirect:/sale";
     }

}
