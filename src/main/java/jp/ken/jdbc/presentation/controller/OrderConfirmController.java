package jp.ken.jdbc.presentation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.domain.dto.CartItem;
import jp.ken.jdbc.domain.entity.MemberEntity;

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

        // --- 配送先情報取得 ---
        @SuppressWarnings("unchecked")
        Map<String, String> shippingAddress =
                (Map<String, String>) session.getAttribute("shippingAddress");

        if (shippingAddress == null) {
            // ログインユーザー情報から DB 取得
            MemberEntity loginUser = (MemberEntity) session.getAttribute("loginUser");
            if (loginUser != null) {
                shippingAddress = Map.of(
                        "postalCode", loginUser.getPostalCode() != null ? loginUser.getPostalCode() : "",
                        "address", loginUser.getAddress() != null ? loginUser.getAddress() : ""
                );

                // セッションに保存して次回以降も使えるようにする
                session.setAttribute("shippingAddress", shippingAddress);
            } else {
                // 未ログイン or セッション切れ時
                shippingAddress = Map.of("postalCode", "", "address", "");
            }
        }

        // --- model にセット ---
        model.addAttribute("cartItem", cart);
        model.addAttribute("postalCode", shippingAddress.get("postalCode"));
        model.addAttribute("address", shippingAddress.get("address"));

        return "confirmorderdetails";
    }
}
