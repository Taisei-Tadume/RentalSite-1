package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import jp.ken.jdbc.application.service.MemberService;
import jp.ken.jdbc.presentation.form.MemberRegistForm;

@Controller
public class MemberRegistController {

    private final MemberService memberService;

    public MemberRegistController(MemberService memberService) {
        this.memberService = memberService;
    }

    // --- GET: 新規会員登録フォーム ---
    @GetMapping("/regist")
    public String registForm(Model model) {
        model.addAttribute("memberForm", new MemberRegistForm());
        return "newmemberregistration";
    }

    // --- POST: 会員登録 ---
    @PostMapping("/regist")
    public String registSubmit(
            @Valid MemberRegistForm memberForm,
            BindingResult result,
            Model model) {

        // パスワード一致チェック
        if (!memberForm.getPassword().equals(memberForm.getPasswordConfirm())) {
            result.rejectValue("passwordConfirm", "error.passwordConfirm", "パスワードが一致しません");
        }

        // メールアドレス重複チェック
        if (memberService.emailExists(memberForm.getEmail())) {
            result.rejectValue("email", "error.email", "このメールアドレスは既に登録されています");
        }

        // エラーがあれば画面に戻す
        if (result.hasErrors()) {
            model.addAttribute("memberForm", memberForm);
            return "newmemberregistration";
        }

        // ▼★ ここ重要：Service に Form を渡すだけ
        memberService.register(memberForm);

        // 登録成功ページ
        return "success";
    }
}
