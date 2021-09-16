package ru.otus.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;
import ru.otus.main.domain.Auction;
import ru.otus.main.dto.ResponseAuctionDto;
import ru.otus.main.repository.AuctionRepository;
import ru.otus.main.service.exception.TokenException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuctionServiceImpl implements AuctionService {
    private final TokenService tokenService;
    private final RestOperations restOperations;
    private final String host;
    private final String port;
    private final AuctionRepository auctionRepository;
    private final ItemLoaderService itemLoaderService;

    @Autowired
    public AuctionServiceImpl(TokenService tokenService,
                              RestOperations restOperations,
                              @Value("${loader-service.host}") String host,
                              @Value("${loader-service.port}") String port,
                              AuctionRepository auctionRepository,
                              ItemLoaderService itemLoaderService) {
        this.tokenService = tokenService;
        this.restOperations = restOperations;
        this.host = host;
        this.port = port;
        this.auctionRepository = auctionRepository;
        this.itemLoaderService = itemLoaderService;
    }

    public List<Auction> loadAllAuctionsByRealmId(Long realmId) {
        try {
            List<Auction> auctionList = getAuctionsByRealmIdFromBlizzard(tokenService.getToken().getToken(), realmId);
            if (auctionList.size() > 0) {
                itemLoaderService.loadItemsByAuctions(auctionRepository.findAllByItemIsNull(auctionList.get(0).getLocalDateTime()));
            }
            return auctionList;
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
    private List<Auction> getAuctionsByRealmIdFromDatabase(Long realmId) {
        return auctionRepository.findAllByRealmId(realmId);
    }

    private List<Auction> getAuctionsByRealmIdFromBlizzard(String token, Long realmId) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.put("Authorization", Collections.singletonList(token));
        final RequestEntity<?> requestEntity = RequestEntity
                .get("http://" + host + ":" + port + "/auctions/" + realmId)
                .headers(headers)
                .build();
        ResponseEntity<List<ResponseAuctionDto>> responseEntity = restOperations.exchange(requestEntity, new ParameterizedTypeReference<List<ResponseAuctionDto>>() {
        });
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            final List<Auction> auctionList = responseEntity.getBody().stream()
                    .filter(responseAuctionDto -> responseAuctionDto.getItemPrice() != null)
                    .map(ResponseAuctionDto::toDomain)
                    .collect(Collectors.toList());
            updateAuctionRepository(auctionList);
            return auctionList;
        }
        else {
            return getAuctionsByRealmIdFromDatabase(realmId);
        }
    }

    @Transactional
    private void updateAuctionRepository(List<Auction> auctionList) {
        auctionRepository.saveAll(auctionList);
    }
}