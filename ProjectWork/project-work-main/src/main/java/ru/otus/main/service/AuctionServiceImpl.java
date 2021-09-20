package ru.otus.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.main.domain.AuctionLot;
import ru.otus.main.dto.ResponseAuctionDto;
import ru.otus.main.repository.AuctionRepository;
import ru.otus.main.service.exception.TokenException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuctionServiceImpl implements AuctionService {
    private final TokenService tokenService;
    private final AuctionRepository auctionRepository;
    private final ItemLoaderService itemLoaderService;
    private final RequestEntityService requestEntityService;

    @Autowired
    public AuctionServiceImpl(TokenService tokenService,
                              AuctionRepository auctionRepository,
                              ItemLoaderService itemLoaderService,
                              RequestEntityService requestEntityService) {
        this.tokenService = tokenService;
        this.auctionRepository = auctionRepository;
        this.itemLoaderService = itemLoaderService;
        this.requestEntityService = requestEntityService;
    }

    public List<AuctionLot> loadAllAuctionsByRealmId(Long realmId) {
        try {
            List<AuctionLot> auctionLotList = getAuctionsByRealmIdFromBlizzard(tokenService.getToken().getToken(), realmId);
            if (auctionLotList.size() > 0) {
                itemLoaderService.loadItemsByAuctions(auctionRepository.findAllByLocalDateTimeWhereItemIsNotExists(auctionLotList.get(0).getLocalDateTime()));
            }
            return auctionLotList;
        }
        catch (TokenException e) {
            return getAuctionsByRealmIdFromDatabase(realmId);
        }
    }

    public void save(ResponseAuctionDto responseAuctionDto) {
        if (responseAuctionDto.getItemPrice() != null) {
            updateAuctionRepository(Collections.singletonList(ResponseAuctionDto.toDomain(responseAuctionDto)));
        }
    }

    @Transactional(readOnly = true)
    private List<AuctionLot> getAuctionsByRealmIdFromDatabase(Long realmId) {
        return auctionRepository.findAllByRealmId(realmId);
    }

    private List<AuctionLot> getAuctionsByRealmIdFromBlizzard(String token, Long realmId) {
        final ResponseEntity<List<ResponseAuctionDto>> responseEntity = requestEntityService.getResponseAuctionDtoList(token, realmId);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            final List<AuctionLot> auctionLotList = responseEntity.getBody().stream()
                    .filter(responseAuctionDto -> responseAuctionDto.getItemPrice() != null)
                    .map(ResponseAuctionDto::toDomain)
                    .collect(Collectors.toList());
            updateAuctionRepository(auctionLotList);
            return auctionLotList;
        }
        else {
            return getAuctionsByRealmIdFromDatabase(realmId);
        }
    }

    @Transactional
    private void updateAuctionRepository(List<AuctionLot> auctionLotList) {
        auctionRepository.saveAll(auctionLotList);
    }
}