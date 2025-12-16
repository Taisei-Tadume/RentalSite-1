package jp.ken.jdbc.application.service;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.ken.jdbc.domain.entity.PlanEntity;
import jp.ken.jdbc.domain.repository.PlanRepository;

@Service
public class PlanService {

	private final PlanRepository planRepository;

	public PlanService(PlanRepository planRepository) {
		this.planRepository = planRepository;
	}

	public List<PlanEntity> getAllPlans() {
		return planRepository.findAll();
	}
	
	public PlanEntity getPlanById(int planId) {
	    return planRepository.findById(planId);
	}

}