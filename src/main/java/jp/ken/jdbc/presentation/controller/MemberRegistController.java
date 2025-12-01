package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.ken.jdbc.presentation.form.MemberForm;

@Controller
public class MemberRegistController {

    @GetMapping("/regist")
    public String regist(Model model) {
    	MemberForm memberForm = new MemberForm();
    	model.addAttribute("memberForm", memberForm);
    	return "newmemberregistration";
    }
}
