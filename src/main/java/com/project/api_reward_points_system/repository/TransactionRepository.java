package com.project.api_reward_points_system.repository;

import com.project.api_reward_points_system.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(TransactionRepository.class);

    /**
     * Retrieves all transactions.
     * In a real application, this would connect to a database or external service.
     *
     * @return List of Transaction objects
     */
    public List<Transaction> findAll() {
        return Arrays.asList(
                new Transaction(1L, 120, LocalDate.of(2025, 4, 10)),
                new Transaction(1L, 60, LocalDate.of(2025, 4, 1)),
                new Transaction(1L, 300, LocalDate.of(2025, 4, 5)),
                new Transaction(1L, 80, LocalDate.of(2025, 5, 15)),
                new Transaction(1L, 40, LocalDate.of(2025, 6, 5)),
                new Transaction(2L, 30, LocalDate.of(2024, 1, 12)),
                new Transaction(2L, 150, LocalDate.of(2024, 2, 20)),
                new Transaction(2L, 60, LocalDate.of(2024, 3, 25)),
                new Transaction(3L, 500, LocalDate.of(2025, 3, 10)),
                new Transaction(3L, 620, LocalDate.of(2025, 9, 3)),
                new Transaction(4L, 30, LocalDate.of(2025, 10, 12)),
                new Transaction(4L, 150, LocalDate.of(2025, 12, 20)),
                new Transaction(4L, 60, LocalDate.of(2025, 11, 25))
        );
    }
}