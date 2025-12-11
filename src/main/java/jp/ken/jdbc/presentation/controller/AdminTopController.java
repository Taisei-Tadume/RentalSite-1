package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.domain.entity.MemberEntity;

@Controller
@RequestMapping("/admin")
public class AdminTopController {

    private final HttpSession session;

    public AdminTopController(HttpSession session) {
        this.session = session;
    }

    // 管理TOP
    @GetMapping("")
    public String top(Model model) {

        // セッションからログインユーザー取得
        MemberEntity loginUser = (MemberEntity) session.getAttribute("loginUser");

        // 未ログイン → ログイン画面へ
        if (loginUser == null) {
            return "redirect:/login";
        }

        // 権限チェック（2 = 管理者）
        if (loginUser.getAuthorityId() == null || loginUser.getAuthorityId() != 2) {
            model.addAttribute("errorMessage", "管理者権限がありません");
            return "redirect:/"; // top.html を表示
        }

        // 管理者のみアクセス可能
        return "admin-top";
    }
}