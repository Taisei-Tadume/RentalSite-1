package jp.ken.jdbc.presentation.form;

public class SearchForm {

	 // 検索キーワード（任意）
    private String keyword;

    // カテゴリやその他検索条件を追加する場合はここにフィールドを追加可能
    private String category;
    
    private int genreId;

    // getter / setter
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genre) {
        this.genreId = genre;
    }
}
