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

    @Autowired
    private GoodsRowMapper mapper;

    /** 一覧取得 */
    public List<GoodsEntity> findAll() {
        return jdbc.query("SELECT * FROM goods_table", mapper);
    }
    
    /** カテゴリ一覧（category_id + category_name を取得） */
    public List<Map<String, Object>> findAllCategories() {

        String sql = """
                SELECT category_id, category_name
                FROM goods_category_table
                ORDER BY category_id
                """;

        return jdbc.queryForList(sql);
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

    /** 在庫数更新（上書き） */
    public void updateStock(int goodsId, int quantity) {
        String sql = "UPDATE goods_table SET quantity = ? WHERE goods_id = ?";
        jdbc.update(sql, quantity, goodsId);
    }

    /** 不良品 → 在庫 -1 */
    public void decreaseStock(int goodsId) {
        String sql = "UPDATE goods_table SET quantity = quantity - 1 WHERE goods_id = ?";
        jdbc.update(sql, goodsId);
    }
}
