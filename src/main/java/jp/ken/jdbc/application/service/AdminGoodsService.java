package jp.ken.jdbc.application.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ken.jdbc.domain.entity.GoodsEntity;
import jp.ken.jdbc.domain.repository.AdminGoodsRepository;

@Service
public class AdminGoodsService {

    @Autowired
    private AdminGoodsRepository repo;

    public List<GoodsEntity> findAll() {
        return repo.findAll();
    }

    public void insertGoods(GoodsEntity g) {
        repo.insertGoods(g);
    }

    public void updateStock(Long id, Integer qty) {
        repo.updateStock(id, qty);
    }

    public void decreaseStock(Long id) {
        repo.decreaseStock(id);
    }

    public List<Map<String, Object>> findAllCategories() {
        return repo.findAllCategories();
    }
}
