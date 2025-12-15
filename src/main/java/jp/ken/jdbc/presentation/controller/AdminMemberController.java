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

    // ============================
    // 会員管理画面表示
    // ============================
    @GetMapping("/admin/member")
    public String memberManagePage(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        var members = (keyword == null || keyword.isBlank())
                ? memberService.findAllMembers()
                : memberService.search(keyword);

        model.addAttribute("members", members);
        model.addAttribute("keyword", keyword);

        return "admin-member";
    }

    // ============================
    // 権限変更
    // ============================
    @PostMapping("/admin/member/authority")
    public String updateAuthority(
            @RequestParam Integer userId,
            @RequestParam Integer authorityId) {

        memberService.changeAuthority(userId, authorityId);

        return "redirect:/admin/member?keyword=" + userId;
    }

    // ============================
    // プラン変更（Free / Bronze / Silver / Gold）
    // ============================
    @PostMapping("/admin/member/plan")
    public String updatePlan(
            @RequestParam Integer userId,
            @RequestParam Integer planId) {

        memberService.changePlan(userId, planId);

        return "redirect:/admin/member?keyword=" + userId;
    }
}
