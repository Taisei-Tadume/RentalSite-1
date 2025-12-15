package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.ken.jdbc.application.service.MemberService;
import jp.ken.jdbc.domain.entity.MemberEntity;
import jp.ken.jdbc.presentation.form.MemberRegistForm;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member")
    public String showForm(Model model) {
        model.addAttribute("memberForm", new MemberRegistForm());
        return "newmemberregistration";
    }

    @PostMapping("/member")
    public String regist(
            @Validated @ModelAttribute("memberForm") MemberRegistForm form,
            BindingResult bindingResult,
            Model model) {

        // 入力チェック
        if (bindingResult.hasErrors()) {
            return "newmemberregistration";
        }

        // メール重複チェック
        if (memberService.emailExists(form.getEmail())) {
            model.addAttribute("errorMessage", "このメールアドレスはすでに登録されています");
            return "newmemberregistration";
        }

        // Form → Entity 変換
        MemberEntity member = new MemberEntity();
        member.setUserName(form.getUserName());
        member.setEmail(form.getEmail());
        member.setPhoneNumber(form.getPhoneNumber());
        member.setAddress(form.getAddress());
        member.setPostalCode(form.getPostalCode());
        member.setPasswordHash(form.getPassword()); // ※ 後でBCryptにする
        member.setAuthorityId(1); // 一般会員
        member.setPlanId(1);      // デフォルトプラン

        // DBへ保存
        memberService.register(member);

        return "redirect:/top";
    }
}
