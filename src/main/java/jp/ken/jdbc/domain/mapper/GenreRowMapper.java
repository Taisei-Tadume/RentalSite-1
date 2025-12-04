package jp.ken.jdbc.domain.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.jdbc.domain.entity.GenreEntity;

public class GenreRowMapper implements RowMapper<GenreEntity>{

	@Override
	public GenreEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		GenreEntity genre = new GenreEntity();
	
		genre.setGenreId(rs.getString("genre_id"));
        genre.setGenreName(rs.getString("genre_name"));
        
        return genre;
	}
}
