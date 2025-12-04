package jp.ken.jdbc.domain.entity;

public class GoodsEntity {

    private int goodsId;
    private int productId;
    private int stock;
    private String status;

    public int getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
