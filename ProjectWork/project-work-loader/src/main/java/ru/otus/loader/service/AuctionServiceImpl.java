package ru.otus.loader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.loader.dto.BlizzardAuctionDto;
import ru.otus.loader.dto.ResponseAuctionDto;
import ru.otus.loader.dto.ResponseTokenDto;
import ru.otus.loader.publisher.AuctionPublishGateway;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuctionServiceImpl implements AuctionService {
    private final GetTokenService getTokenService;
    private final LoadAuctionsService loadAuctionsService;
    private final AuctionPublishGateway auctionPublishGateway;
    private final List<Long> realmIdList;

    @Autowired
    public AuctionServiceImpl(
            GetTokenService getTokenService,
            LoadAuctionsService loadAuctionsService,
            AuctionPublishGateway auctionPublishGateway,
            @Value("#{'${realm-ids}'.split(',')}") List<Long> realmIdList) {
        this.getTokenService = getTokenService;
        this.loadAuctionsService = loadAuctionsService;
        this.auctionPublishGateway = auctionPublishGateway;
        this.realmIdList = realmIdList;
    }

    public void sendAuctions() {
        final LocalDateTime localDateTime = LocalDateTime.now();
        final String token = ResponseTokenDto.toDto(getTokenService.getToken()).getToken();
        for (Long realmId : realmIdList) {
            final List<BlizzardAuctionDto> blizzardAuctionDtoList = loadAuctionsService.getAllAuctionsByRealmId(token, realmId).getBlizzardAuctionDtoList();
            final List<ResponseAuctionDto> responseAuctionDtoList = blizzardAuctionDtoList.stream().map((BlizzardAuctionDto blizzardAuctionDto) -> {
                return ResponseAuctionDto.toDto(blizzardAuctionDto, realmId, localDateTime);
            }).collect(Collectors.toList());
            for (ResponseAuctionDto responseAuctionDto : responseAuctionDtoList) {
                auctionPublishGateway.auctionPublish(responseAuctionDto);
            }
        }
    }
}
