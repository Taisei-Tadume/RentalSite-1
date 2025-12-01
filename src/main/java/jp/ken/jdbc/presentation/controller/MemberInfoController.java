package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberInfoController {

    @GetMapping("/member/info")
    public String info() {
        return "memberinformation";
    }
}
