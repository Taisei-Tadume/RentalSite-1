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
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            SearchForm searchForm,
            HttpSession session,
            Model model) {

        // ページ番号の安全化
        int currentPage = Math.max(page, 0);

        // キーワード null 対策
        String keyword = (searchForm.getKeyword() != null) ? searchForm.getKeyword().trim() : "";

        List<GoodsEntity> resultList;
        long totalCount;

        if (!keyword.isEmpty()) {
            // キーワード検索（部分一致） + ジャンル絞り込み
            resultList = goodsService.searchByKeyword(keyword, genreId, currentPage, PAGE_SIZE);
            totalCount = goodsService.countByKeyword(keyword, genreId);
        } else {
            // ジャンル別検索
            int genre = (genreId != null) ? genreId : 0;
            resultList = goodsService.searchGoods(genre, currentPage, PAGE_SIZE);
            totalCount = goodsService.countGoodsByGenre(genre);
        }

        // 全ジャンルリスト
        List<GenreEntity> genres = goodsService.getAllGenres();

        // モデルにセット
        model.addAttribute("resultList", resultList);
        model.addAttribute("genres", genres);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalCount / PAGE_SIZE));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("searchForm", searchForm);

        // カート情報
        model.addAttribute("cartItems", cartService.getCart(session));

        return "searchresult";
    }
}
