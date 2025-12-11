package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {

	@GetMapping("/logout")
	public String logoutPage() {
		return "logout";
	}
	
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		
		//セッション破棄
		session.invalidate();
		
		return "redirect:/login?logout";
	}
}
