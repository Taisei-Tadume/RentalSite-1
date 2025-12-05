package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderConfirmController {

    // 注文確定（POST）
    @PostMapping("/confirm")
    public String confirmOrder(HttpSession session, Model model) {

        // セッションから注文内容を取得
        Object cartItem = session.getAttribute("cartItem");
        Object shippingAddress = session.getAttribute("shippingAddress");
        Object paymentMethod = session.getAttribute("paymentMethod");

        // ★本来ここで注文をDBに保存する処理を書く
        // orderService.saveOrder(cartItem, shippingAddress, paymentMethod);

        // 確認用に画面へ渡すなら model.addAttribute も可能
        model.addAttribute("cartItem", cartItem);
        model.addAttribute("shippingAddress", shippingAddress);
        model.addAttribute("paymentMethod", paymentMethod);

        // 完了画面へ
        return "confirmorderdetails";
    }
}