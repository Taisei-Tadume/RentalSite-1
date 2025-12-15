package jp.ken.jdbc.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.application.service.CartService;
import jp.ken.jdbc.application.service.GoodsService;
import jp.ken.jdbc.domain.entity.GenreEntity;
import jp.ken.jdbc.domain.entity.GoodsEntity;
import jp.ken.jdbc.presentation.form.SearchForm;

@Controller
public class SearchController {

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private CartService cartService;

	// 1ページあたりの表示件数
	private static final int PAGE_SIZE = 9;

	@GetMapping("/search")
	public String search(
			@RequestParam(value = "genre", required = false) Integer genreId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "from", required = false) String from,
			SearchForm searchForm,
			HttpSession session,
			Model model) {

		// ============================================================
		// ✅ トップページから戻ってきた場合は検索条件をクリアする
		//    理由：トップ → 詳細 → 戻る で検索結果に戻らないようにするため
		// ============================================================
		if ("top".equals(from)) {
			session.removeAttribute("searchForm");
		}

		// ============================================================
		// ✅ 検索条件の保存 or 復元
		//    ・フォームから keyword が来た → 新規検索として保存
		//    ・フォームが空 & セッションに保存済み → 復元（detail → 戻る）
		// ============================================================
		SearchForm sessionForm = (SearchForm) session.getAttribute("searchForm");

		if (searchForm.getKeyword() != null) {
			// 新規検索（フォーム入力あり）
			searchForm.setKeyword(searchForm.getKeyword().trim());
			session.setAttribute("searchForm", searchForm);

		} else if (sessionForm != null) {
			// detail → 検索結果に戻る時など
			searchForm = sessionForm;
		}

		// キーワードが null の場合は空文字にする
		String keyword = (searchForm.getKeyword() != null)
				? searchForm.getKeyword()
				: "";

		// ページ番号（0 未満にならないように調整）
		int currentPage = (page != null) ? Math.max(page, 0) : 0;

		List<GoodsEntity> resultList;
		long totalCount;

		// ============================================================
		// ✅ キーワード検索 or ジャンル検索
		// ============================================================
		if (!keyword.isEmpty()) {
			// キーワード検索
			resultList = goodsService.searchByKeyword(
					keyword, genreId, currentPage, PAGE_SIZE);
			totalCount = goodsService.countByKeyword(keyword, genreId);

		} else {
			// ジャンル検索（または全件）
			int genre = (genreId != null) ? genreId : 0;
			resultList = goodsService.searchGoods(
					genre, currentPage, PAGE_SIZE);
			totalCount = goodsService.countGoodsByGenre(genre);
		}

		// ジャンル一覧（検索フォーム用）
		List<GenreEntity> genres = goodsService.getAllGenres();

		// ============================================================
		// ✅ 画面に渡すデータ
		// ============================================================
		model.addAttribute("resultList", resultList);
		model.addAttribute("genres", genres);
		model.addAttribute("totalPages",
				(int) Math.ceil((double) totalCount / PAGE_SIZE));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("searchForm", searchForm);

		// カート情報（ヘッダーのカート表示用）
		model.addAttribute("cartItems", cartService.getCart(session));
		
		// ヒットした検索数
		model.addAttribute("totalCount", totalCount);

		return "searchresult";
	}
}