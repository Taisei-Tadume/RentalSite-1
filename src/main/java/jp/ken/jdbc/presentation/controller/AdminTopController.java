package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminTopController {

    // 管理TOP
    @GetMapping("")
    public String top() {
        return "admin-top"; // admin-top.html を返す
    }
}
