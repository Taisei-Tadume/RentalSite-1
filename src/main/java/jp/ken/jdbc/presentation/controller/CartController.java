package jp.ken.jdbc.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

        // detail?added=1 に戻る（トースト表示）
        return "redirect:/detail/" + goodsId + "?added=1";
    }

    // 増加
    @PostMapping("/increase")
    public String increase(@RequestParam("goodsId") long goodsId, HttpSession session) {
        cartService.increase(goodsId, session);
        return "redirect:/cart";
    }

    // 減少
    @PostMapping("/decrease")
    public String decrease(@RequestParam("goodsId") long goodsId, HttpSession session) {
        cartService.decrease(goodsId, session);
        return "redirect:/cart";
    }

    // 削除
    @PostMapping("/remove")
    public String remove(@RequestParam("goodsId") long goodsId, HttpSession session) {
        cartService.remove(goodsId, session);
        return "redirect:/cart";
    }
}
