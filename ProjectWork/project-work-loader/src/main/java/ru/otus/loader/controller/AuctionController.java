package ru.otus.loader.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.loader.dto.BlizzardAuctionDto;
import ru.otus.loader.dto.BlizzardAuctionsDto;
import ru.otus.loader.dto.ResponseAuctionDto;
import ru.otus.loader.service.LoadAuctionsService;
import ru.otus.loader.service.exception.AuctionException;

import java.time.LocalDateTime;
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
    public ResponseEntity<List<ResponseAuctionDto>> getAllAuctionsByRealmId(
            @RequestHeader("Authorization") String token,
            @PathVariable("realmId") Long realmId) {
        final LocalDateTime localDateTime = LocalDateTime.now();
        final List<BlizzardAuctionDto> blizzardAuctionDtoList = loadAuctionsService.getAllAuctionsByRealmId(token, realmId).getBlizzardAuctionDtoList();
        final List<ResponseAuctionDto> responseAuctionDtoList = blizzardAuctionDtoList.stream().map((BlizzardAuctionDto blizzardAuctionDto) -> {
            return ResponseAuctionDto.toDto(blizzardAuctionDto, realmId, localDateTime);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(responseAuctionDtoList);
    }

    @ExceptionHandler(AuctionException.class)
    public ResponseEntity<String> errorLoadingAuction(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
