package jp.ken.jdbc.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.domain.dto.CartItem;

@Controller
public class OrderCompleteController {

    // 注文完了処理（POST）
    @PostMapping("/order/complete")
    public String completePost(HttpSession session) {

        // --- カート情報取得 ---
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null && !cart.isEmpty()) {
            // TODO: 注文内容をDBに登録する処理をここに書く

            // --- カートのセッションを破棄 ---
            session.removeAttribute("cart");
        }

        return "ordercomplete";
    }

    // 完了画面を直接表示したい場合の GET
    @GetMapping("/order/complete")
    public String completeGet() {
        return "ordercomplete";
    }
}
