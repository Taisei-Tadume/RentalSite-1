package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductDetailsController {

    @GetMapping("/product/{id}")
    public String detail(@PathVariable("id") Integer id) {
        return "productdetails";
    }
}
