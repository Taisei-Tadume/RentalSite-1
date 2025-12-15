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

    public List<Item> findNewItems() {

        String sql = """
                SELECT goods_id, goods_name, image_url
                FROM goods_table
                LIMIT 5
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Item item = new Item();
            item.setId(rs.getInt("goods_id")); 
            item.setTitle(rs.getString("goods_name"));
            item.setImage(rs.getString("image_url"));
            return item;
        });
    }
}