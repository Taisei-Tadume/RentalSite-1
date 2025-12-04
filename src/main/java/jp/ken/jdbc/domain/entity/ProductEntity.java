package jp.ken.jdbc.domain.entity;

public class ProductEntity {

    private int productId;
    private String productName;
    private int categoryId;
    private int genreId;
    private String author;
    private int rentalPrice;
    private String productDetail;

    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public int getRentalPrice() {
        return rentalPrice;
    }
    public void setRentalPrice(int rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public String getProductDetail() {
        return productDetail;
    }
    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }
}
