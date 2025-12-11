package jp.ken.jdbc.domain.entity;

public class CategoryEntity {

    /** カテゴリID */
    private long categoryId;

    /** カテゴリ名 */
    private String categoryName;

    // Getter / Setter
    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
