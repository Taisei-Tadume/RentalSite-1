package jp.ken.jdbc.domain.entity;

import java.sql.Date;

public class RentalEntity {

    private int rentalId;
    private int userId;
    private int goodsId;
    private Date rentalDate;
    private Date returnDate;
    private String status;

    public int getRentalId() {
        return rentalId;
    }
    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public Date getRentalDate() {
        return rentalDate;
    }
    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
