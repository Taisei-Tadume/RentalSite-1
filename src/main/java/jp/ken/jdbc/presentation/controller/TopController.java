package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class TopController {

    // ★ "/" にアクセスされたら /top にリダイレクト
    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/top";
    }

    @GetMapping("/top")
    public String topPage(Model model, HttpSession session) {

        // ★ セッションから role を取得（例："admin" or "user"）
        String role = (String) session.getAttribute("role");

        // 管理者かどうかのフラグ
        boolean isAdmin = "admin".equals(role);

        model.addAttribute("isAdmin", isAdmin);

        return "top";
    }
}
