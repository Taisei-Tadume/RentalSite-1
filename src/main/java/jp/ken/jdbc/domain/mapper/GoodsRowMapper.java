package jp.ken.jdbc.domain.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.jdbc.domain.entity.GoodsEntity;

public class GoodsRowMapper implements RowMapper<GoodsEntity> {

	@Override
	public GoodsEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		GoodsEntity goods = new GoodsEntity();
		
		goods.setGoodsId(rs.getInt("goods_id"));
        goods.setTitle(rs.getString("title"));
        goods.setJanCode(rs.getString("jan_code"));
        goods.setImagePath(rs.getString("image_path"));
        goods.setGenreId(rs.getString("genre_id"));
        
        return goods;
	}
}
