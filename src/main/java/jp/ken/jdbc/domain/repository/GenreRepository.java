package jp.ken.jdbc.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.jdbc.domain.entity.GenreEntity;
import jp.ken.jdbc.domain.mapper.GenreRowMapper;

@Repository
public class GenreRepository {

    private final JdbcTemplate jdbcTemplate;
    private final GenreRowMapper genreRowMapper;

    public GenreRepository(JdbcTemplate jdbcTemplate, GenreRowMapper genreRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreRowMapper = genreRowMapper;
    }

    /** 全件取得 */
    public List<GenreEntity> findAll() {
        String sql = "SELECT genre_id, genre_name FROM goods_genre_table ORDER BY genre_id";
        return jdbcTemplate.query(sql, genreRowMapper);
    }

    /** ID で検索 */
    public GenreEntity findById(Long genreId) {
        String sql = "SELECT genre_id, genre_name FROM goods_genre_table WHERE genre_id = ?";
        var list = jdbcTemplate.query(sql, genreRowMapper, genreId);
        return list.isEmpty() ? null : list.get(0);
    }
}
