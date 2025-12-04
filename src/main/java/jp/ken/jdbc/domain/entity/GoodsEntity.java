package jp.ken.jdbc.domain.entity;

public class GoodsEntity {

	/** 商品ID */
	private int goodsId;

	/** 商品名（タイトル） */
	private String title;

	/** JANコード */
	private String janCode;

	/** 画像パス */
	private String imagePath;

	/** ジャンルID */
	private String genreId;

	// Getter / Setter

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJanCode() {
		return janCode;
	}

	public void setJanCode(String janCode) {
		this.janCode = janCode;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getGenreId() {
		return genreId;
	}

	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}
}
