package jp.ken.jdbc.domain.repository;

import java.util.List;

import jp.ken.jdbc.domain.entity.CategoryEntity;

public interface CategoryRepository {

    List<CategoryEntity> findAll();

    CategoryEntity findById(Integer categoryId);
}
