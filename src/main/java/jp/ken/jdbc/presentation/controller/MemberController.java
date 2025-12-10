package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.ken.jdbc.application.service.MemberService;
import jp.ken.jdbc.presentation.form.MemberRegistForm;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 新規登録画面へ
    @GetMapping("/newmember")
    public String showForm(Model model) {
        model.addAttribute("memberForm", new MemberRegistForm());
        return "newmemberregistration";
    }

    // ★ 新規登録処理（完全版）
    @PostMapping("/regist")
    public String regist(
            @Validated @ModelAttribute("memberForm") MemberRegistForm form,
            BindingResult bindingResult,
            Model model) {

        // 入力エラー
        if (bindingResult.hasErrors()) {
            return "newmemberregistration";
        }

        // ★ メールアドレス重複チェック
        if (memberService.emailExists(form.getEmail())) {
            model.addAttribute("errorMessage", "このメールアドレスはすでに登録されています");
            return "newmemberregistration";
        }

        // DBへ保存
        memberService.register(form);

        // 完了後トップへ
        return "redirect:/top";
    }
}
