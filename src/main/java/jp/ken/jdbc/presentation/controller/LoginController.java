package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.application.service.MemberService;
import jp.ken.jdbc.domain.entity.MemberEntity;
import jp.ken.jdbc.presentation.form.LoginForm;

@Controller
public class LoginController {

    private final MemberService memberService;
    private final HttpSession session;

    public LoginController(MemberService memberService, HttpSession session) {
        this.memberService = memberService;
        this.session = session;
    }

    @GetMapping("/login")
    public String loginPage(@ModelAttribute("loginForm") LoginForm form, Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @Validated @ModelAttribute("loginForm") LoginForm form,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "login";
        }

        MemberEntity user = memberService.login(form);
        if (user == null) {
            model.addAttribute("errorMessage", "メールアドレスまたはパスワードが違います");
            return "login";
        }

        // ログイン成功 → セッションにユーザを保存
        session.setAttribute("loginUser", user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/login";
    }
}
