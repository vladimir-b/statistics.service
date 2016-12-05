package com.n26.exercise.resource;

import com.n26.exercise.model.Statistics;
import com.n26.exercise.model.Transaction;
import com.n26.exercise.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * Created by Vladimir on 12/2/2016.
 */
@RestController
public class StatisticsController {
    @Autowired
    StatisticsService statisticsService;

   @RequestMapping(method=GET, path="/statistics")
    public Statistics statistics() {
        return statisticsService.getCurrentStatistics(System.currentTimeMillis());
    }

    @RequestMapping(method=POST, path="/transactions")
    public void transactions(Transaction transaction) {
        statisticsService.transactions(transaction, System.currentTimeMillis());
    }
}