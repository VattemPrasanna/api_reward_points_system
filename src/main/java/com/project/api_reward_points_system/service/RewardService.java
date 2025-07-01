package com.project.api_reward_points_system.service;

import com.project.api_reward_points_system.model.RewardResponse;
import com.project.api_reward_points_system.model.Transaction;

import java.util.List;

public interface RewardService {
    public List<RewardResponse> calculateRewards();
    public RewardResponse calculateRewardsByCustomerId(Long customerId, List<Transaction> transactionList);

}