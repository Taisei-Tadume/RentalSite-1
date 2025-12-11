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

    /**
     * 商品検索（ジャンル別 + ページング）
     * @param genreId ジャンルID（0で全ジャンル）
     * @param page ページ番号（1始まりを想定）
     * @param pageSize 1ページあたり件数
     * @return 商品リスト
     */
    public List<GoodsEntity> searchGoods(int genreId, int page, int pageSize) {

        int offset = Math.max(page, 0) * pageSize;

        if (genreId == ALL_GENRES) {
            return goodsRepository.findAll(offset, pageSize);
        } else {
            return goodsRepository.findByGenre(genreId, offset, pageSize);
        }
    }

    /**
     * 商品件数取得（ジャンル別）
     * @param genreId ジャンルID（0で全ジャンル）
     * @return 件数
     */
    public long countGoodsByGenre(int genreId) {
        if (genreId == ALL_GENRES) {
            return goodsRepository.countAll();
        } else {
            return goodsRepository.countByGenre(genreId);
        }
    }

    /**
     * 全ジャンル取得
     */
    public List<GenreEntity> getAllGenres() {
        return goodsRepository.findGenres();
    }

    /**
     * 商品IDから取得（存在しない場合は null を返す）
     */
    public GoodsEntity findById(long goodsId) {
        try {
            return goodsRepository.findById(goodsId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
