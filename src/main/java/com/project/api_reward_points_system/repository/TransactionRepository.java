package com.project.api_reward_points_system.repository;

import com.project.api_reward_points_system.model.Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Simulates a data source for transactions.
 * Used mock data for testing purposes. We can configure a real database later with configuration.
 */
@Repository
public class TransactionRepository {
    public List<Transaction> findAll() {
        return Arrays.asList(
                new Transaction(1L, 120, LocalDate.of(2024, 4, 10)),
                new Transaction(1L, 60, LocalDate.of(2024, 4, 1)),
                new Transaction(1L, 300, LocalDate.of(2024, 4, 5)),
                new Transaction(1L, 80, LocalDate.of(2024, 5, 15)),
                new Transaction(1L, 40, LocalDate.of(2024, 6, 5)),
                new Transaction(2L, 30, LocalDate.of(2024, 1, 12)),
                new Transaction(2L, 150, LocalDate.of(2024, 2, 20)),
                new Transaction(2L, 60, LocalDate.of(2024, 3, 25)),
                new Transaction(3L, 500, LocalDate.of(2025, 3, 10)),
                new Transaction(3L, 620, LocalDate.of(2025, 9, 3)),
                new Transaction(4L, 30, LocalDate.of(2025, 1, 12)),
                new Transaction(4L, 150, LocalDate.of(2025, 2, 20)),
                new Transaction(4L, 60, LocalDate.of(2025, 3, 25))
        );
    }
}