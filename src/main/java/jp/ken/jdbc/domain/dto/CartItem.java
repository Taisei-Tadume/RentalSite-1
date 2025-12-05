package jp.ken.jdbc.domain.dto;

import jp.ken.jdbc.domain.entity.GoodsEntity;

public class CartItem {
    private GoodsEntity goods;
    private int quantity;

    public CartItem(GoodsEntity goods) {
        this.goods = goods;
        this.quantity = 1;
    }

    public GoodsEntity getGoods() { return goods; }
    public int getQuantity() { return quantity; }

    public void increase() { quantity++; }
    public void decrease() { if (quantity > 1) quantity--; }
}
