package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlanSelectionController {

    @GetMapping("/plan")
    public String plan() {
        return "planselection";
    }
    
    @PostMapping("/payment")
    public String toPayment(@RequestParam("selectedPlan") int planId, Model model) {

        int price = 0;
        String name = "";

        switch (planId) {
            case 1 -> { price = 324;  name = "お試し"; }
            case 2 -> { price = 1080; name = "Bronze"; }
            case 3 -> { price = 2160; name = "Silver"; }
            case 4 -> { price = 5400; name = "Gold"; }
        }

        model.addAttribute("planName", name);
        model.addAttribute("amount", price);

        return "payment"; // 決済画面へ
    }

}
