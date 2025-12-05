package jp.ken.jdbc.domain.entity;

public class MemberEntity {

    private Long userId;          // user_id
    private String userName;      // user_name
    private String email;         // email
    private String phoneNumber;   // phone_number
    private String zipcode;       // zipcode
    private String address;       // address
    private String loginId;       // login_id
    private String passwordHash;  // password_hash
    private Long authorityId;     // authority_id

    // --- Getter / Setter ---

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getLoginId() {
        return loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Long getAuthorityId() {
        return authorityId;
    }
    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }
}
