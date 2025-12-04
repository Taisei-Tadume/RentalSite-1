package jp.ken.jdbc.domain.entity;

public class GenreEntity {

	/** ジャンルID */
    private String genreId;

    /** ジャンル名 */
    private String genreName;

    // Getter / Setter

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
