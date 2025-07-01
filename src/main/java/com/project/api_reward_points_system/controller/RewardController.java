package com.project.api_reward_points_system.controller;

import com.project.api_reward_points_system.constants.AuthConstants;
import com.project.api_reward_points_system.model.ErrorResponse;
import com.project.api_reward_points_system.model.RewardResponse;
import com.project.api_reward_points_system.service.RewardService;
import com.project.api_reward_points_system.utilities.appUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
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
    private static final Logger logger = LoggerFactory.getLogger(RewardController.class);

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    /**
     * Returns reward points per customer, per month, and total.
     *
     * @return List of reward responses
     */

    @GetMapping()
    public ResponseEntity<?> getRewardsResponse() {
        logger.info("Processing request to get rewards at controller level started at {}", System.currentTimeMillis());
        try {
            List<RewardResponse> rewards = rewardService.calculateRewards();
            if (CollectionUtils.isEmpty(rewards)) {
                ErrorResponse error = appUtil.globalErrorResponse(null, AuthConstants.RECORDS_NOT_FOUND, HttpStatus.NOT_FOUND);
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            logger.info("Returning {} rewards responses.", rewards.size());
            return new ResponseEntity<>(rewards, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while processing rewards: {}", e.getMessage(), e);
            ErrorResponse error = appUtil.globalErrorResponse(null, AuthConstants.FAILED_TO_GET_REWARDS, HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}