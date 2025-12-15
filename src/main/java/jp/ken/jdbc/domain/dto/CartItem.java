package jp.ken.jdbc.domain.dto;

import java.io.Serializable;

import jp.ken.jdbc.domain.entity.GoodsEntity;

/**
 * カート内の商品情報
 */
public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 商品情報 */
    private GoodsEntity goods;

    /** 数量 */
    private int quantity;

    public CartItem(GoodsEntity goods) {
        this.goods = goods;
        this.quantity = 1;
    }

    // --- getter ---

    public GoodsEntity getGoods() {
        return goods;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * 商品IDを返す（Controller から直接参照用）
     */
    public Integer getGoodsId() {
        return goods != null ? goods.getGoodsId() : null;
    }

    // --- business logic ---

    public void increase() {
        quantity++;
    }

    public void decrease() {
        if (quantity > 1) {
            quantity--;
        }
    }
}
