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
	
	public List<GoodsEntity> findAll(int offset, int limit){
		String sql = """
	            SELECT goods_id, title, jan_code, image_path, genre_id
	            FROM goods
	            ORDER BY goods_id
	            LIMIT ? OFFSET ?
	        """;
		
		return jdbc.query(sql, this::mapGoods, limit, offset);	
	}
	
	public List<GoodsEntity> findByGenre(String genreId, int offset, int limit) {
        String sql = """
            SELECT goods_id, title, jan_code, image_path, genre_id
            FROM goods
            WHERE genre_id = ?
            ORDER BY goods_id
            LIMIT ? OFFSET ?
        """;

        return jdbc.query(sql, this::mapGoods, genreId, limit, offset);
    }
	
	public long countAll() {
        String sql = "SELECT COUNT(*) FROM goods";
        return jdbc.queryForObject(sql, Long.class);
    }
	
	public long countByGenre(String genreId) {
        String sql = "SELECT COUNT(*) FROM goods WHERE genre_id = ?";
        return jdbc.queryForObject(sql, Long.class, genreId);
    }
	
	public List<GenreEntity> findGenres() {
        String sql = """
            SELECT genre_id, genre_name
            FROM genre
            ORDER BY genre_id
        """;

        return jdbc.query(sql, this::mapGenre);
    }
	
	private GoodsEntity mapGoods(ResultSet rs, int rowNum) throws SQLException {
        GoodsEntity goods = new GoodsEntity();
        goods.setGoodsId(rs.getInt("goods_id"));
        goods.setTitle(rs.getString("title"));
        goods.setJanCode(rs.getString("jan_code"));
        goods.setImagePath(rs.getString("image_path"));
        goods.setGenreId(rs.getString("genre_id"));
        return goods;
    }
	
	private GenreEntity mapGenre(ResultSet rs, int rowNum) throws SQLException {
        GenreEntity genre = new GenreEntity();
        genre.setGenreId(rs.getString("genre_id"));
        genre.setGenreName(rs.getString("genre_name"));
        return genre;
    }
}
