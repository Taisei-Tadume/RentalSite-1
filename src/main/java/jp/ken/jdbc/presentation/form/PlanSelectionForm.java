package jp.ken.jdbc.presentation.form;

import jakarta.validation.constraints.NotNull;

public class PlanSelectionForm {

	 // ======== 表示項目 ========
    private String memberName; // ログイン中の氏名を表示
    private String planDetails; // 選択プランの月額・枚数上限など詳細表示
    private String notes; // 注意事項表示

    // ======== 入力項目 ========
    @NotNull(message = "プランを選択してください。")
    private String selectedPlan; // お試し / Bronze / Silver / Gold

    // ======== getter / setter ========
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPlanDetails() {
        return planDetails;
    }

    public void setPlanDetails(String planDetails) {
        this.planDetails = planDetails;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSelectedPlan() {
        return selectedPlan;
    }

    public void setSelectedPlan(String selectedPlan) {
        this.selectedPlan = selectedPlan;
    }
}
