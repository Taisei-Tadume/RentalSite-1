package jp.ken.jdbc.presentation.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductDetailsForm {

    // 商品ID（システム管理用、表示はしない）
    @NotNull(message = "商品IDは必須です。")
    private Long goodsId;

    // 注文数量
    @NotNull(message = "注文数量を入力してください。")
    @Min(value = 1, message = "注文数量は1以上で入力してください。")
    private Integer orderQuantity;

    // ===== Getter / Setter =====
    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
