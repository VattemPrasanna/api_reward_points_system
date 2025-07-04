package com.project.api_reward_points_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String data;
    private String errorMessage;
    private String status;
    private boolean hasError;
}
