package jp.ken.jdbc.domain.entity;

public class CategoryEntity {

    /** カテゴリID */
    private int categoryId;

    /** カテゴリ名 */
    private String categoryName;

    // Getter / Setter
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
