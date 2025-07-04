package com.project.api_reward_points_system.controller;

import com.project.api_reward_points_system.constants.AuthConstants;
import com.project.api_reward_points_system.exception.RewardServiceException;
import com.project.api_reward_points_system.model.ErrorResponse;
import com.project.api_reward_points_system.model.RewardResponse;
import com.project.api_reward_points_system.model.Transaction;
import com.project.api_reward_points_system.repository.TransactionRepository;
import com.project.api_reward_points_system.service.RewardServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class RewardControllerTest {

    @Mock
    private RewardServiceImp rewardService;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardController rewardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRewardsResponse_success() {
        List<RewardResponse> rewards = List.of(new RewardResponse());
        when(rewardService.calculateRewards()).thenReturn(rewards);

        ResponseEntity<?> response = rewardController.getRewardsResponse();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
    }

    @Test
    void getRewardsResponse_notFound() {
        when(rewardService.calculateRewards()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = rewardController.getRewardsResponse();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
    }

    @Test
    void getRewardsResponse_exception() {
        when(rewardService.calculateRewards()).thenThrow(new RuntimeException("DB error"));

        RewardServiceException exception = assertThrows(RewardServiceException.class, () -> {
            rewardController.getRewardsResponse();
        });

        assertEquals(AuthConstants.FAILED_TO_GET_REWARDS, exception.getMessage());
    }

    @Test
    void getRewardsByCustomerId_success() {
        List<Transaction> transactions = List.of(new Transaction());
        RewardResponse rewardResponse = new RewardResponse();
        when(transactionRepository.findById(anyLong())).thenReturn(transactions);
        when(rewardService.calculateRewardsByCustomerId(anyLong(), anyList())).thenReturn(rewardResponse);

        ResponseEntity<?> response = rewardController.getRewardsByCustomerId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof RewardResponse);
    }

    @Test
    void getRewardsByCustomerId_notFound() {
        when(transactionRepository.findById(anyLong())).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = rewardController.getRewardsByCustomerId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
    }

}