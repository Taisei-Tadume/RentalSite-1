package jp.ken.jdbc.presentation.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 会員情報管理画面（⑧会員情報管理）用フォームクラス
 */
public class MemberForm {

    /** 氏名 */
    @NotBlank(message = "氏名は必須です。")
    private String name;

    /** メールアドレス */
    @NotBlank(message = "メールアドレスは必須です。")
    @Email(message = "メールアドレス形式が不正です。")
    private String email;

    /** 電話番号 */
    @Pattern(regexp = "^[0-9]*$", message = "電話番号は数字のみで入力してください。")
    private String phone;

    /** 郵便番号（zipcode に統一） */
    @NotBlank(message = "郵便番号は必須です。")
    @Pattern(regexp = "^[0-9]*$", message = "郵便番号は数字のみで入力してください。")
    private String zipcode;

    /** 住所 */
    private String address;

    /** パスワード */
    @NotBlank(message = "パスワードは必須です。")
    @Size(min = 8, max = 20, message = "パスワードは8文字以上20文字以下で入力してください。")
    private String password;

    /** パスワード（確認用） */
    private String passwordConfirm;

    // ---------------------------
    // getter / setter
    // ---------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
