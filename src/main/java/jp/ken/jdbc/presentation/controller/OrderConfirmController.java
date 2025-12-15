package jp.ken.jdbc.presentation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.domain.dto.CartItem;

@Controller
@RequestMapping("/order")
public class OrderConfirmController {

    @GetMapping("/confirm")
    public String showConfirmPage(HttpSession session, Model model) {

        // --- カート取得 ---
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        // --- 新規登録時に保存した配送先住所を取得（Map）---
        @SuppressWarnings("unchecked")
        Map<String, String> shippingAddress =
                (Map<String, String>) session.getAttribute("shippingAddress");

        if (shippingAddress == null) {
            shippingAddress = Map.of("postalCode", "", "address", "");
        }

        // --- model にセット ---
        model.addAttribute("cartItem", cart);
        model.addAttribute("postalCode", shippingAddress.get("postalCode"));
        model.addAttribute("address", shippingAddress.get("address"));

        return "confirmorderdetails";
    }
}
