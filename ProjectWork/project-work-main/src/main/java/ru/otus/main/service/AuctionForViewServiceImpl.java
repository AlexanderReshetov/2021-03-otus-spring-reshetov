package ru.otus.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.main.domain.AuctionForView;
import ru.otus.main.repository.AuctionForViewRepository;

import java.util.List;

@Service
public class AuctionForViewServiceImpl implements AuctionForViewService {
    private final AuctionForViewRepository auctionForViewRepository;

    @Autowired
    public AuctionForViewServiceImpl(AuctionForViewRepository auctionForViewRepository) {
        this.auctionForViewRepository = auctionForViewRepository;
    }

    @Override
    public List<AuctionForView> getAllAuctionsByRealmId(Long realmId) {
        return auctionForViewRepository.findAllByRealmId(realmId);
    }
}
