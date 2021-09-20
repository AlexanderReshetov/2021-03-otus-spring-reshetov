package ru.otus.main.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.main.dto.TrendItemDto;
import ru.otus.main.service.TrendService;

import java.util.List;

@RestController
public class AnalyticController {
    private final TrendService trendService;

    @Autowired
    public AnalyticController(TrendService trendService) {
        this.trendService = trendService;
    }

    @Timed("REST_GET_TREND_BY_ITEM_BLIZZARD_ID")
    @GetMapping("/analytic/trend/blizzard")
    public ResponseEntity<List<TrendItemDto>> getTrendByItemBlizzardId(@RequestParam("realmId") Long realmId,
                                                                       @RequestParam("itemId") Long itemBlizzardId) {
        return ResponseEntity.ok(trendService.getTrendByItemBlizzardId(realmId, itemBlizzardId));
    }

    @Timed("REST_GET_TREND_BY_ITEM_ID")
    @GetMapping("/analytic/trend")
    public ResponseEntity<List<TrendItemDto>> getTrendByItemId(@RequestParam("realmId") Long realmId,
                                                               @RequestParam("itemId") Long itemId) {
        return ResponseEntity.ok(trendService.getTrendByItemId(realmId, itemId));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> analyticError(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}