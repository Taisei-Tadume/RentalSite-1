package jp.ken.jdbc.presentation.controller;

import java.util.List;
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

        model.addAttribute("categoryList",
                service.findAll().stream()
                        .map(g -> Map.of("id", g.getCategoryId(), "name", g.getCategoryName()))
                        .distinct()
                        .toList());

        model.addAttribute("genreList", genreService.getAllGenres());

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

        List<GoodsEntity> list = service.search(goodsId, goodsName, categoryId, genreId);

        model.addAttribute("result", list);
        model.addAttribute("form", new GoodsEntity());

        model.addAttribute("categoryList",
                service.findAll().stream()
                        .map(g -> Map.of("id", g.getCategoryId(), "name", g.getCategoryName()))
                        .distinct()
                        .toList());

        model.addAttribute("genreList", genreService.getAllGenres());

        return "admin-stock";
    }

    /** 商品追加 */
    @PostMapping("/admin/add")
    public String addGoods(@ModelAttribute GoodsEntity form) {

        if (form.getQuantity() == null) form.setQuantity(0);
        if (form.getGenreId() == null) form.setGenreId(0);
        if (form.getCategoryId() == null) form.setCategoryId(0);

        // ★ JANコードが空欄なら null に変換
        if (form.getJanCode() == null || form.getJanCode().isBlank()) {
            form.setJanCode(null);
        }

        service.insertGoods(form);
        return "redirect:/admin/stock";
    }


    /** 在庫更新 */
    @PostMapping("/admin/update")
    public String updateStock(@RequestParam Long id, @RequestParam Integer qty) {
        service.updateStock(id, qty);
        return "redirect:/admin/stock";
    }

    /** 不良品 -1 */
    @PostMapping("/admin/bad")
    public String badItem(@RequestParam Long id) {
        service.decreaseStock(id);
        return "redirect:/admin/stock";
    }
}
