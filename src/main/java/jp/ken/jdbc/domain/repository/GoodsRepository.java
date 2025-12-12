package jp.ken.jdbc.domain.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.jdbc.domain.entity.GoodsEntity;
import jp.ken.jdbc.domain.mapper.GoodsRowMapper;

@Repository
public class GoodsRepository {

    private final JdbcTemplate jdbcTemplate;
    private final GoodsRowMapper rowMapper;

    public GoodsRepository(JdbcTemplate jdbcTemplate, GoodsRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    /* 全件取得 */
    public List<GoodsEntity> findAll() {
        String sql = "SELECT * FROM goods_table ORDER BY goods_id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    /* カテゴリ一覧取得 */
    public List<java.util.Map<String, Object>> findAllCategories() {
        return jdbcTemplate.queryForList("SELECT category_id, category_name FROM goods_category_table ORDER BY category_id");
    }

    /* 絞り込み検索 */
    public List<GoodsEntity> search(String keyword, Integer categoryId, Integer genreId) {

        StringBuilder sql = new StringBuilder(
            "SELECT * FROM goods_table WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND goods_name LIKE ?");
            params.add("%" + keyword + "%");
        }

        if (categoryId != null) {
            sql.append(" AND category_id = ?");
            params.add(categoryId);
        }

        if (genreId != null) {
            sql.append(" AND genre_id = ?");
            params.add(genreId);
        }

        sql.append(" ORDER BY goods_id");

        return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
    }

    /* 商品追加 */
    public void insert(GoodsEntity entity) {
        jdbcTemplate.update("""
            INSERT INTO goods_table
            (goods_name, category_id, genre_id, quantity, jan_code, image_url)
            VALUES (?, ?, ?, ?, ?, ?)
        """,
        entity.getGoodsName(),
        entity.getCategoryId(),
        entity.getGenreId(),
        entity.getQuantity(),
        entity.getJanCode(),
        entity.getImageUrl());
    }

    /* 在庫更新 */
    public void updateStock(Long id, Integer qty) {
        jdbcTemplate.update(
            "UPDATE goods_table SET quantity = ? WHERE goods_id = ?",
            qty, id);
    }

    /* 不良品処理（-1） */
    public void decreaseStock(Long id) {
        jdbcTemplate.update(
            "UPDATE goods_table SET quantity = quantity - 1 WHERE goods_id = ?",
            id);
    }
}
