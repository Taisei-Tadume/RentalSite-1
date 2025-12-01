package jp.ken.jdbc.presentation.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDetailsForm {

	 // ======== 商品管理用 ========
    @NotBlank(message = "商品IDは必須です。")
    private String productId; // システム内部管理用、表示はしない

    // ======== 注文情報 ========
    @NotNull(message = "注文数量を入力してください。")
    @Min(value = 1, message = "注文数量は1以上で入力してください。")
    private Integer orderQuantity; // 初期値1、在庫上限はコントローラー側でチェック

    // ======== getter / setter ========
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
