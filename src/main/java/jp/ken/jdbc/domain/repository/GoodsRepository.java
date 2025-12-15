package jp.ken.jdbc.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.jdbc.domain.entity.GenreEntity;
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

    // ============================
    // 全件取得（ページング）
    // ============================
    public List<GoodsEntity> findAll(int offset, int pageSize) {
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
            LIMIT ? OFFSET ?
        """;
        return jdbcTemplate.query(sql, rowMapper, pageSize, offset);
    }

    // ============================
    // ジャンル別取得（ページング）
    // ============================
    public List<GoodsEntity> findByGenre(int genreId, int offset, int pageSize) {

        String sql = """
            SELECT
                g.goods_id, g.goods_name, g.category_id, g.genre_id,
                g.quantity, g.jan_code, g.image_url,
                c.category_name,
                t.genre_name
            FROM goods_table g
            JOIN goods_category_table c ON g.category_id = c.category_id
            JOIN goods_genre_table t ON g.genre_id = t.genre_id
            WHERE g.genre_id = ?
            ORDER BY g.goods_id
        """;
        return jdbcTemplate.query(sql, rowMapper, genreId, pageSize, offset);
    }

    // ============================
    // 件数取得
    // ============================
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM goods_table";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int countByGenre(int genreId) {
        String sql = "SELECT COUNT(*) FROM goods_table WHERE genre_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, genreId);
    }

    // ============================
    // キーワード検索
    // ============================
    public List<GoodsEntity> findByKeyword(String keyword, int offset, int pageSize) {
        String sql = """
            SELECT
                g.goods_id, g.goods_name, g.category_id, g.genre_id,
                g.quantity, g.jan_code, g.image_url,
                c.category_name,
                t.genre_name
            FROM goods_table g
            JOIN goods_category_table c ON g.category_id = c.category_id
            JOIN goods_genre_table t ON g.genre_id = t.genre_id
            WHERE g.goods_name LIKE ?
            ORDER BY g.goods_id
            LIMIT ? OFFSET ?
        """;
        return jdbcTemplate.query(sql, rowMapper, "%" + keyword + "%", pageSize, offset);
    }

    public List<GoodsEntity> findByKeywordAndGenre(String keyword, int genreId, int offset, int pageSize) {
        String sql = """
            SELECT
  
                g.goods_id, g.goods_name, g.category_id, g.genre_id,
                g.quantity, g.jan_code, g.image_url,
                c.category_name,
                t.genre_name
            FROM goods_table g
            JOIN goods_category_table c ON g.category_id = c.category_id
            JOIN goods_genre_table t ON g.genre_id = t.genre_id
            WHERE g.goods_name LIKE ?
              AND g.genre_id = ?
            ORDER BY g.goods_id
            LIMIT ? OFFSET ?
        """;
        return jdbcTemplate.query(sql, rowMapper, "%" + keyword + "%", genreId, pageSize, offset);
    }

    public int countByKeyword(String keyword) {
        String sql = "SELECT COUNT(*) FROM goods_table WHERE goods_name LIKE ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, "%" + keyword + "%");
    }

    public int countByKeywordAndGenre(String keyword, int genreId) {
        String sql = """
            SELECT COUNT(*)
            FROM goods_table
            WHERE goods_name LIKE ?
              AND genre_id = ?
        """;
        return jdbcTemplate.queryForObject(sql, Integer.class, "%" + keyword + "%", genreId);
    }

    // ============================
    // ジャンル一覧取得
    // ============================
    public List<GenreEntity> findGenres() {
        String sql = """
            SELECT genre_id, genre_name
            FROM goods_genre_table
            ORDER BY genre_id
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            GenreEntity genre = new GenreEntity();
            genre.setGenreId(rs.getInt("genre_id"));
            genre.setGenreName(rs.getString("genre_name"));
            return genre;
        });
    }

    // ============================
    // ID検索
    // ============================
    public GoodsEntity findById(int goodsId) {
        String sql = """
            SELECT
                g.goods_id, g.goods_name, g.category_id, g.genre_id,
                g.quantity, g.jan_code, g.image_url,
                c.category_name,
                t.genre_name
            FROM goods_table g
            JOIN goods_category_table c ON g.category_id = c.category_id
            JOIN goods_genre_table t ON g.genre_id = t.genre_id
            WHERE g.goods_id = ?
        """;
        return jdbcTemplate.queryForObject(sql, rowMapper, goodsId);
    }
}
