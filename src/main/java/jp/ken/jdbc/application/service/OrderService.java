package jp.ken.jdbc.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ken.jdbc.application.exception.OutOfStockException;
import jp.ken.jdbc.domain.dto.CartItem;
import jp.ken.jdbc.domain.repository.GoodsRepository;

@Service
public class OrderService {

    private final GoodsRepository goodsRepository;

    public OrderService(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    /**
     * 注文確定処理
     * ・カート内商品の在庫を減算する
     * ・在庫が不足している場合は例外をスローし、全体をロールバックする
     */
    @Transactional
    public void completeOrder(List<CartItem> cart) {

        for (CartItem item : cart) {

            int goodsId = item.getGoods().getGoodsId();
            int orderQuantity = item.getQuantity();

            // 在庫を減らす
            int updatedCount = goodsRepository.decreaseStock(goodsId, orderQuantity);

            // 更新件数が 0 件 = 在庫不足 or 対象商品なし
            if (updatedCount == 0) {
                throw new OutOfStockException(
                        "在庫が不足しています（商品名：" +
                        item.getGoods().getGoodsName() + "）");
            }
        }
    }
}
