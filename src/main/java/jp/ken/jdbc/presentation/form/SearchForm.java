package jp.ken.jdbc.presentation.form;

public class SearchForm {

    // 検索キーワード（任意）
    private String keyword;

    // カテゴリID（未選択時は null）
    private Long categoryId;

    // ジャンルID（未選択時は null）
    private Long genreId;

    // ===== Getter / Setter =====
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }
}
