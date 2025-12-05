package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import jp.ken.jdbc.application.service.MemberService;
import jp.ken.jdbc.domain.entity.MemberEntity;
import jp.ken.jdbc.presentation.form.MemberRegistForm;

@Controller
public class MemberRegistController {

	private final MemberService memberService;

    public MemberRegistController(MemberService memberService) {
        this.memberService = memberService;
    }

    // --- GET: 新規会員登録フォーム表示 ---
    @GetMapping("/regist")
    public String registForm(Model model) {
        MemberRegistForm memberForm = new MemberRegistForm();
        model.addAttribute("memberForm", memberForm);
        return "newmemberregistration";
    }

    // --- POST: フォーム送信（会員登録処理） ---
    @PostMapping("/regist")
    public String registSubmit(@Valid MemberRegistForm memberForm, BindingResult result, Model model) {

        // パスワード確認チェック
        if (!memberForm.getPassword().equals(memberForm.getPasswordConfirm())) {
            result.rejectValue("passwordConfirm", "error.passwordConfirm", "パスワードが一致しません");
        }

        // メールアドレス重複チェック
        if (memberService.emailExists(memberForm.getEmail())) {
            result.rejectValue("email", "error.email", "このメールアドレスは既に登録されています");
        }

        if (result.hasErrors()) {
            model.addAttribute("memberForm", memberForm);
            return "newmemberregistration";
        }

        // Form → Entity 変換
        MemberEntity member = new MemberEntity();
        member.setUserName(memberForm.getUserName());
        member.setEmail(memberForm.getEmail());
        member.setPhoneNumber(memberForm.getPhoneNumber());
        member.setAddress(memberForm.getAddress());
        member.setPasswordHash(memberForm.getPassword()); // パスワードは Service 内でハッシュ化される

        // 登録処理
        memberService.register(member);

        return "success"; // 登録完了画面
    }
}
