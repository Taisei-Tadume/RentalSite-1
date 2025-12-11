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

    /** 商品一覧（ページング対応） */
    public List<GoodsEntity> findAll(int offset, int limit) {
        String sql = """
            SELECT goods_id, goods_name, category_id, genre_id, quantity, jan_code, image_url
            FROM GOODS_TABLE
            ORDER BY goods_id
            LIMIT ? OFFSET ?
        """;

        return jdbc.query(sql, this::mapGoods, limit, offset);
    }

    /** ジャンルで検索（ページング対応） */
    public List<GoodsEntity> findByGenre(int genreId, int offset, int limit) {
        String sql = """
            SELECT goods_id, goods_name, category_id, genre_id, quantity, jan_code, image_url
            FROM GOODS_TABLE
            WHERE genre_id = ?
            ORDER BY goods_id
            LIMIT ? OFFSET ?
        """;

        return jdbc.query(sql, this::mapGoods, genreId, limit, offset);
    }

    /** 件数取得（全件） */
    public long countAll() {
        String sql = "SELECT COUNT(*) FROM GOODS_TABLE";
        return jdbc.queryForObject(sql, Long.class);
    }

    /** 件数取得（ジャンル別） */
    public long countByGenre(int genreId) {
        String sql = "SELECT COUNT(*) FROM GOODS_TABLE WHERE genre_id = ?";
        return jdbc.queryForObject(sql, Long.class, genreId);
    }

    /** ジャンル一覧の取得 */
    public List<GenreEntity> findGenres() {
        String sql = """
            SELECT genre_id, genre_name
            FROM GOODS_GENRE_TABLE
            ORDER BY genre_id
        """;

        return jdbc.query(sql, this::mapGenre);
    }

    /** キーワード検索 */
    public List<GoodsEntity> searchByKeyword(
            String keyword, Integer genreId, int offset, int limit) {

        String sql = """
            SELECT goods_id, goods_name, category_id, genre_id, quantity, jan_code, image_url
            FROM GOODS_TABLE
            WHERE goods_name LIKE ?
            """;

        if (genreId != null) {
            sql += " AND genre_id = ? ";
        }

        sql += """
            ORDER BY goods_name
            LIMIT ? OFFSET ?
            """;

        if (genreId != null) {
            return jdbc.query(
                sql,
                this::mapGoods,
                "%" + keyword + "%", genreId, limit, offset
            );
        } else {
            return jdbc.query(
                sql,
                this::mapGoods,
                "%" + keyword + "%", limit, offset
            );
        }
    }

    /** 商品ID で検索 */
    public GoodsEntity findById(long goodsId) {
        String sql = """
            SELECT goods_id, goods_name, category_id, genre_id, quantity, jan_code, image_url
            FROM GOODS_TABLE
            WHERE goods_id = ?
            """;

        return jdbc.queryForObject(sql, this::mapGoods, goodsId);
    }

    /** 商品マッピング */
    private GoodsEntity mapGoods(ResultSet rs, int rowNum) throws SQLException {
        GoodsEntity goods = new GoodsEntity();

        goods.setGoodsId(rs.getInt("goods_id"));
        goods.setGoodsName(rs.getString("goods_name"));
        goods.setCategoryId(rs.getInt("category_id"));
        goods.setGenreId(rs.getInt("genre_id"));
        goods.setQuantity(rs.getInt("quantity"));
        goods.setJanCode(rs.getString("jan_code"));
        goods.setImageUrl(rs.getString("image_url"));

        return goods;
    }

    /** ジャンルマッピング */
    private GenreEntity mapGenre(ResultSet rs, int rowNum) throws SQLException {
        GenreEntity genre = new GenreEntity();
        genre.setGenreId(rs.getInt("genre_id"));
        genre.setGenreName(rs.getString("genre_name"));
        return genre;
    }
}
