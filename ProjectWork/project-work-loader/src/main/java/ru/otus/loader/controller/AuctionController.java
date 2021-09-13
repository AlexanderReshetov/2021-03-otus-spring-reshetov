package ru.otus.loader.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.loader.dto.ResponseAuctionDto;
import ru.otus.loader.service.LoadAuctionsService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuctionController {
    private final LoadAuctionsService loadAuctionsService;

    @Autowired
    public AuctionController(LoadAuctionsService loadAuctionsService) {
        this.loadAuctionsService = loadAuctionsService;
    }

    @Timed("REST_GET_ALL_AUCTIONS_BY_REALM_ID")
    @GetMapping("/auctions/{realmId}")
    public ResponseEntity<List<ResponseAuctionDto>> getAllAuctionsByRealmId(@PathVariable("realmId") Long realmId) {
        return ResponseEntity.ok(loadAuctionsService.getAllAuctionsByRealmId(realmId).getBlizzardAuctionDtoList().stream().map(ResponseAuctionDto::toDto).collect(Collectors.toList()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorLoadingAuction(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
