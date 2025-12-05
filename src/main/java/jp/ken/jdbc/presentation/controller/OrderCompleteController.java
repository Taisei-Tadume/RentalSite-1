package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderCompleteController {

    // 注文完了処理（POST）
    @PostMapping("/order/complete")
    public String completePost() {
        // ※ここに注文登録処理(DB書き込み)などが入る
        return "ordercomplete";
    }

    // 完了画面を直接表示したい場合の GET
    @GetMapping("/order/complete")
    public String completeGet() {
        return "ordercomplete";
    }
}
