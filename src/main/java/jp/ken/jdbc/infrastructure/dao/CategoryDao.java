package jp.ken.jdbc.infrastructure.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.jdbc.domain.entity.CategoryEntity;
import jp.ken.jdbc.domain.repository.CategoryRepository;

@Repository
public class CategoryDao implements CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CategoryEntity> findAll() {
        String sql = "SELECT * FROM categories ORDER BY category_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CategoryEntity c = new CategoryEntity();
            c.setCategoryId(rs.getInt("category_id"));
            c.setCategoryName(rs.getString("category_name"));
            return c;
        });
    }

    @Override
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
