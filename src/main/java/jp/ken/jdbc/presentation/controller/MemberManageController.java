package jp.ken.jdbc.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ken.jdbc.application.service.MemberService;
import jp.ken.jdbc.domain.entity.MemberEntity;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberManageController {

    private final MemberService memberService;

    @GetMapping("/admin/member/manage")
    public String memberManagePage(
            @RequestParam(name = "keyword", required = false) String keyword,
            Model model) {

        List<MemberEntity> list = memberService.search(keyword);

        model.addAttribute("members", list);
        model.addAttribute("keyword", keyword);

        if (!list.isEmpty()) {
            model.addAttribute("selected", list.get(0));
        }

        return "admin-member"; // Thymeleaf のページ名
    }
}
