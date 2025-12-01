package jp.ken.jdbc.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.ken.jdbc.domain.entity.CategoryEntity;
import jp.ken.jdbc.domain.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }
}
