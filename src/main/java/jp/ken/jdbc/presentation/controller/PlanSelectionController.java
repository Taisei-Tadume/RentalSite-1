package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.ken.jdbc.application.service.PlanService;
import jp.ken.jdbc.domain.entity.PlanEntity;

@Controller
public class PlanSelectionController {

    private final PlanService planService;

    public PlanSelectionController(PlanService planService) {
        this.planService = planService;
    }

    // プラン選択画面
    @GetMapping("/plan")
    public String plan(Model model) {
        model.addAttribute("plans", planService.getAllPlans());
        return "planselection";
    }

    // 決済画面へ
    @PostMapping("/payment")
    public String toPayment(
            @RequestParam("selectedPlan") int planId,
            Model model,
            HttpSession session) {

        // DB からプラン情報を取得
        PlanEntity plan = planService.getPlanById(planId);

        if (plan == null) {
            model.addAttribute("error", "プランが存在しません");
            return "planselection";
        }

        // 🔥 選択したプランIDをセッションに保存（重要）
        session.setAttribute("selectedPlanId", planId);

        // 決済画面に表示する情報
        model.addAttribute("planName", plan.getPlanName());
        model.addAttribute("amount", plan.getPlanPrice().intValue());
        model.addAttribute("limit", plan.getRentalLimit());

        return "payment";
    }
}