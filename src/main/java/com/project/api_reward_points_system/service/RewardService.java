package com.project.api_reward_points_system.service;

import com.project.api_reward_points_system.model.Transaction;
import com.project.api_reward_points_system.model.RewardResponse;
import com.project.api_reward_points_system.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service to calculate reward points for transactions.
 */
@Service
public class RewardService {
    private final TransactionRepository transactionRepository;

    public RewardService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Calculates reward points for all customers, grouped by month.
     * Retrieves all transactions, groups them by customer and month,
     * calculates monthly and total points, and returns a list of reward responses.
     * @return List of RewardResponse objects, each containing customerId, monthly points, and total points.
     */

    public List<RewardResponse> calculateRewards() {
        List<Transaction> transactionList = transactionRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        Map<Long, Map<String, Integer>> customerMonthPoints = transactionList.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getCustomerId,
                        Collectors.groupingBy(
                                tx -> tx.getDate().format(formatter),
                                Collectors.summingInt(tx -> calculatePoints(tx.getAmount()))
                        )
                ));

        return customerMonthPoints.entrySet().stream()
                .map(entry -> {
                    int total = entry.getValue().values().stream().mapToInt(Integer::intValue).sum();
                    return new RewardResponse(entry.getKey(), entry.getValue(), total);
                })
                .collect(Collectors.toList());
    }

    /**
     * Calculates reward points for a single transaction amount.
     * @param amount Transaction amount
     * @return Points earned
     */
    public int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += (int) ((amount - 100) * 2 + 50);
        } else if (amount > 50) {
            points += (int) (amount - 50);
        }
        return points;
    }
}