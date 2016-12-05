package com.n26.exercise.config;

import com.n26.exercise.service.StatisticsService;
import com.n26.exercise.service.StatisticsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Vladimir on 12/4/2016.
 */
@Configuration
public class ApplicationConfig {
    @Bean
    public StatisticsService statisticsService() {
        return new StatisticsServiceImpl();
    }
}
