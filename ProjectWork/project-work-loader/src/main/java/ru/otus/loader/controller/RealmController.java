package ru.otus.loader.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.loader.dto.BlizzardRealmDto;
import ru.otus.loader.dto.ResponseRealmDto;
import ru.otus.loader.service.LoadRealmsService;
import ru.otus.loader.service.exception.RealmException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RealmController {
    private final LoadRealmsService loadRealmsService;

    @Autowired
    public RealmController(LoadRealmsService loadRealmsService) {
        this.loadRealmsService = loadRealmsService;
    }

    @Timed("REST_GET_ALL_REALMS")
    @GetMapping("/realms")
    public ResponseEntity<List<ResponseRealmDto>> getAllRealms(@RequestHeader("Authorization") String token) {
        final List<BlizzardRealmDto> realmDtoList = loadRealmsService.getAllRealms(token).getBlizzardRealmDtoList();
        return ResponseEntity.ok(realmDtoList.stream().map(ResponseRealmDto::toDto).collect(Collectors.toList()));
    }

    @ExceptionHandler(RealmException.class)
    public ResponseEntity<String> errorLoadingRealm(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
