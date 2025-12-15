package jp.ken.jdbc.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.application.service.CartService;
import jp.ken.jdbc.domain.dto.CartItem;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // カートに追加
    @PostMapping("/add")
    public String add(@RequestParam("goodsId") int goodsId, HttpSession session) {
        cartService.addToCart(goodsId, session);
        // 検索画面へ戻す（追加済み表示は Session で判断）
        return "redirect:/search";
    }

    // 数を増やす
    @PostMapping("/increase")
    @ResponseBody
    public void increase(@RequestParam int goodsId, HttpSession session) {
        cartService.increase(goodsId, session);
    }

    // 数を減らす
    @PostMapping("/decrease")
    @ResponseBody
    public void decrease(@RequestParam int goodsId, HttpSession session) {
        cartService.decrease(goodsId, session);
    }

    // 削除
    @PostMapping("/remove")
    @ResponseBody
    public String remove(@RequestParam("goodsId") int goodsId, HttpSession session) {
        cartService.remove(goodsId, session);
        return "redirect:/cart";
    }

    // カートクリア
    @PostMapping("/clear")
    public String clear(HttpSession session) {
        cartService.clearCart(session);
        return "redirect:/cart";
    }

    // カート画面表示
    @GetMapping("")
    public String showCartPage(HttpSession session, Model model) {
        var cart = (List<CartItem>) session.getAttribute("cart");
        model.addAttribute("cart", cart);
        model.addAttribute("errorMessage", cart == null || cart.isEmpty() ? "カートに商品が入っていません。" : "");
        return "cart";
    }

    // 注文内容確認（未ログイン時は Spring Security が自動で /login へリダイレクト）
    @GetMapping("/confirm")
    public String confirmOrder() {
        // 未ログインユーザーは Spring Security が /login にリダイレクト
        // GET /cart/confirm へのアクセスは SecurityConfig で authenticated() に設定
        return "redirect:/order/confirm";
    }
}
