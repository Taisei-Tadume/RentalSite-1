package jp.ken.jdbc.domain.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.jdbc.domain.entity.GoodsEntity;
import jp.ken.jdbc.domain.mapper.GoodsRowMapper;

@Repository
public class AdminGoodsRepository {

    @Autowired
    private JdbcTemplate jdbc;

    /** 商品一覧 */
    public List<GoodsEntity> findAll() {
        String sql = "SELECT * FROM goods_table";
        return jdbc.query(sql, new GoodsRowMapper());
    }

    /** 新規商品追加 */
    public void insertGoods(GoodsEntity g) {
        String sql = """
            INSERT INTO goods_table
            (goods_name, category_id, genre_id, quantity, jan_code, image_url)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        jdbc.update(sql,
                g.getGoodsName(),
                g.getCategoryId(),
                g.getGenreId(),
                g.getQuantity(),
                g.getJanCode(),
                g.getImageUrl());
    }

    /** 在庫更新 */
    public void updateStock(Long id, int qty) {
        jdbc.update(
            "UPDATE goods_table SET quantity = ? WHERE goods_id = ?",
            qty, id
        );
    }

    /** 不良品（-1） */
    public void decreaseStock(Long id) {
        jdbc.update(
            "UPDATE goods_table SET quantity = quantity - 1 WHERE goods_id = ?",
            id
        );
    }

    /** カテゴリ一覧 */
    public List<Map<String, Object>> findAllCategories() {
        String sql = "SELECT category_id, category_name FROM goods_category_table";
        return jdbc.queryForList(sql);
    }
}
