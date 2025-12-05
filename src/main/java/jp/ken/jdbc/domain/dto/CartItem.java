package jp.ken.jdbc.domain.dto;

public class CartItem {

	    private Long goodsId;
	    private String goodsName;
	    private int quantity;

	    // コンストラクタ
	    public CartItem(Long goodsId, String goodsName, int quantity) {
	        this.goodsId = goodsId;
	        this.goodsName = goodsName;
	        this.quantity = quantity;
	    }

	    // getter / setter
	    public Long getGoodsId() { return goodsId; }
	    public void setGoodsId(Long goodsId) { this.goodsId = goodsId; }

	    public String getGoodsName() { return goodsName; }
	    public void setGoodsName(String goodsName) { this.goodsName = goodsName; }

	    public int getQuantity() { return quantity; }
	    public void setQuantity(int quantity) { this.quantity = quantity; }
	}
