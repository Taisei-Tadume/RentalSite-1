package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberRegistController {

    @GetMapping("/member/regist")
    public String regist() {
        return "newmemberregistration";
    }
}
