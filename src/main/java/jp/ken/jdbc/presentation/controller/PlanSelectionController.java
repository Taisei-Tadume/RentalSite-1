package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlanSelectionController {

    @GetMapping("/plan")
    public String plan() {
        return "planselection";
    }
}
