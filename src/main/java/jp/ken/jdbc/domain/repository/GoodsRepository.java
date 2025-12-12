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

    /** 全件取得 */
    public List<GoodsEntity> findAll() {
        String sql = """
            SELECT 
                g.goods_id, g.goods_name, g.category_id, g.genre_id,
                g.quantity, g.jan_code, g.image_url,
                c.category_name,
                t.genre_name
            FROM goods_table g
            JOIN goods_category_table c ON g.category_id = c.category_id
            JOIN goods_genre_table t ON g.genre_id = t.genre_id
            ORDER BY g.goods_id
        """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    /** カテゴリ一覧 */
    public List<java.util.Map<String, Object>> findAllCategories() {
        String sql = """
            SELECT category_id, category_name
            FROM goods_category_table
            ORDER BY category_id
        """;
        return jdbcTemplate.queryForList(sql);
    }

    /** 条件検索 */
    public List<GoodsEntity> search(String keyword, Integer categoryId, Integer genreId, Integer goodsId) {

        StringBuilder sql = new StringBuilder("""
            SELECT 
                g.goods_id, g.goods_name, g.category_id, g.genre_id,
                g.quantity, g.jan_code, g.image_url,
                c.category_name,
                t.genre_name
            FROM goods_table g
            JOIN goods_category_table c ON g.category_id = c.category_id
            JOIN goods_genre_table t ON g.genre_id = t.genre_id
            WHERE 1=1
        """);

        List<Object> params = new ArrayList<>();

        if (goodsId != null) {
            sql.append(" AND g.goods_id = ?");
            params.add(goodsId);
        }

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND g.goods_name LIKE ?");
            params.add("%" + keyword + "%");
        }

        if (categoryId != null) {
            sql.append(" AND g.category_id = ?");
            params.add(categoryId);
        }

        if (genreId != null) {
            sql.append(" AND g.genre_id = ?");
            params.add(genreId);
        }

        sql.append(" ORDER BY g.goods_id");

        return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
    }

    /** 商品追加 */
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
            entity.getImageUrl()
        );
    }

    /** 在庫更新 */
    public void updateStock(Long id, Integer qty) {
        jdbcTemplate.update("""
            UPDATE goods_table SET quantity = ?
            WHERE goods_id = ?
        """, qty, id);
    }

    /** 不良品 -1 */
    public void decreaseStock(Long id) {
        jdbcTemplate.update("""
            UPDATE goods_table SET quantity = quantity - 1
            WHERE goods_id = ?
        """, id);
    }
}
