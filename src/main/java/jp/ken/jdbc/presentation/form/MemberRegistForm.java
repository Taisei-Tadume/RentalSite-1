package jp.ken.jdbc.presentation.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 新規会員登録画面（③新規会員登録）用フォームクラス
 */
public class MemberRegistForm {

	  /** ユーザー名 */
    @NotBlank(message = "ユーザー名は必須です。")
    private String userName;

    /** メールアドレス */
    @NotBlank(message = "メールアドレスは必須です。")
    @Email(message = "メールアドレス形式が不正です。")
    private String email;

    /** パスワード */
    @NotBlank(message = "パスワードは必須です。")
    @Size(min = 8, max = 16, message = "パスワードは8文字以上16文字以内で入力してください。")
    private String password;

    /** パスワード確認 */
    @NotBlank(message = "パスワード（確認）は必須です。")
    private String confirmPassword;

    /** 電話番号 */
    @Pattern(regexp = "^[0-9]{0,11}$", message = "電話番号は数字で11桁以内で入力してください。")
    private String phoneNumber;

    /** 郵便番号 */
    @Pattern(regexp = "^[0-9]{0,7}$", message = "郵便番号は数字で7桁以内で入力してください。")
    private String postalCode;

    /** 住所 */
    private String address;

    // ---------------------------
    // getter / setter
    // ---------------------------
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
