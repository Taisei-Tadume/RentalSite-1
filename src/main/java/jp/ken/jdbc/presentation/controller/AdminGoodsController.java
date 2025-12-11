package jp.ken.jdbc.presentation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jp.ken.jdbc.application.service.AdminGoodsService;
import jp.ken.jdbc.domain.entity.GoodsEntity;

@Controller
public class AdminGoodsController {

    @Autowired
    private AdminGoodsService service;

    /** 在庫画面 */
    @GetMapping("/admin/stock")
    public String stockPage(Model model) {

        model.addAttribute("goodsList", service.findAll());
        model.addAttribute("form", new GoodsEntity());

        // カテゴリリスト
        List<Map<String, Object>> categories = service.findAllCategories();
        model.addAttribute("categoryList", categories);

        return "admin-stock";
    }

    /** 商品追加 */
    @PostMapping("/admin/add")
    public String addGoods(
            @Valid @ModelAttribute("form") GoodsEntity form,
            BindingResult binding,
            Model model) {

        if (binding.hasErrors()) {
            model.addAttribute("goodsList", service.findAll());
            model.addAttribute("categoryList", service.findAllCategories());
            return "admin-stock";
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

    /** 不良品処理 */
    @PostMapping("/admin/bad")
    public String badItem(@RequestParam Long id) {

        service.decreaseStock(id);
        return "redirect:/admin/stock";
    }
}
