package com.project.api_reward_points_system.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PropertyConfig {
    @Value("${reward.pointsPerDollar}")
    private int pointsPerDollar;
    @Value("${reward.maximumRange}")
    private int maximumRange;
    @Value("${reward.minimumRange}")
    private int minimumRange;
    @Value("${reward.timePeriod}")
    private int timePeriod;
}
