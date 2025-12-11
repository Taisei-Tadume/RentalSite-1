package jp.ken.jdbc.presentation.form;

import java.util.Map;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * カート画面用フォーム
 */
public class CartForm {

    /**
     * カート内商品IDと数量のマップ
     * key: 商品ID, value: 注文数量
     */
    @NotNull(message = "カート内商品が存在しません。")
    private Map<@NotNull Long, @Min(value = 1, message = "注文数量は1以上で入力してください") Integer> items;

    // ===== Getter / Setter =====
    public Map<Long, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Long, Integer> items) {
        this.items = items;
    }
}
