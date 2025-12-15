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

	private static final int PAGE_SIZE = 9;

	@GetMapping("/search")
	public String search(
			@RequestParam(value = "genre", required = false) Integer genreId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "from", required = false) String from,
			SearchForm searchForm,
			HttpSession session,
			Model model) {

		// ✅ トップから戻った場合は検索条件をクリア
		if ("top".equals(from)) {
			session.removeAttribute("searchForm");
		}

		// ===== 検索条件の保存 or 復元 =====
		SearchForm sessionForm = (SearchForm) session.getAttribute("searchForm");

		if (searchForm.getKeyword() != null) {
			// 新規検索（フォームから来た）
			searchForm.setKeyword(searchForm.getKeyword().trim());
			session.setAttribute("searchForm", searchForm);
		} else if (sessionForm != null) {
			// detail から戻った場合など
			searchForm = sessionForm;
		}

		String keyword = (searchForm.getKeyword() != null)
				? searchForm.getKeyword()
				: "";

		int currentPage = (page != null) ? Math.max(page, 0) : 0;

		List<GoodsEntity> resultList;
		long totalCount;

		if (!keyword.isEmpty()) {
			resultList = goodsService.searchByKeyword(
					keyword, genreId, currentPage, PAGE_SIZE);
			totalCount = goodsService.countByKeyword(keyword, genreId);
		} else {
			int genre = (genreId != null) ? genreId : 0;
			resultList = goodsService.searchGoods(
					genre, currentPage, PAGE_SIZE);
			totalCount = goodsService.countGoodsByGenre(genre);
		}

		List<GenreEntity> genres = goodsService.getAllGenres();

		model.addAttribute("resultList", resultList);
		model.addAttribute("genres", genres);
		model.addAttribute("totalPages",
				(int) Math.ceil((double) totalCount / PAGE_SIZE));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("searchForm", searchForm);
		model.addAttribute("cartItems", cartService.getCart(session));

		return "searchresult";
	}
}