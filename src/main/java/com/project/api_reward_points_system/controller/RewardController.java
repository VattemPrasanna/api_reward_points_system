package com.project.api_reward_points_system.controller;

import com.project.api_reward_points_system.model.RewardResponse;
import com.project.api_reward_points_system.service.RewardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for reward points API.
 */
@RestController
@RequestMapping("/api/rewards")
public class RewardController {
    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    /**
     * Returns reward points per customer, per month, and total.
     *
     * @return List of reward responses
     */
    @GetMapping
    public List<RewardResponse> getRewards() {
        return rewardService.calculateRewards();
    }
}