package jp.ken.jdbc.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jp.ken.jdbc.application.service.AdminGoodsService;
import jp.ken.jdbc.domain.entity.GoodsEntity;

@Controller
@RequestMapping("/admin")
public class AdminGoodsController {

    @Autowired
    private AdminGoodsService service;

    @GetMapping("/stock")
    public String stockPage(Model model) {

        model.addAttribute("form", new GoodsEntity());
        model.addAttribute("goodsList", service.findAll());
        model.addAttribute("categoryList", service.getCategoryList());

        return "admin-stock";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute("form") GoodsEntity form,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("goodsList", service.findAll());
            model.addAttribute("categoryList", service.getCategoryList());
            return "admin-stock";
        }

        service.insertGoods(form);
        return "redirect:/admin/stock";
    }

    @PostMapping("/update")
    public String update(
            @RequestParam int goodsId,
            @RequestParam int quantity) {

        service.updateStock(goodsId, quantity);
        return "redirect:/admin/stock";
    }

    @PostMapping("/broken")
    public String broken(@RequestParam int goodsId) {
        service.decreaseStock(goodsId);
        return "redirect:/admin/stock";
    }
}
