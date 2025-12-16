package jp.ken.jdbc.presentation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.application.service.MemberService;
import jp.ken.jdbc.domain.entity.MemberEntity;
import jp.ken.jdbc.presentation.form.MemberRegistForm;

@Controller
public class MemberRegistController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public MemberRegistController(MemberService memberService,
                                  PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    // --- GET: 新規会員登録フォーム ---
    @GetMapping("/regist")
    public String registForm(Model model) {
    	
    	// ログイン済みなら新規登録ページにアクセス不可
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return "redirect:/top"; // TOPへ飛ばす
        }

        model.addAttribute("memberForm", new MemberRegistForm());
        return "newmemberregistration";
    }

    // --- POST: 会員登録（DBには保存しない） ---
    @PostMapping("/regist")
    public String registSubmit(
            @ModelAttribute("memberForm") @Validated MemberRegistForm memberForm,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {
    	
    	// ログイン済みなら新規登録ページにアクセス不可
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return "redirect:/top"; // どこに飛ばすかは自由
        }


        // ▼バリデーションエラー
        if (bindingResult.hasErrors()) {
            return "newmemberregistration";
        }

        // ▼パスワード一致チェック
        if (memberForm.getPassword() == null || memberForm.getPasswordConfirm() == null
                || !memberForm.getPassword().equals(memberForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "error.passwordConfirm", "パスワードが一致しません");
            return "newmemberregistration";
        }

        // ▼メール重複チェック（既存会員との重複のみチェック）
        if (memberService.emailExists(memberForm.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "このメールアドレスは既に登録されています");
            return "newmemberregistration";
        }

        // ▼Form → Entity 変換（まだ DB に保存しない）
        MemberEntity member = new MemberEntity();
        member.setUserName(memberForm.getUserName());
        member.setEmail(memberForm.getEmail());
        member.setPhoneNumber(memberForm.getPhoneNumber());
        member.setAddress(memberForm.getAddress());
        member.setPostalCode(memberForm.getPostalCode());

        // ▼パスワードをハッシュ化してセット
        String hashedPassword = passwordEncoder.encode(memberForm.getPassword());
        member.setPasswordHash(hashedPassword);

        // ▼初期値（プランは後で選択）
        member.setAuthorityId(1); // 一般会員

        // ▼セッションに保存（DBには保存しない）
        session.setAttribute("tempMember", member);

        // ▼住所もセッションに保存（配送先として使用）
        Map<String, String> address = new HashMap<>();
        address.put("postalCode", memberForm.getPostalCode());
        address.put("address", memberForm.getAddress());
        session.setAttribute("shippingAddress", address);

        // ▼プラン選択画面へ遷移（Controller を通す）
        return "redirect:/plan";
    }
}