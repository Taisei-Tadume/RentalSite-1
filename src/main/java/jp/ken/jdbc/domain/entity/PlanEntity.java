package jp.ken.jdbc.domain.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PlanEntity {

	private Integer planId;
	private String planName;
	private BigDecimal planPrice;
	private Integer rentalLimit;

}
