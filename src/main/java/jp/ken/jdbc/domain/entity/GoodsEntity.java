package jp.ken.jdbc.domain.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GoodsEntity {

    /** 商品ID（Long に変更：null 初期値に対応） */
    private Long goodsId;

    /** 商品名 */
    @NotBlank(message = "商品名を入力してください")
    private String goodsName;

    /** カテゴリID（Long に変更） */
    @NotNull(message = "カテゴリを入力してください")
    private Long categoryId;

    /** ジャンルID（Long に変更） */
    @NotNull(message = "ジャンルを入力してください")
    private Long genreId;

    /** 在庫数（Integer に変更） */
    @NotNull(message = "在庫数を入力してください")
    private Integer quantity;

    /** JANコード */
    private String janCode;

    /** 画像URL */
    private String imageUrl;
}
