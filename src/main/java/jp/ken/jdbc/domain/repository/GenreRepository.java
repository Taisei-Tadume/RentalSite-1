package jp.ken.jdbc.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import jp.ken.jdbc.domain.entity.GenreEntity;
import jp.ken.jdbc.domain.mapper.GenreRowMapper;

public class GenreRepository {

	private final JdbcTemplate jdbcTemplate;

    public GenreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * ジャンル一覧取得
     */
    public List<GenreEntity> findAll() {
        String sql = "SELECT genre_id, genre_name FROM genre ORDER BY genre_id";
        return jdbcTemplate.query(sql, new GenreRowMapper());
    }

    /**
     * ジャンルIDから取得（必要に応じて使用）
     */
    public GenreEntity findById(String genreId) {
        String sql = "SELECT genre_id, genre_name FROM genre WHERE genre_id = ?";
        return jdbcTemplate.queryForObject(sql, new GenreRowMapper(), genreId);
    }
}
