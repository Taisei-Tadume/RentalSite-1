package jp.ken.jdbc.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ken.jdbc.domain.entity.GenreEntity;
import jp.ken.jdbc.domain.entity.GoodsEntity;
import jp.ken.jdbc.domain.repository.GoodsRepository;

@Service
public class GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    public List<GoodsEntity> searchGoods(int genreId, int page, int pageSize) {

        int offset = page * pageSize;

        if (genreId == 0) {
            return goodsRepository.findAll(offset, pageSize);
        } else {
            return goodsRepository.findByGenre(genreId, offset, pageSize);
        }
    }

    public long countGoodsByGenre(int genreId) {

        if (genreId == 0) {
            return goodsRepository.countAll();
        } else {
            return goodsRepository.countByGenre(genreId);
        }
    }

    public List<GenreEntity> getAllGenres() {
        return goodsRepository.findGenres();
    }

    // 🔥 これが detail に必要だったやつ！
    public GoodsEntity findById(long goodsId) {
        return goodsRepository.findById(goodsId);
    }
}
