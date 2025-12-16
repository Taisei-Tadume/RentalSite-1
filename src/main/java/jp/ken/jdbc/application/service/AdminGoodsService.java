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

    /** 在庫を「最終値」で更新（マイナス防止） */
    public void updateStock(Long id, Integer qty) {
        int safeQty = (qty == null) ? 0 : Math.max(qty, 0);
        repo.updateStock(id, safeQty);
    }

    /** 不良品 -1（安全版） */
    public void decreaseStock(Long id) {
        repo.decreaseStock(id);
    }
}
