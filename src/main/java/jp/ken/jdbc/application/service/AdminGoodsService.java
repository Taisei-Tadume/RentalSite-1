package jp.ken.jdbc.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.ken.jdbc.domain.entity.GoodsEntity;
import jp.ken.jdbc.domain.repository.AdminGoodsRepository;

@Service
public class AdminGoodsService {

    private final AdminGoodsRepository repo;

    public AdminGoodsService(AdminGoodsRepository repo) {
        this.repo = repo;
    }

    public List<GoodsEntity> findAll() {
        return repo.findAll();
    }

    public List<GoodsEntity> search(Integer id, String name, Integer categoryId, Integer genreId) {
        return repo.search(id, name, categoryId, genreId);
    }

    public void insertGoods(GoodsEntity e) {
        repo.insert(e);
    }

    public void updateStock(Long id, Integer qty) {
        repo.updateStock(id, qty);
    }

    public void decreaseStock(Long id) {
        repo.decreaseStock(id);
    }
}
