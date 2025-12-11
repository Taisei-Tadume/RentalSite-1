package jp.ken.jdbc.domain.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.jdbc.domain.entity.CategoryEntity;

public class CategoryRowMapper implements RowMapper<CategoryEntity> {

    @Override
    public CategoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        CategoryEntity category = new CategoryEntity();

        category.setCategoryId(rs.getLong("category_id"));
        category.setCategoryName(rs.getString("category_name"));

        return category;
    }
}
