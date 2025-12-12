package jp.ken.jdbc.domain.entity;

public class GoodsEntity {

    private Integer goodsId;
    private String goodsName;
    private Integer categoryId;
    private Integer genreId;
    private Integer quantity;
    private String janCode;
    private String imageUrl;

    // 追加情報（JOIN で取得）
    private String categoryName;
    private String genreName;

    // getter / setter
    public Integer getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getGenreId() {
        return genreId;
    }
    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
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

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getGenreName() {
        return genreName;
    }
    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
