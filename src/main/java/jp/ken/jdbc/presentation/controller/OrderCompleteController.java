package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderCompleteController {

    @GetMapping("/order/complete")
    public String complete() {
        return "ordercomplete";
    }
}
