package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import jp.ken.jdbc.presentation.form.MemberForm;

@Controller
public class MemberRegistController {

    // --- GET: 新規会員登録フォーム表示 ---
    @GetMapping("/regist")
    public String registForm(Model model) {
        MemberForm memberForm = new MemberForm();
        model.addAttribute("memberForm", memberForm);
        return "newmemberregistration";
    }

    // --- POST: 新規会員登録処理 ---
    @PostMapping("/regist")
    public String registSubmit(
            @Valid @ModelAttribute("memberForm") MemberForm memberForm,
            BindingResult bindingResult,
            Model model) {

        // バリデーションエラーがある場合はフォームに戻す
        if (bindingResult.hasErrors()) {
            return "newmemberregistration";
        }

        // TODO: 会員登録処理（DB保存など）をここに書く

        // 登録成功後はプラン選択画面に遷移
        return "redirect:/planselection";
    }

}
