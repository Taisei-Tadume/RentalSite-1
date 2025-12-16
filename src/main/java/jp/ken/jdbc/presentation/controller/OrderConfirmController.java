package jp.ken.jdbc.presentation.controller;

import java.util.List;

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
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        // --- ログインユーザー取得 ---
        MemberEntity loginUser =
                (MemberEntity) session.getAttribute("loginUser");

        if (loginUser == null) {
            // 通常は Security でここには来ないが安全策
            return "redirect:/login";
        }

        // --- model にセット ---
        model.addAttribute("cartItem", cart);
        model.addAttribute("loginUser", loginUser);

        return "confirmorderdetails";
    }
}
