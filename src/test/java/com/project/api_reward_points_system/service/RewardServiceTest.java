package com.project.api_reward_points_system.service;

import com.project.api_reward_points_system.configuration.PropertyConfig;
import com.project.api_reward_points_system.model.Transaction;
import com.project.api_reward_points_system.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RewardServiceTest {

    private RewardService rewardService;
    private TransactionRepository transactionRepository;
    private PropertyConfig propertyConfig;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        propertyConfig = mock(PropertyConfig.class);
        when(propertyConfig.getPointsPerDollar()).thenReturn(2);
        when(propertyConfig.getMinimumRange()).thenReturn(50);
        when(propertyConfig.getMaximumRange()).thenReturn(100);
        rewardService = new RewardService(transactionRepository, propertyConfig);
    }

    @Test
    void testCalculatePoints() {
        assertEquals(90, rewardService.calculatePoints(120));
        assertEquals(10, rewardService.calculatePoints(60));
        assertEquals(0, rewardService.calculatePoints(50));
        assertEquals(0, rewardService.calculatePoints(30));
    }

    @Test
    void testCalculatePoints_Exactly100() {
        assertEquals(50, rewardService.calculatePoints(100));
    }

    @Test
    void testCalculatePoints_Exactly50() {
        assertEquals(0, rewardService.calculatePoints(50));
    }

    @Test
    void testCalculatePoints_NegativeAmount() {
        assertEquals(0, rewardService.calculatePoints(-10));
    }

    @Test
    void testCalculatePoints_ZeroAmount() {
        assertEquals(0, rewardService.calculatePoints(0));
    }

    @Test
    void testCalculateRewards_MultipleCustomers() {
        List<Transaction> txs = Arrays.asList(
                new Transaction(1L, 120, LocalDate.of(2024, 4, 10)),
                new Transaction(1L, 75, LocalDate.of(2024, 5, 15)),
                new Transaction(2L, 110, LocalDate.of(2024, 4, 12))
        );
        when(transactionRepository.findAll()).thenReturn(txs);
        var results = rewardService.calculateRewards();
        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(r -> r.getCustomerId() == 1L));
        assertTrue(results.stream().anyMatch(r -> r.getCustomerId() == 2L));
    }

    @Test
    void testCalculateRewards_MultipleCustomersMultipleTransactions() {
        List<Transaction> txs = Arrays.asList(
                new Transaction(1L, 120, LocalDate.of(2024, 4, 10)),
                new Transaction(1L, 75, LocalDate.of(2024, 5, 15)),
                new Transaction(1L, 200, LocalDate.of(2024, 6, 5)),
                new Transaction(2L, 60, LocalDate.of(2024, 4, 12)),
                new Transaction(2L, 110, LocalDate.of(2024, 5, 20)),
                new Transaction(2L, 40, LocalDate.of(2024, 6, 25))
        );
        when(transactionRepository.findAll()).thenReturn(txs);
        var results = rewardService.calculateRewards();
        assertEquals(2, results.size());
        var customer1 = results.stream().filter(r -> r.getCustomerId() == 1L).findFirst().orElseThrow();
        assertEquals(90 + 25 + 250, customer1.getTotalPoints());
        var customer2 = results.stream().filter(r -> r.getCustomerId() == 2L).findFirst().orElseThrow();
        assertEquals(10 + 70 + 0, customer2.getTotalPoints());
    }

    @Test
    void testCalculateRewards_EmptyList() {
        when(transactionRepository.findAll()).thenReturn(List.of());
        var results = rewardService.calculateRewards();
        assertTrue(results.isEmpty());
    }

    @Test
    void testCalculateRewards_TransactionWithNullDate() {
        List<Transaction> txs = List.of(new Transaction(1L, 120, null));
        when(transactionRepository.findAll()).thenReturn(txs);
        assertThrows(NullPointerException.class, () -> rewardService.calculateRewards());
    }

    @Test
    void testCalculateRewards_TransactionWithNegativeAmount() {
        List<Transaction> txs = List.of(new Transaction(1L, -100, LocalDate.of(2024, 4, 10)));
        when(transactionRepository.findAll()).thenReturn(txs);
        var results = rewardService.calculateRewards();
        assertEquals(1, results.size());
        assertEquals(0, results.get(0).getTotalPoints());
    }
}