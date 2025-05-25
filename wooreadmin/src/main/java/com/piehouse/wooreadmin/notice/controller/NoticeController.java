package com.piehouse.wooreadmin.notice.controller;


import com.piehouse.wooreadmin.dashboard.entity.Estate;
import com.piehouse.wooreadmin.notice.repository.NoticeRepository;
import com.piehouse.wooreadmin.notice.service.NoticeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/disclosure")
@AllArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("")
    public String DisclosureView(Model model){
        model.addAttribute("currentpage", "disclosure");
        Optional<List<Estate>> estates = noticeService.getAllEstates();
        estates.ifPresent(estateList -> model.addAttribute("estates", estateList));
        return "disclosure-create";
    }

    @PostMapping("/create")
    public String createDisclosure(
            @RequestParam("notice_title") String noticeTitle,
            @RequestParam(value = "notice_content", required = false) String noticeContent,
            @RequestParam("estate_id") Long estateId,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "agreeTerms", required = false) boolean agreeTerms,
            RedirectAttributes redirectAttributes) {

        try {
            noticeService.addNotice(noticeTitle,noticeContent,estateId,file);
            redirectAttributes.addFlashAttribute("message", "공시가 성공적으로 등록되었습니다.");
            return "redirect:/disclosure";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "공시 등록 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/disclosure";
        }
    }

}
