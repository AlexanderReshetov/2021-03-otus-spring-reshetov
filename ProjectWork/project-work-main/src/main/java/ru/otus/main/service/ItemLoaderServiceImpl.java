package ru.otus.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.main.domain.Auction;
import ru.otus.main.domain.Item;
import ru.otus.main.dto.ItemAndTokenDto;

import java.util.List;

@Service
public class ItemLoaderServiceImpl implements ItemLoaderService {
    private final ItemService itemService;
    private String token = null;

    @Autowired
    public ItemLoaderServiceImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    public void loadItemsByAuctions(List<Auction> auctionList) {
        for (Auction auction : auctionList) {
            getItemByIdFromBlizzard(auction.getItemBlizzardId());
        }
    }

    private Item getItemByIdFromBlizzard(Long id) {
        final Item item;
        if (token != null) {
            item = itemService.loadItemById(token, id);
        }
        else {
            final ItemAndTokenDto itemAndTokenDto = itemService.loadItemWithTokenById(id);
            item = itemAndTokenDto.getItem();
            token = itemAndTokenDto.getToken().getToken();
        }
        return item;
    }
}
