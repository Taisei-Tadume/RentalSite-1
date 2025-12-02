package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMemberController {

    // 会員管理画面
    @GetMapping("/member")
    public String memberManage() {
        return "admin-member"; 
    }
}
