package jp.ken.jdbc.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.application.service.GoodsService;
import jp.ken.jdbc.domain.dto.CartItem;
import jp.ken.jdbc.domain.entity.GoodsEntity;

@Controller
public class DetailController {

	@Autowired
	private GoodsService goodsService;

	@GetMapping("/detail/{id}")
	public String detail(
			@PathVariable("id") Integer goodsId,
			@RequestParam(name = "from", required = false) String from,
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "added", required = false) String added,
			HttpSession session,
			Model model) {

		// 商品取得
		GoodsEntity goods = goodsService.findById(goodsId);
		model.addAttribute("goods", goods);

		// カート情報
		@SuppressWarnings("unchecked")
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

		boolean isInCart = cart != null && cart.stream()
				.anyMatch(i -> i.getGoodsId().equals(goodsId));

		model.addAttribute("isInCart", isInCart);
		model.addAttribute("added", added != null);

		// ✅ 戻り先情報（top / search のみ）
		model.addAttribute("from", from);
		model.addAttribute("keyword", keyword);

		return "detail";
	}
}