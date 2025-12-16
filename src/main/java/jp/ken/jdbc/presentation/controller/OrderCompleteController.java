package jp.ken.jdbc.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.application.service.OrderService;
import jp.ken.jdbc.domain.dto.CartItem;

@Controller
public class OrderCompleteController {

    private final OrderService orderService;

    public OrderCompleteController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 注文確定処理（POST）
    @PostMapping("/order/complete")
    public String completePost(HttpSession session) {

        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            // カートなしでのアクセスはトップへ
            return "redirect:/top";
        }

        // ==========================
        // 在庫を減らす
        // ==========================
        orderService.completeOrder(cart);

        // ==========================
        // カートをクリア
        // ==========================
        session.removeAttribute("cart");

        // PRGパターン（リロード対策）
        return "redirect:/order/complete";
    }

    // 完了画面（GET）
    @GetMapping("/order/complete")
    public String completeGet() {
        return "ordercomplete";
    }
}
