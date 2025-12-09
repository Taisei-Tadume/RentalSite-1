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

    // ★ 新規登録処理
    @PostMapping("/regist")
    public String regist(
            @Validated @ModelAttribute("memberForm") MemberRegistForm form,
            BindingResult bindingResult) {

        // エラーがあれば画面に戻す
        if (bindingResult.hasErrors()) {
            return "newmemberregistration";
        }

        // DB へ保存
        memberService.regist(form);

        // 登録完了後トップへ
        return "redirect:/top";
    }
}
