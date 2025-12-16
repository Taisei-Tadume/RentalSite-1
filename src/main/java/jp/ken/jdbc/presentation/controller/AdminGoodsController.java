package jp.ken.jdbc.presentation.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ken.jdbc.application.service.AdminGoodsService;
import jp.ken.jdbc.application.service.GenreService;
import jp.ken.jdbc.domain.entity.GoodsEntity;

@Controller
public class AdminGoodsController {

    private final AdminGoodsService service;
    private final GenreService genreService;

    public AdminGoodsController(AdminGoodsService service, GenreService genreService) {
        this.service = service;
        this.genreService = genreService;
    }

    /** 初期表示 */
    @GetMapping("/admin/stock")
    public String stockPage(Model model) {

        model.addAttribute("form", new GoodsEntity());
        model.addAttribute("result", null);
        model.addAttribute("scrollAnchor", null);

        setCommon(model);
        return "admin-stock";
    }

    /** 商品検索 */
    @GetMapping("/admin/stock/search")
    public String search(
            @RequestParam(required = false) Integer goodsId,
            @RequestParam(required = false) String goodsName,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer genreId,
            Model model) {

        model.addAttribute("result",
                service.search(goodsId, goodsName, categoryId, genreId));
        model.addAttribute("form", new GoodsEntity());
        model.addAttribute("scrollAnchor", null);

        setCommon(model);
        return "admin-stock";
    }

    /** 商品追加 */
    @PostMapping("/admin/stock/add")
    public String addGoods(@ModelAttribute GoodsEntity form) {

        if (form.getQuantity() == null) form.setQuantity(0);
        if (form.getGenreId() == null) form.setGenreId(0);
        if (form.getCategoryId() == null) form.setCategoryId(0);
        if (form.getJanCode() != null && form.getJanCode().isBlank()) {
            form.setJanCode(null);
        }

        service.insertGoods(form);
        return "redirect:/admin/stock";
    }

    /** 在庫更新（±・任意入力対応） */
    @PostMapping("/admin/stock/update")
    public String updateStock(
            @RequestParam Long id,
            @RequestParam Integer qty,
            @RequestParam(required = false) Integer goodsId,
            @RequestParam(required = false) String goodsName,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer genreId,
            @RequestParam(required = false) String anchor,
            Model model) {

        service.updateStock(id, qty);
        return backToSearch(goodsId, goodsName, categoryId, genreId, anchor, model);
    }

    /** 共通：検索条件＋スクロール位置を維持 */
    private String backToSearch(
            Integer goodsId,
            String goodsName,
            Integer categoryId,
            Integer genreId,
            String anchor,
            Model model) {

        model.addAttribute("result",
                service.search(goodsId, goodsName, categoryId, genreId));
        model.addAttribute("form", new GoodsEntity());
        model.addAttribute("scrollAnchor", anchor);

        setCommon(model);
        return "admin-stock";
    }

    /** 共通Model */
    private void setCommon(Model model) {
        model.addAttribute("categoryList",
                service.findAll().stream()
                        .map(g -> Map.of(
                                "id", g.getCategoryId(),
                                "name", g.getCategoryName()))
                        .distinct()
                        .toList());

        model.addAttribute("genreList", genreService.getAllGenres());
    }
}
