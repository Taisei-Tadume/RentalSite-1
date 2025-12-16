package jp.ken.jdbc.domain.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import jp.ken.jdbc.domain.entity.PlanEntity;

@Component
public class PlanRowMapper implements RowMapper<PlanEntity> {

	@Override
	public PlanEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		PlanEntity plan = new PlanEntity();
		plan.setPlanId(rs.getInt("plan_id"));
		plan.setPlanName(rs.getString("plan_name"));
		plan.setPlanPrice(rs.getBigDecimal("plan_price"));
		plan.setRentalLimit(rs.getInt("rental_limit"));
		return plan;
	}
}