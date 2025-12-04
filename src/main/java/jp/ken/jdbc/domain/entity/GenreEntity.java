package jp.ken.jdbc.domain.entity;

public class GenreEntity {

	/** ジャンルID */
    private int genreId;

    /** ジャンル名 */
    private String genreName;

    // Getter / Setter

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
