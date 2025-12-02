package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.ken.jdbc.presentation.form.MemberRegistForm;

@Controller
public class MemberRegistController {

    // --- GET: 新規会員登録フォーム表示 ---
    @GetMapping("/regist")
  public String registForm(Model model) {
        MemberRegistForm memberForm = new MemberRegistForm();
        model.addAttribute("memberForm", memberForm);
        return "newmemberregistration";
    }
}
