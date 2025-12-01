package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.ken.jdbc.presentation.form.MemberRegistForm;

@Controller
public class MemberRegistController {

    @GetMapping("/regist")
    public String regist(Model model) {
    	MemberRegistForm memberregistForm = new MemberRegistForm();
    	model.addAttribute("memberregistForm", memberregistForm);
    	return "newmemberregistration";
    }
}
