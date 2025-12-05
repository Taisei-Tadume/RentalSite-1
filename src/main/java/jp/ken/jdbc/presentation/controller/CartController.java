package jp.ken.jdbc.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.application.service.CartService;
import jp.ken.jdbc.domain.dto.CartItem;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // カート表示
    @GetMapping
    public String showCart(HttpSession session, Model model) {
        model.addAttribute("cartItems", cartService.getCart(session));
        return "cart"; // cart.html
    }

    // カート追加（POST）
    @PostMapping("/add")
    public String addToCart(
            @RequestParam("goodsId") Long goodsId,
            @RequestParam("goodsName") String goodsName,
            @RequestParam("quantity") int quantity,
            HttpSession session) {

        CartItem item = new CartItem(goodsId, goodsName, quantity);
        cartService.addToCart(session, item);

        return "redirect:/cart";
    }

    // 商品削除
    @PostMapping("/remove")
    public String remove(@RequestParam("goodsId") Long goodsId, HttpSession session) {
        cartService.removeFromCart(session, goodsId);
        return "redirect:/cart";
    }

    // カートを空にする
    @PostMapping("/clear")
    public String clear(HttpSession session) {
        cartService.clearCart(session);
        return "redirect:/cart";
    }
}
