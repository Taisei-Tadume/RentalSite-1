package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
            @ModelAttribute("memberForm") @Valid MemberRegistForm memberForm,
            BindingResult bindingResult,
            Model model) {

        // ▼バリデーションエラーがある時点で画面に戻す
        if (bindingResult.hasErrors()) {
            return "newmemberregistration";
        }

        // ▼パスワード一致チェック
        if (!memberForm.getPassword().equals(memberForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "error.passwordConfirm", "パスワードが一致しません");
            return "newmemberregistration";
        }

        // ▼メール重複チェック
        if (memberService.emailExists(memberForm.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "このメールアドレスは既に登録されています");
            return "newmemberregistration";
        }

        // ▼登録処理
        memberService.register(memberForm);

        // ▼プラン選択画面に遷移
        return "planselection";
    }
}
