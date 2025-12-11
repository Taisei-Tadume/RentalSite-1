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

    /** 商品一覧 */
    public List<GoodsEntity> findAll() {
        return repo.findAll();
    }

    /** カテゴリ一覧 */
    public List<Map<String, Object>> getCategoryList() {
        return repo.findAllCategories();
    }

    /** 新規追加 */
    public void insertGoods(GoodsEntity g) {
        repo.insertGoods(g);
    }

    /** 在庫更新 */
    public void updateStock(int goodsId, int quantity) {
        repo.updateStock(goodsId, quantity);
    }

    /** 不良品 */
    public void decreaseStock(int goodsId) {
        repo.decreaseStock(goodsId);
    }
}
