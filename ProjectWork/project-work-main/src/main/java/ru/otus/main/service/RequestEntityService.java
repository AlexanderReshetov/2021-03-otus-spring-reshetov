package ru.otus.main.service;

import org.springframework.http.ResponseEntity;
import ru.otus.main.dto.ResponseAuctionDto;
import ru.otus.main.dto.ResponseItemDto;
import ru.otus.main.dto.ResponseRealmDto;
import ru.otus.main.dto.ResponseTokenDto;

import java.util.List;

public interface RequestEntityService {
    ResponseEntity<ResponseItemDto> getResponseItemDto(String token, Long id);
    ResponseEntity<List<ResponseAuctionDto>> getResponseAuctionDtoList(String token, Long realmId);
    ResponseEntity<List<ResponseRealmDto>> getResponseRealmDtoList(String token);
    ResponseEntity<ResponseTokenDto> getResponseTokenDto();
}
