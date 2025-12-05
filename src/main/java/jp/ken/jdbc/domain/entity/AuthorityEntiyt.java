package jp.ken.jdbc.domain.entity;

public class AuthorityEntiyt {

	private Long authorityId;      // authority_id
    private String authorityName;  // authority_name

    // --- Getter / Setter ---
    public Long getAuthorityId() {
        return authorityId;
    }
    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }
    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }
}
