package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {   // ★ クラス名を修正

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

        // 入力値を再表示用に保持
        model.addAttribute("cardNumber", cardNumber);
        model.addAttribute("cardName", cardName);
        model.addAttribute("expiry", expiry);
        model.addAttribute("cvv", cvv);
        model.addAttribute("planName", planName);
        model.addAttribute("amount", amount);

        // --- バリデーション ----------------------------------------------------

        // ① カード番号 16 桁数字
        if (!cardNumber.matches("\\d{16}")) {
            model.addAttribute("errorMessage", "カード番号が不正です。");
            return "payment";
        }

        // ② 名義（空白不可）
        if (cardName.isBlank()) {
            model.addAttribute("errorMessage", "カード名義が不正です。");
            return "payment";
        }

        // ③ 有効期限 MM/YY形式
        if (!expiry.matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
            model.addAttribute("errorMessage", "有効期限が不正です。");
            return "payment";
        }

        // ④ セキュリティコード 3桁
        if (!cvv.matches("\\d{3}")) {
            model.addAttribute("errorMessage", "セキュリティコードが不正です。");
            return "payment";
        }

        // ★ 本来は決済APIと通信する部分（今は成功扱い）
        return "payment_success";
    }
}
