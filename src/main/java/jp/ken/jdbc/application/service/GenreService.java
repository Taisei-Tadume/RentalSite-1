package jp.ken.jdbc.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.ken.jdbc.domain.entity.GenreEntity;
import jp.ken.jdbc.domain.repository.GenreRepository;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    /**
     * 全ジャンル取得
     */
    public List<GenreEntity> getAllGenres() {
        return genreRepository.findAll();
    }

    /**
     * ジャンルIDから取得（存在しない場合は null を返す）
     */
    public GenreEntity getGenreById(Long genreId) {
        return genreRepository.findById(genreId);
    }
}
