package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.domain.service.ItemService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TopController {

	private final ItemService itemService;

	// "/" にアクセスされたら /top にリダイレクト
	@GetMapping("/")
	public String rootRedirect() {
		return "redirect:/top";
	}

	@GetMapping("/top")
	public String topPage(Model model, HttpSession session) {

		// ✅ トップに来たら検索条件をクリア（重要）
		session.removeAttribute("searchForm");

		// セッションから role を取得
		String role = (String) session.getAttribute("role");

		// 管理者かどうか
		boolean isAdmin = "admin".equals(role);
		model.addAttribute("isAdmin", isAdmin);

		// 新作入荷商品を DB から取得
		model.addAttribute("newItems", itemService.findNewItems());

		return "top";
	}
}