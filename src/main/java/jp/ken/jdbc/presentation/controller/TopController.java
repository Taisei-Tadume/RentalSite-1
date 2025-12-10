package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class TopController {

    @GetMapping({"", "/", "/top"})
    public String topPage(Model model, HttpSession session) {

        String role = (String) session.getAttribute("role");
        boolean isAdmin = "admin".equals(role);

        model.addAttribute("isAdmin", isAdmin);

        return "top";
    }
}
