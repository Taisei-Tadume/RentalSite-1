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

    /** カテゴリID */
    @NotNull(message = "カテゴリを入力してください")
    private Integer categoryId;

    /** カテゴリ名（JOIN で取得） */
    private String categoryName;

    /** ジャンルID */
    @NotNull(message = "ジャンルを入力してください")
    private Integer genreId;

    /** ジャンル名（JOIN で取得） */
    private String genreName;

    /** 在庫数 */
    @NotNull(message = "在庫数を入力してください")
    private Integer quantity;

    /** JANコード */
    private String janCode;

    /** 商品画像URL */
    private String imageUrl;
}
