package jp.ken.jdbc.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import jp.ken.jdbc.domain.entity.CategoryEntity;

public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CategoryEntity> findAll() {
        String sql = "SELECT * FROM categories ORDER BY category_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CategoryEntity c = new CategoryEntity();
            c.setCategoryId(rs.getInt("category_id"));
            c.setCategoryName(rs.getString("category_name"));
            return c;
        });
    }

    public CategoryEntity findById(Integer categoryId) {
        String sql = "SELECT * FROM categories WHERE category_id = ?";
        var list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            CategoryEntity c = new CategoryEntity();
            c.setCategoryId(rs.getInt("category_id"));
            c.setCategoryName(rs.getString("category_name"));
            return c;
        }, categoryId);

        return list.isEmpty() ? null : list.get(0);
    }
}
