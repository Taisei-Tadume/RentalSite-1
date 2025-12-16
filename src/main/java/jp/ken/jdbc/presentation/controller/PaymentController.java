package jp.ken.jdbc.presentation.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.application.service.MemberService;
import jp.ken.jdbc.domain.entity.MemberEntity;

@Controller
public class PaymentController {

    private final MemberService memberService;

    public PaymentController(MemberService memberService) {
        this.memberService = memberService;
    }

    // ▼ 決済実行（POST）
    @PostMapping("/payment/execute")
    public String executePayment(
            @RequestParam String cardNumber,
            @RequestParam String cardName,
            @RequestParam String expiry,
            @RequestParam String cvv,
            Model model,
            HttpSession session
    ) {
    	
    	// ログイン済みなら新規登録ページにアクセス不可
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return "redirect:/top"; // どこに飛ばすかは自由
        }


        // ▼ バリデーション
        if (!cardNumber.matches("\\d{16}")) {
            model.addAttribute("errorMessage", "カード番号が不正です。");
            return "payment";
        }

        if (cardName.isBlank()) {
            model.addAttribute("errorMessage", "カード名義が不正です。");
            return "payment";
        }

        if (!expiry.matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
            model.addAttribute("errorMessage", "有効期限が不正です。");
            return "payment";
        }

        if (!cvv.matches("\\d{3}")) {
            model.addAttribute("errorMessage", "セキュリティコードが不正です。");
            return "payment";
        }

        // ▼ セッションから会員情報を取得
        MemberEntity member = (MemberEntity) session.getAttribute("tempMember");
        Integer selectedPlanId = (Integer) session.getAttribute("selectedPlanId");

        if (member == null || selectedPlanId == null) {
            model.addAttribute("errorMessage", "セッションが切れています。最初からやり直してください。");
            return "payment";
        }

        // ▼ プランIDをセット
        member.setPlanId(selectedPlanId);

        // ▼ DB に初めて登録（ここが重要）
        memberService.register(member);

        // ▼ セッションをクリア
        session.removeAttribute("tempMember");
        session.removeAttribute("selectedPlanId");

        // ▼ 完了画面へ
        return "payment_success";
    }
}