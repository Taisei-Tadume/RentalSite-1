package jp.ken.jdbc.domain.entity;

public class CategoryEntity {

    /** カテゴリID */
    private int categoryId;

    /** カテゴリ名 */
    private String categoryName;

    /** 削除フラグ（0:有効 / 1:削除） */
    private boolean deleteFlag;

    // ====== Getter / Setter ======

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

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

}
