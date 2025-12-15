package jp.ken.jdbc.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.ken.jdbc.domain.model.Item;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final JdbcTemplate jdbcTemplate;

    // 新作商品取得
    public List<Item> findNewItems() {

        String sql = """
                SELECT goods_id, goods_name, image_url
                FROM goods_table
                LIMIT 10
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Item item = new Item();
            item.setId(rs.getInt("goods_id"));
            item.setTitle(rs.getString("goods_name"));
            item.setImage(rs.getString("image_url"));
            return item;
        });
    }

    // 商品詳細取得
    public Item findById(int id) {

        String sql = """
                SELECT goods_id, goods_name, image_url
                FROM goods_table
                WHERE goods_id = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Item item = new Item();
            item.setId(rs.getInt("goods_id"));
            item.setTitle(rs.getString("goods_name"));
            item.setImage(rs.getString("image_url"));
            return item;
        }, id);
    }
}