package ru.otus.main.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.main.domain.AuctionForView;
import ru.otus.main.domain.Item;
import ru.otus.main.service.AuctionForViewService;
import ru.otus.main.service.ItemForViewService;

import java.util.List;

@RestController
public class ViewController {
    private final AuctionForViewService auctionForViewService;
    private final ItemForViewService itemForViewService;

    @Autowired
    public ViewController(AuctionForViewService auctionForViewService, ItemForViewService itemForViewService) {
        this.auctionForViewService = auctionForViewService;
        this.itemForViewService = itemForViewService;
    }

    @Timed("REST_VIEW_AUCTIONS_BY_REALM_ID")
    @GetMapping("/view/auctions/{realmId}")
    public ResponseEntity<List<AuctionForView>> getAuctionsByRealmId(@PathVariable("realmId") Long realmId) {
        return ResponseEntity.ok(auctionForViewService.getAllAuctionsByRealmId(realmId));
    }

    @Timed("REST_VIEW_ITEM_BY_RU_NAME")
    @GetMapping("/view/items/ru")
    public ResponseEntity<List<Item>> getItemByRuName(@RequestParam("name") String ruName) {
        return ResponseEntity.ok(itemForViewService.getByRuName(ruName));
    }

    @Timed("REST_VIEW_ITEM_BY_EN_NAME")
    @GetMapping("/view/items/en")
    public ResponseEntity<List<Item>> getItemByEnName(@RequestParam("name") String enName) {
        return ResponseEntity.ok(itemForViewService.getByEnName(enName));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> viewerError(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}