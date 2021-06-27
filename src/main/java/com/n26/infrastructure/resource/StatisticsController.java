package com.n26.infrastructure.resource;

import com.n26.domain.dto.StatisticDto;
import com.n26.domain.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService service;

    @GetMapping
    public ResponseEntity<StatisticDto> getStatistics(){
        return ResponseEntity.ok().body(service.getStatistic());
    }
}
