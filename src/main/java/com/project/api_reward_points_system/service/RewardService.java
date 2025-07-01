package com.project.api_reward_points_system.service;

import com.project.api_reward_points_system.configuration.PropertyConfig;
import com.project.api_reward_points_system.constants.AuthConstants;
import com.project.api_reward_points_system.model.Transaction;
import com.project.api_reward_points_system.model.RewardResponse;
import com.project.api_reward_points_system.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service to calculate reward points for transactions.
 */
@Service
public class RewardService {
    private static final Logger logger = LoggerFactory.getLogger(RewardService.class);

    private final TransactionRepository transactionRepository;
    private final PropertyConfig propertyConfig;

    public RewardService(TransactionRepository transactionRepository, PropertyConfig propertyConfig) {
        this.transactionRepository = transactionRepository;
        this.propertyConfig = propertyConfig;
    }

    /**
     * Calculates reward points for all customers, grouped by month.
     * Retrieves all transactions, groups them by customer and month,
     * calculates monthly and total points, and returns a list of reward responses.
     *
     * @return List of RewardResponse objects, each containing customerId, monthly points, and total points.
     */

    public List<RewardResponse> calculateRewards() {
        logger.info("Calculating rewards for all customers at service level started at {}", System.currentTimeMillis());
        List<Transaction> transactionList = transactionRepository.findAll();
        logger.info("All transactions retrieved: size {}", transactionList.size());

        if (CollectionUtils.isEmpty(transactionList)) {
            logger.warn("No transactions found. Returning empty reward response list.");
            return Collections.emptyList();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AuthConstants.YEAR_MONTH_FORMAT);

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
     *
     * @param amount Transaction amount
     * @return Points earned
     */
    public int calculatePoints(double amount) {
        int points = 0;
        if (amount > propertyConfig.getMaximumRange()) {
            logger.info("Amount {} exceeds maximum range {}. Calculating points accordingly.", amount, propertyConfig.getMaximumRange());
            points += (int) ((amount - propertyConfig.getMaximumRange()) * propertyConfig.getPointsPerDollar() + propertyConfig.getMinimumRange());
        } else if (amount > propertyConfig.getMinimumRange()) {
            logger.info("Amount {} is within the range {} - {}. Calculating points accordingly.", amount, propertyConfig.getMinimumRange(), propertyConfig.getMaximumRange());
            points += (int) (amount - propertyConfig.getMinimumRange());
        }
        return points;
    }
}