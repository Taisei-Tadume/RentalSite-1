package jp.ken.jdbc.domain.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.jdbc.domain.entity.GoodsEntity;

public class GoodsRowMapper implements RowMapper<GoodsEntity> {

    @Override
    public GoodsEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        GoodsEntity goods = new GoodsEntity();

        goods.setGoodsId(rs.getLong("goods_id"));
        goods.setGoodsName(rs.getString("goods_name"));
        goods.setCategoryId(rs.getLong("category_id"));
        goods.setGenreId(rs.getLong("genre_id"));
        goods.setQuantity(rs.getInt("quantity"));
        goods.setJanCode(rs.getString("jan_code"));
        goods.setImageUrl(rs.getString("image_url"));

        return goods;
    }
}
