package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderConfirmController {

    @GetMapping("/order/confirm")
    public String confirm() {
        return "confirrmorderdetails";
    }
}
