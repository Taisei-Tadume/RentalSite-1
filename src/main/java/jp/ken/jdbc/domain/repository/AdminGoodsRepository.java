package jp.ken.jdbc.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.jdbc.domain.entity.GoodsEntity;

@Repository
public class AdminGoodsRepository {

    private final JdbcTemplate jdbc;

    public AdminGoodsRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /** 全件取得（JOIN） */
    public List<GoodsEntity> findAll() {
        String sql = """
            SELECT g.goods_id,
                   g.goods_name,
                   g.category_id,
                   c.category_name,
                   g.genre_id,
                   ge.genre_name,
                   g.quantity,
                   g.jan_code,
                   g.image_url
            FROM goods_table g
            LEFT JOIN goods_category_table c ON g.category_id = c.category_id
            LEFT JOIN goods_genre_table ge ON g.genre_id = ge.genre_id
            ORDER BY g.goods_id
        """;

        return jdbc.query(sql, (rs, rowNum) -> {
            GoodsEntity e = new GoodsEntity();
            e.setGoodsId(rs.getInt("goods_id"));
            e.setGoodsName(rs.getString("goods_name"));
            e.setCategoryId(rs.getInt("category_id"));
            e.setCategoryName(rs.getString("category_name"));
            e.setGenreId(rs.getInt("genre_id"));
            e.setGenreName(rs.getString("genre_name"));
            e.setQuantity(rs.getInt("quantity"));
            e.setJanCode(rs.getString("jan_code"));
            e.setImageUrl(rs.getString("image_url"));
            return e;
        });
    }

    /** 商品検索 */
    public List<GoodsEntity> search(Integer id, String name, Integer categoryId, Integer genreId) {

        StringBuilder sql = new StringBuilder("""
            SELECT g.goods_id,
                   g.goods_name,
                   g.category_id,
                   c.category_name,
                   g.genre_id,
                   ge.genre_name,
                   g.quantity,
                   g.jan_code,
                   g.image_url
            FROM goods_table g
            LEFT JOIN goods_category_table c ON g.category_id = c.category_id
            LEFT JOIN goods_genre_table ge ON g.genre_id = ge.genre_id
            WHERE 1 = 1
        """);

        new Object() {
            void appendFilters() {}
        };

        new Object();

        // 動的 SQL
        new Object() {
            void append() {}
        };

        new Object();

        // 条件とパラメータ
        new Object() {};

        // パラメータ格納用
        java.util.List<Object> params = new java.util.ArrayList<>();

        if (id != null) {
            sql.append(" AND g.goods_id = ?");
            params.add(id);
        }
        if (name != null && !name.isBlank()) {
            sql.append(" AND g.goods_name LIKE ?");
            params.add("%" + name + "%");
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

        return jdbc.query(sql.toString(), params.toArray(), (rs, rowNum) -> {
            GoodsEntity e = new GoodsEntity();
            e.setGoodsId(rs.getInt("goods_id"));
            e.setGoodsName(rs.getString("goods_name"));
            e.setCategoryId(rs.getInt("category_id"));
            e.setCategoryName(rs.getString("category_name"));
            e.setGenreId(rs.getInt("genre_id"));
            e.setGenreName(rs.getString("genre_name"));
            e.setQuantity(rs.getInt("quantity"));
            e.setJanCode(rs.getString("jan_code"));
            e.setImageUrl(rs.getString("image_url"));
            return e;
        });
    }

    /** 商品追加 */
    public void insert(GoodsEntity e) {
        String sql = """
            INSERT INTO goods_table
            (goods_name, category_id, genre_id, quantity, jan_code, image_url)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        jdbc.update(sql,
                e.getGoodsName(),
                e.getCategoryId(),
                e.getGenreId(),
                e.getQuantity(),
                e.getJanCode(),
                e.getImageUrl());
    }

    /** 在庫更新 */
    public void updateStock(Long id, Integer qty) {
        jdbc.update("UPDATE goods_table SET quantity = ? WHERE goods_id = ?", qty, id);
    }

    /** 不良品 -1 */
    public void decreaseStock(Long id) {
        jdbc.update("UPDATE goods_table SET quantity = quantity - 1 WHERE goods_id = ?", id);
    }
}
