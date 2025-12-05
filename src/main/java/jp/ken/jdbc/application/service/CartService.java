package jp.ken.jdbc.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.domain.dto.CartItem;
import jp.ken.jdbc.domain.entity.GoodsEntity;
import jp.ken.jdbc.domain.repository.GoodsRepository;

@Service
public class CartService {

    @Autowired
    private GoodsRepository goodsRepository;

    private static final String CART_SESSION_KEY = "cart";

    // カート取得（なければ作る）
    @SuppressWarnings("unchecked")
    public List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    // 商品追加
    public void addToCart(long goodsId, HttpSession session)
{

    	GoodsEntity goods = goodsRepository.findById(goodsId);
    	List<CartItem> cart = getCart(session);


        // すでに存在するなら数量+1
        for (CartItem item : cart) {
            if (item.getGoods().getGoodsId() == goodsId) {
                item.increase();
                return;
            }
        }

        // 新規追加
        cart.add(new CartItem(goods));
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    // 数量増加
    public void increase(long goodsId, HttpSession session) {
        List<CartItem> cart = getCart(session);
        cart.stream()
                .filter(i -> i.getGoods().getGoodsId() == goodsId)
                .findFirst()
                .ifPresent(CartItem::increase);
    }

    // 数量減少
    public void decrease(long goodsId, HttpSession session) {
        List<CartItem> cart = getCart(session);
        cart.stream()
                .filter(i -> i.getGoods().getGoodsId() == goodsId)
                .findFirst()
                .ifPresent(CartItem::decrease);
    }

    // 削除
    public void remove(long goodsId, HttpSession session) {
        List<CartItem> cart = getCart(session);
        cart.removeIf(i -> i.getGoods().getGoodsId() == goodsId);
    }

    // 全削除
    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }
}
