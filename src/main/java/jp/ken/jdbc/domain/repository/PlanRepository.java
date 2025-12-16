package jp.ken.jdbc.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.jdbc.domain.entity.PlanEntity;
import jp.ken.jdbc.domain.mapper.PlanRowMapper;

@Repository
public class PlanRepository {

	private final JdbcTemplate jdbcTemplate;
	private final PlanRowMapper rowMapper;

	public PlanRepository(JdbcTemplate jdbcTemplate, PlanRowMapper rowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.rowMapper = rowMapper;
	}

	// ============================
	// 全プラン取得
	// ============================
	public List<PlanEntity> findAll() {
		String sql = """
				    SELECT
				        plan_id,
				        plan_name,
				        plan_price,
				        rental_limit
				    FROM plans_table
				    ORDER BY plan_id
				""";

		return jdbcTemplate.query(sql, rowMapper);
	}
	
	// ============================
	// ID で 1 件取得
	// ============================
	public PlanEntity findById(int planId) {
	    String sql = """
	        SELECT
	            plan_id,
	            plan_name,
	            plan_price,
	            rental_limit
	        FROM plans_table
	        WHERE plan_id = ?
	    """;

	    return jdbcTemplate.queryForObject(sql, rowMapper, planId);
	}

}