package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ken.jdbc.application.service.MemberService;

@Controller
public class AdminMemberController {

    private final MemberService memberService;

    public AdminMemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/admin/member")
    public String memberManagePage(
            @RequestParam(value="keyword", required=false) String keyword,
            Model model) {

        var members = (keyword == null)
                ? memberService.findAllMembers()
                : memberService.search(keyword);

        model.addAttribute("members", members);
        model.addAttribute("keyword", keyword);

        return "admin-member";
    }

    @PostMapping("/admin/member/authority")
    public String updateAuthority(
            @RequestParam Integer userId,
            @RequestParam Integer authorityId) {

        memberService.changeAuthority(userId, authorityId);

        return "redirect:/admin/member?keyword=" + userId;
    }
}
