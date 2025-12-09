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



@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // カートに追加
    @PostMapping("/add")
    public String add(@RequestParam("goodsId") long goodsId,
                      HttpSession session) {

        cartService.addToCart(goodsId, session);

        // ★ 検索画面へ戻す（追加済み表示は Session で判断）
        return "redirect:/search";
    }

    // 削除（必要なら）
    @PostMapping("/remove")
    public String remove(@RequestParam("goodsId") long goodsId, HttpSession session) {
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

        // セッションのカート情報を取得
        var cart = session.getAttribute("cart");

        model.addAttribute("cart", cart);
        model.addAttribute("errorMessage", "カートに商品が入っていません。");

        return "cart"; // ← cart.html を返す
    }


}
