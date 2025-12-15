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
			@PathVariable("id") Integer goodsId, // URL の {id} を取得
			@RequestParam(name = "from", required = false) String from, // 遷移元（top / search）
			@RequestParam(name = "keyword", required = false) String keyword, // 検索キーワード（search の場合）
			@RequestParam(name = "added", required = false) String added, // カート追加後のフラグ
			HttpSession session,
			Model model) {

		// ============================================================
		// ✅ 商品情報を取得して画面へ渡す
		// ============================================================
		GoodsEntity goods = goodsService.findById(goodsId);
		model.addAttribute("goods", goods);

		// ============================================================
		// ✅ カート内にこの商品が入っているか判定
		//    detail 画面で「カートに追加済み」表示を切り替えるため
		// ============================================================
		@SuppressWarnings("unchecked")
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

		boolean isInCart = cart != null && cart.stream()
				.anyMatch(i -> i.getGoodsId().equals(goodsId));

		model.addAttribute("isInCart", isInCart);

		// ✅ カート追加直後のトースト表示用フラグ
		model.addAttribute("added", added != null);

		// ============================================================
		// ✅ 戻り先情報（top / search のみ使用）
		//    detail.html の「戻る」ボタンの遷移先を制御するため
		// ============================================================
		model.addAttribute("from", from);
		model.addAttribute("keyword", keyword);

		return "detail";
	}
}