package jp.ken.jdbc.presentation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
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
            @ModelAttribute("memberForm") @Validated MemberRegistForm memberForm,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {

        // ▼バリデーションエラー
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

        // ▼住所をセッションに保存（confirmorderdetails で使用する）
        Map<String, String> address = new HashMap<>();
        address.put("postalCode", memberForm.getPostalCode());
        address.put("address", memberForm.getAddress()); // まとめた住所文字列

        session.setAttribute("shippingAddress", address);

        // ▼プラン選択画面へ遷移
        return "planselection";
    }
}
