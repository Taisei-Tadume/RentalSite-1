package jp.ken.jdbc.presentation.form;

public class SearchForm {

    // 検索キーワード（任意）
    private String keyword;

    // カテゴリID（未選択時は null）
    private Integer categoryId;

    // ジャンルID（未選択時は null）
    private Integer genreId;

    // ===== Getter / Setter =====
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }
}
