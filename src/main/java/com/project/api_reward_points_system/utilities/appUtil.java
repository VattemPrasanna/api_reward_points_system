package com.project.api_reward_points_system.utilities;

import com.project.api_reward_points_system.model.ErrorResponse;
import org.springframework.http.HttpStatus;

public final class appUtil {

    public static ErrorResponse globalErrorResponse(String requestParam, String errorMessage, HttpStatus statusCode) {
        ErrorResponse response = generateErrorResponse(requestParam, errorMessage, statusCode.name());
        return response;
    }

    private static ErrorResponse generateErrorResponse(String requestParam, String errorMessage, String statusCode) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(errorMessage);
        response.setStatusCode(statusCode);
        response.setDetails(requestParam);
        return response;
    }
}
