package jp.ken.jdbc.domain.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GoodsEntity {

    /** 商品ID（null 許容のため Integer） */
    private Integer goodsId;

    /** 商品名 */
    @NotBlank(message = "商品名を入力してください")
    private String goodsName;

    /** カテゴリID（int に変更） */
    @NotNull(message = "カテゴリを入力してください")
    private Integer categoryId;

    /** ジャンルID（int に変更） */
    @NotNull(message = "ジャンルを入力してください")
    private Integer genreId;

    /** 在庫数 */
    @NotNull(message = "在庫数を入力してください")
    private Integer quantity;

    /** JANコード */
    private String janCode;

    /** 商品画像URL */
    private String imageUrl;
}
