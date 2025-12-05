package jp.ken.jdbc.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.domain.dto.CartItem;

@Service
public class CartService {

    private static final String CART_SESSION_KEY = "cart";

    // カート取得（セッションにない場合は空を入れる）
    @SuppressWarnings("unchecked")
    public List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    // カートに商品を追加
    public void addToCart(HttpSession session, CartItem item) {
        List<CartItem> cart = getCart(session);

        // すでに同じ商品がある場合は数量だけ追加
        for (CartItem cartItem : cart) {
            if (cartItem.getGoodsId().equals(item.getGoodsId())) {
                cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                return;
            }
        }

        cart.add(item);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    // カートから商品を削除
    public void removeFromCart(HttpSession session, Long goodsId) {
        List<CartItem> cart = getCart(session);
        cart.removeIf(item -> item.getGoodsId().equals(goodsId));
    }

    // カート全削除
    public void clearCart(HttpSession session) {
        session.setAttribute(CART_SESSION_KEY, new ArrayList<CartItem>());
    }
}

