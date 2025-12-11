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

    /**
     * 全カテゴリ取得
     */
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * カテゴリIDから取得（存在しない場合は null を返す）
     */
    public CategoryEntity getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }
}
