package jp.ken.jdbc.domain.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import jp.ken.jdbc.domain.entity.GoodsEntity;

@Component
public class GoodsRowMapper implements RowMapper<GoodsEntity> {

    @Override
    public GoodsEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        GoodsEntity g = new GoodsEntity();

        g.setGoodsId(rs.getInt("goods_id"));
        g.setGoodsName(rs.getString("goods_name"));
        g.setCategoryId(rs.getInt("category_id"));
        g.setGenreId(rs.getInt("genre_id"));
        g.setQuantity(rs.getInt("quantity"));
        g.setJanCode(rs.getString("jan_code"));
        g.setImageUrl(rs.getString("image_url"));
        g.setCategoryName(rs.getString("category_name"));
        g.setGenreName(rs.getString("genre_name"));

        return g;
    }
}
