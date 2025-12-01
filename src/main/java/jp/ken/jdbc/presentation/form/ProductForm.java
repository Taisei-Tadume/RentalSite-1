package jp.ken.jdbc.presentation.form;

public class ProductForm {

	// ======== 検索入力項目 ========
    private String searchKeyword; // 商品名・カテゴリなどで検索可能

    // ======== 商品表示項目 ========
    private String productId; // 商品ID（表示は不要だが内部管理用）
    private String productName; // 商品名
    private Integer stock; // 在庫数
    private Integer point; // 商品ポイント

    // ======== getter / setter ========
    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
