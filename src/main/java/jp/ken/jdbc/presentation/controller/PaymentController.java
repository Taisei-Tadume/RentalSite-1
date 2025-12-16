package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {

    // ▼ 決済実行（POST）
    @PostMapping("/payment/execute")
    public String executePayment(
            @RequestParam String cardNumber,
            @RequestParam String cardName,
            @RequestParam String expiry,
            @RequestParam String cvv,
            @RequestParam String planName,
            @RequestParam int amount,
            Model model
    ) {

        model.addAttribute("planName", planName);
        model.addAttribute("amount", amount);

        // ▼ バリデーション
        if (!cardNumber.matches("\\d{16}")) {
            model.addAttribute("errorMessage", "カード番号が不正です。");
            return "payment";
        }

        if (cardName.isBlank()) {
            model.addAttribute("errorMessage", "カード名義が不正です。");
            return "payment";
        }

        if (!expiry.matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
            model.addAttribute("errorMessage", "有効期限が不正です。");
            return "payment";
        }

        if (!cvv.matches("\\d{3}")) {
            model.addAttribute("errorMessage", "セキュリティコードが不正です。");
            return "payment";
        }

        return "payment_success";
    }
}