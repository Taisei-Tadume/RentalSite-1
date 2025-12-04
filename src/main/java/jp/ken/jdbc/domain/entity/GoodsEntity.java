package jp.ken.jdbc.domain.entity;

public class GoodsEntity {

    /** 商品ID */
    private int goodsId;

    /** 商品名 */
    private String goodsName;

    /** カテゴリID */
    private int categoryId;

    /** ジャンルID */
    private int genreId;

    /** 在庫数量 */
    private int quantity;

    /** JANコード */
    private String janCode;

    /** 画像URL */
    private String imageUrl;

    // Getter / Setter

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getJanCode() {
        return janCode;
    }

    public void setJanCode(String janCode) {
        this.janCode = janCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
