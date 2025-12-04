package jp.ken.jdbc.presentation.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PaymentForm {

    @NotBlank(message = "カード番号が不正です。")
    @Pattern(regexp = "^[0-9]{16}$", message = "カード番号が不正です。")
    private String cardNumber;

    @NotBlank(message = "カード名義は必須です。")
    private String cardHolder;

    @NotBlank(message = "有効期限が不正です。")
    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/([0-9]{2})$", message = "有効期限が不正です。")
    private String expire;

    @NotBlank(message = "セキュリティコードが不正です。")
    @Pattern(regexp = "^[0-9]{3}$", message = "セキュリティコードが不正です。")
    private String securityCode;

    // getter/setter
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getCardHolder() { return cardHolder; }
    public void setCardHolder(String cardHolder) { this.cardHolder = cardHolder; }

    public String getExpire() { return expire; }
    public void setExpire(String expire) { this.expire = expire; }

    public String getSecurityCode() { return securityCode; }
    public void setSecurityCode(String securityCode) { this.securityCode = securityCode; }
}
