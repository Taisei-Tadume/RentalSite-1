package jp.ken.jdbc.presentation.form;

import java.util.Map;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


/**
 * カート画面（⑤カート処理画面）用フォームクラス
 */
public class CartForm {

	  /**
     * カート内商品IDと数量のマップ
     * key: 商品ID, value: 注文数量
     */
    @NotNull(message = "カート内商品が存在しません。")
    private Map<@Size(min = 1, message = "商品IDが不正です") String,
                @Min(value = 1, message = "注文数量は1以上で入力してください") Integer> items;

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }
	
}
