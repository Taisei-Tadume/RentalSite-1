package jp.ken.jdbc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ken.jdbc.application.service.PlanService;
import jp.ken.jdbc.domain.entity.PlanEntity;

@Controller
public class PlanSelectionController {

	private final PlanService planService;

	public PlanSelectionController(PlanService planService) {
		this.planService = planService;
	}

	// ✅ プラン選択画面（プラン一覧を表示）
	@GetMapping("/plan")
	public String plan(Model model) {

		model.addAttribute("plans", planService.getAllPlans());

		return "planselection";
	}

	// ✅ 決済画面へ（選択されたプランIDから DB で情報取得）
	@PostMapping("/payment")
	public String toPayment(@RequestParam("selectedPlan") int planId, Model model) {

		// ✅ DB からプラン情報を取得
		PlanEntity plan = planService.getPlanById(planId);

		if (plan == null) {
			// 万が一プランが存在しない場合
			model.addAttribute("error", "プランが存在しません");
			return "planselection";
		}

		model.addAttribute("planName", plan.getPlanName());
		model.addAttribute("amount", plan.getPlanPrice().intValue());
		model.addAttribute("limit", plan.getRentalLimit());

		return "payment"; // 決済画面へ
	}
}