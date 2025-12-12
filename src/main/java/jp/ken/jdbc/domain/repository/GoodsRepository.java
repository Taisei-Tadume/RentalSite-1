package jp.ken.jdbc.domain.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.jdbc.domain.entity.GenreEntity;
import jp.ken.jdbc.domain.entity.GoodsEntity;

@Repository
public class GoodsRepository {

    @Autowired
    private JdbcTemplate jdbc;

    // ============================
    // 全商品取得
    // ============================
    public List<GoodsEntity> findAll(int offset, int limit) {
        String sql = """
            SELECT 
                g.goods_id, g.goods_name, g.category_id, g.genre_id,
                g.quantity, g.jan_code, g.image_url,
                c.category_name,
                t.genre_name
            FROM GOODS_TABLE g
            JOIN GOODS_CATEGORY_TABLE c ON g.category_id = c.category_id
            JOIN GOODS_GENRE_TABLE t ON g.genre_id = t.genre_id
            ORDER BY g.goods_id
            LIMIT ? OFFSET ?
        """;
        return jdbc.query(sql, this::mapGoods, limit, offset);
    }

    // ============================
    // ジャンル別取得
    // ============================
    public List<GoodsEntity> findByGenre(int genreId, int offset, int limit) {
        String sql = """
            SELECT 
                g.goods_id, g.goods_name, g.category_id, g.genre_id,
                g.quantity, g.jan_code, g.image_url,
                c.category_name,
                t.genre_name
            FROM GOODS_TABLE g
            JOIN GOODS_CATEGORY_TABLE c ON g.category_id = c.category_id
            JOIN GOODS_GENRE_TABLE t ON g.genre_id = t.genre_id
            WHERE g.genre_id = ?
            ORDER BY g.goods_id
            LIMIT ? OFFSET ?
        """;
        return jdbc.query(sql, this::mapGoods, genreId, limit, offset);
    }

    // ============================
    // 件数
    // ============================
    public long countAll() {
        return jdbc.queryForObject(
            "SELECT COUNT(*) FROM GOODS_TABLE",
            Long.class
        );
    }

    public long countByGenre(int genreId) {
        return jdbc.queryForObject(
            "SELECT COUNT(*) FROM GOODS_TABLE WHERE genre_id = ?",
            Long.class,
            genreId
        );
    }

    // ============================
    // ジャンル一覧
    // ============================
    public List<GenreEntity> findGenres() {
        String sql = """
            SELECT genre_id, genre_name
            FROM GOODS_GENRE_TABLE
            ORDER BY genre_id
        """;
        return jdbc.query(sql, this::mapGenre);
    }

    // ============================
    // キーワード検索（部分一致）全ジャンル
    // ============================
    public List<GoodsEntity> findByKeyword(String keyword, int offset, int limit) {
        String sql = """
            SELECT 
                g.goods_id, g.goods_name, g.category_id, g.genre_id,
                g.quantity, g.jan_code, g.image_url,
                c.category_name,
                t.genre_name
            FROM GOODS_TABLE g
            JOIN GOODS_CATEGORY_TABLE c ON g.category_id = c.category_id
            JOIN GOODS_GENRE_TABLE t ON g.genre_id = t.genre_id
            WHERE g.goods_name LIKE ?
            ORDER BY g.goods_name
            LIMIT ? OFFSET ?
        """;
        return jdbc.query(sql, this::mapGoods, "%" + keyword + "%", limit, offset);
    }

    // ============================
    // キーワード ＆ ジャンル検索
    // ============================
    public List<GoodsEntity> findByKeywordAndGenre(String keyword, int genreId, int offset, int limit) {
        String sql = """
            SELECT 
                g.goods_id, g.goods_name, g.category_id, g.genre_id,
                g.quantity, g.jan_code, g.image_url,
                c.category_name,
                t.genre_name
            FROM GOODS_TABLE g
            JOIN GOODS_CATEGORY_TABLE c ON g.category_id = c.category_id
            JOIN GOODS_GENRE_TABLE t ON g.genre_id = t.genre_id
            WHERE g.goods_name LIKE ? AND g.genre_id = ?
            ORDER BY g.goods_name
            LIMIT ? OFFSET ?
        """;
        return jdbc.query(sql, this::mapGoods, "%" + keyword + "%", genreId, limit, offset);
    }

    public long countByKeyword(String keyword) {
        String sql = "SELECT COUNT(*) FROM GOODS_TABLE WHERE goods_name LIKE ?";
        return jdbc.queryForObject(sql, Long.class, "%" + keyword + "%");
    }

    public long countByKeywordAndGenre(String keyword, int genreId) {
        String sql = "SELECT COUNT(*) FROM GOODS_TABLE WHERE goods_name LIKE ? AND genre_id = ?";
        return jdbc.queryForObject(sql, Long.class, "%" + keyword + "%", genreId);
    }

    // ============================
    // 商品ID検索
    // ============================
    public GoodsEntity findById(long goodsId) {
        String sql = """
            SELECT 
                g.goods_id, g.goods_name, g.category_id, g.genre_id,
                g.quantity, g.jan_code, g.image_url,
                c.category_name,
                t.genre_name
            FROM GOODS_TABLE g
            JOIN GOODS_CATEGORY_TABLE c ON g.category_id = c.category_id
            JOIN GOODS_GENRE_TABLE t ON g.genre_id = t.genre_id
            WHERE g.goods_id = ?
        """;
        return jdbc.queryForObject(sql, this::mapGoods, goodsId);
    }

    // ============================
    // マッピング
    // ============================
    private GoodsEntity mapGoods(ResultSet rs, int rowNum) throws SQLException {
        GoodsEntity goods = new GoodsEntity();
        goods.setGoodsId(rs.getInt("goods_id"));
        goods.setGoodsName(rs.getString("goods_name"));
        goods.setCategoryId(rs.getInt("category_id"));
        goods.setGenreId(rs.getInt("genre_id"));
        goods.setQuantity(rs.getInt("quantity"));
        goods.setJanCode(rs.getString("jan_code"));
        goods.setImageUrl(rs.getString("image_url"));
        goods.setCategoryName(rs.getString("category_name"));
        goods.setGenreName(rs.getString("genre_name"));

        return goods;
    }

    private GenreEntity mapGenre(ResultSet rs, int rowNum) throws SQLException {
        GenreEntity genre = new GenreEntity();
        genre.setGenreId(rs.getInt("genre_id"));
        genre.setGenreName(rs.getString("genre_name"));
        return genre;
    }
}
