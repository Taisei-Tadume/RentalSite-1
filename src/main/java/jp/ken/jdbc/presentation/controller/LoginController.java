package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.ken.jdbc.presentation.form.LoginForm;

@Controller
public class LoginController {
/*  必要ないかも
    private final MemberService memberService;
    private final HttpSession session;

    private static final String LOGIN_USER = "loginUser";

    public LoginController(MemberService memberService, HttpSession session) {
        this.memberService = memberService;
        this.session = session;
    }*/

    @GetMapping("/login")
    public String loginPage(@ModelAttribute("loginForm") LoginForm form, Model model) {
        return "login";
    }
/*  必要ないかも
    @PostMapping("/login")
    public String login(
            @Validated @ModelAttribute("loginForm") LoginForm form,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "login";
        }

        MemberEntity user;
        try {
            user = memberService.login(form);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login";
        }

        if (user == null) {
            model.addAttribute("errorMessage", "メールアドレスまたはパスワードが違います");
            return "login";
        }

        // ログイン成功 → セッションにユーザを保存
        session.setAttribute(LOGIN_USER, user);

        // authority_id によって遷移先を変更
        Integer authority = user.getAuthorityId(); // ← フィールド名に合わせて変更

        if (authority == null) {
            model.addAttribute("errorMessage", "ユーザー権限が設定されていません");
            return "login";
        }

        // 1 = 一般ユーザー → 通常トップページへ
        if (authority == 1) {
            return "redirect:/";
        }

        // 2 = 管理者 → 管理者トップへ
        if (authority == 2) {
            return "redirect:/admin";
        }

        // その他の数値 → 権限エラー扱い
        model.addAttribute("errorMessage", "不正な権限が設定されています");
        return "login";
    }*/
}
