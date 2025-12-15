package jp.ken.jdbc.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.application.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	// ============================================================
	// ✅ カートに商品を追加
	//    ・detail.html のフォームから POST される
	//    ・from と keyword を受け取り、追加後の戻り先を制御する
	// ============================================================
	@PostMapping("/add")
	public String add(
			@RequestParam("goodsId") int goodsId, // 商品ID
			@RequestParam(name = "from", required = false) String from, // 遷移元（top / search）
			@RequestParam(name = "keyword", required = false) String keyword, // 検索キーワード
			HttpSession session) {

		// ✅ カートに商品を追加
		cartService.addToCart(goodsId, session);

		// ============================================================
		// ✅ URL エンコード（検索キーワードが日本語の場合に必要）
		//    日本語をそのまま URL に入れると 400 エラーになるため
		// ============================================================
		String encodedKeyword = "";
		try {
			if (keyword != null) {
				encodedKeyword = java.net.URLEncoder.encode(keyword, "UTF-8");
			}
		} catch (Exception e) {
			// エラー時は空文字のまま進める
		}

		// ============================================================
		// ✅ 遷移元が「検索結果」の場合
		//    detail → カート追加 → detail に戻る → 検索結果に戻れるようにする
		// ============================================================
		if ("search".equals(from)) {
			return "redirect:/detail/" + goodsId
					+ "?added=true&from=search&keyword=" + encodedKeyword;
		}

		// ============================================================
		// ✅ 遷移元が「トップ」の場合
		// ============================================================
		if ("top".equals(from)) {
			return "redirect:/detail/" + goodsId
					+ "?added=true&from=top";
		}

		// ============================================================
		// ✅ デフォルト（from が無い場合）
		// ============================================================
		return "redirect:/detail/" + goodsId + "?added=true";
	}

	// ============================================================
	// ✅ カート内商品の数量を増やす（非同期）
	// ============================================================
	@PostMapping("/increase")
	@ResponseBody
	public void increase(@RequestParam int goodsId, HttpSession session) {
		cartService.increase(goodsId, session);
	}

	// ============================================================
	// ✅ カート内商品の数量を減らす（非同期）
	// ============================================================
	@PostMapping("/decrease")
	@ResponseBody
	public void decrease(@RequestParam int goodsId, HttpSession session) {
		cartService.decrease(goodsId, session);
	}

	// ============================================================
	// ✅ カートから商品を削除（非同期）
	// ============================================================
	@PostMapping("/remove")
	@ResponseBody
	public String remove(@RequestParam("goodsId") int goodsId, HttpSession session) {
		cartService.remove(goodsId, session);
		return "redirect:/cart";
	}

	// ============================================================
	// ✅ カートを空にする
	// ============================================================
	@PostMapping("/clear")
	public String clear(HttpSession session) {
		cartService.clearCart(session);
		return "redirect:/cart";
	}

	// ============================================================
	// ✅ カート画面表示
	// ============================================================
	@GetMapping("")
	public String showCartPage(HttpSession session, Model model) {

		// セッションからカート情報を取得
		var cart = session.getAttribute("cart");

		model.addAttribute("cart", cart);
		model.addAttribute("errorMessage", "カートに商品が入っていません。");

		return "cart";
	}

}