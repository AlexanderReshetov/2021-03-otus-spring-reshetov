package ru.otus.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.main.dto.TrendItemDto;
import ru.otus.main.repository.AuctionRepository;

import java.util.List;

@Service
public class TrendServiceImpl implements TrendService {
    private final AuctionRepository auctionRepository;

    @Autowired
    public TrendServiceImpl(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public List<TrendItemDto> getTrendByItemBlizzardId(Long realmId, Long itemBlizzardId) {
        return auctionRepository.findTrendByBlizzardItemId(realmId, itemBlizzardId);
    }

    public List<TrendItemDto> getTrendByItemId(Long realmId, Long itemId) {
        return auctionRepository.findTrendByItemId(realmId, itemId);
    }
}