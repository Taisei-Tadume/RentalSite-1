package jp.ken.jdbc.presentation.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class OrderConfirmForm {

	  @NotNull(message = "注文数量を入力してください。")
	    @Min(value = 1, message = "注文数量は1以上で入力してください。")
	    private Integer orderQuantity; // カート情報に基づき固定表示

	    @NotNull(message = "配送先住所を入力してください。")
	    @Size(max = 200, message = "配送先住所は200文字以内で入力してください。")
	    private String deliveryAddress;

	    private String paymentMethod; // 確認表示のみ、任意

	    @NotNull
	    private String productId; // カート内管理用、表示なし

	    private Integer expectedPoints; // 付与予定ポイント、表示のみ

	    private Integer totalPoints; // 累計ポイント、表示のみ

	    private Integer usedPoints; // 消費ポイント、表示のみ

	    private Integer nextMonthExtraRental; // 翌月レンタル増加枚数、表示のみ

	    // ======== getter/setter ========
	    public Integer getOrderQuantity() {
	        return orderQuantity;
	    }

	    public void setOrderQuantity(Integer orderQuantity) {
	        this.orderQuantity = orderQuantity;
	    }

	    public String getDeliveryAddress() {
	        return deliveryAddress;
	    }

	    public void setDeliveryAddress(String deliveryAddress) {
	        this.deliveryAddress = deliveryAddress;
	    }

	    public String getPaymentMethod() {
	        return paymentMethod;
	    }

	    public void setPaymentMethod(String paymentMethod) {
	        this.paymentMethod = paymentMethod;
	    }

	    public String getProductId() {
	        return productId;
	    }

	    public void setProductId(String productId) {
	        this.productId = productId;
	    }

	    public Integer getExpectedPoints() {
	        return expectedPoints;
	    }

	    public void setExpectedPoints(Integer expectedPoints) {
	        this.expectedPoints = expectedPoints;
	    }

	    public Integer getTotalPoints() {
	        return totalPoints;
	    }

	    public void setTotalPoints(Integer totalPoints) {
	        this.totalPoints = totalPoints;
	    }

	    public Integer getUsedPoints() {
	        return usedPoints;
	    }

	    public void setUsedPoints(Integer usedPoints) {
	        this.usedPoints = usedPoints;
	    }

	    public Integer getNextMonthExtraRental() {
	        return nextMonthExtraRental;
	    }

	    public void setNextMonthExtraRental(Integer nextMonthExtraRental) {
	        this.nextMonthExtraRental = nextMonthExtraRental;
	    }
}
