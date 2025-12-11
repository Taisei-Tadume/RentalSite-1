package jp.ken.jdbc.domain.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GoodsEntity {

    private Integer goodsId;

    @NotBlank(message = "商品名を入力してください")
    private String goodsName;

    @NotNull(message = "カテゴリを選択してください")
    private Integer categoryId;

    @NotNull(message = "ジャンルを入力してください")
    private Integer genreId;

    @NotNull(message = "在庫数を入力してください")
    private Integer quantity;

    private String janCode;

    private String imageUrl;
}
