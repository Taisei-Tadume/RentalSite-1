package jp.ken.jdbc.presentation.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 新規会員登録画面（③新規会員登録）用フォームクラス
 */
@Data
public class MemberRegistForm {

	@NotBlank(message = "ユーザー名を入力してください")
	private String userName;

	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式が正しくありません")
	private String email;

	@NotBlank(message = "電話番号を入力してください")
	private String phoneNumber;

	@NotBlank(message = "住所を入力してください")
	private String address;

	@NotBlank(message = "パスワードを入力してください")
	@Size(min = 8, message = "パスワードは8文字以上で入力してください")
	private String password;

	@NotBlank(message = "パスワード（確認用）を入力してください")
	private String passwordConfirm;

}
