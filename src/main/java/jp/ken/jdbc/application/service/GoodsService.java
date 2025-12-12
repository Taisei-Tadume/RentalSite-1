package jp.ken.jdbc.application.service;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import jp.ken.jdbc.domain.entity.GenreEntity;
import jp.ken.jdbc.domain.entity.GoodsEntity;
import jp.ken.jdbc.domain.repository.GoodsRepository;

@Service
public class GoodsService {

    private final GoodsRepository goodsRepository;

    // コンストラクタインジェクション
    public GoodsService(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    // 全ジャンルを表す定数
    private static final int ALL_GENRES = 0;

    // ============================
    // ジャンル別＋ページング検索
    // ============================
    public List<GoodsEntity> searchGoods(int genreId, int page, int pageSize) {
        int offset = Math.max(page, 0) * pageSize;

        if (genreId == ALL_GENRES) {
            return goodsRepository.findAll(offset, pageSize);
        } else {
            return goodsRepository.findByGenre(genreId, offset, pageSize);
        }
    }

    public long countGoodsByGenre(int genreId) {
        if (genreId == ALL_GENRES) {
            return goodsRepository.countAll();
        } else {
            return goodsRepository.countByGenre(genreId);
        }
    }

    // ============================
    // キーワード検索（部分一致）対応
    // ============================
    public List<GoodsEntity> searchByKeyword(String keyword, Integer genreId, int page, int pageSize) {
        int offset = Math.max(page, 0) * pageSize;

        if (genreId == null || genreId == ALL_GENRES) {
            return goodsRepository.findByKeyword(keyword, offset, pageSize);
        } else {
            return goodsRepository.findByKeywordAndGenre(keyword, genreId, offset, pageSize);
        }
    }

    public long countByKeyword(String keyword, Integer genreId) {
        if (genreId == null || genreId == ALL_GENRES) {
            return goodsRepository.countByKeyword(keyword);
        } else {
            return goodsRepository.countByKeywordAndGenre(keyword, genreId);
        }
    }

    // ============================
    // 全ジャンル取得
    // ============================
    public List<GenreEntity> getAllGenres() {
        return goodsRepository.findGenres();
    }

    // ============================
    // 商品ID検索（存在しない場合は null）
    // ============================
    public GoodsEntity findById(long goodsId) {
        try {
            return goodsRepository.findById(goodsId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
