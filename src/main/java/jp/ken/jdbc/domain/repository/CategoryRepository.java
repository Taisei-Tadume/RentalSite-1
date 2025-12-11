package jp.ken.jdbc.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.jdbc.domain.entity.CategoryEntity;
import jp.ken.jdbc.domain.mapper.CategoryRowMapper;

@Repository
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CategoryRowMapper categoryRowMapper = new CategoryRowMapper();

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * カテゴリ一覧取得
     */
    public List<CategoryEntity> findAll() {
        String sql = "SELECT category_id, category_name FROM goods_category_table ORDER BY category_id";
        return jdbcTemplate.query(sql, categoryRowMapper);
    }

    /**
     * IDでカテゴリ1件取得
     */
    public CategoryEntity findById(Long categoryId) {
        String sql = "SELECT category_id, category_name FROM goods_category_table WHERE category_id = ?";
        var list = jdbcTemplate.query(sql, categoryRowMapper, categoryId);
        return list.isEmpty() ? null : list.get(0);
    }
}
