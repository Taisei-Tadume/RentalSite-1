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
import jp.ken.jdbc.domain.entity.GoodsEntity;
import jp.ken.jdbc.presentation.form.SearchForm;

@Controller
public class SearchController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CartService cartService;

    @GetMapping("/search")
    public String search(
            @RequestParam(value = "genre", required = false, defaultValue = "0") Integer genreId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            SearchForm searchForm,
            HttpSession session,
            Model model) {

        int pageSize = 9;

        List<GoodsEntity> resultList = goodsService.searchGoods(genreId, page, pageSize);
        long totalCount = goodsService.countGoodsByGenre(genreId);

        model.addAttribute("resultList", resultList);
        model.addAttribute("genres", goodsService.getAllGenres());
        model.addAttribute("totalPages", (int) Math.ceil((double) totalCount / pageSize));
        model.addAttribute("currentPage", page);
        model.addAttribute("searchForm", searchForm);

        // ★ 追加済み判定のためにカート中アイテムを渡す（重要）
        model.addAttribute("cartItems", cartService.getCart(session));

        return "searchResult";
    }
}
