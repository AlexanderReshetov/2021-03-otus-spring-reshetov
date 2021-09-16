package ru.otus.main.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.otus.main.domain.Auction;
import ru.otus.main.domain.Item;
import ru.otus.main.domain.Realm;
import ru.otus.main.service.AuctionService;
import ru.otus.main.service.ItemService;
import ru.otus.main.service.RealmService;
import ru.otus.main.service.exception.ItemException;
import ru.otus.main.service.exception.TokenException;

import java.util.Collections;
import java.util.List;

@RestController
public class LoaderController {
    private final RealmService realmService;
    private final ItemService itemService;
    private final AuctionService auctionService;

    @Autowired
    public LoaderController(RealmService realmService,
                            ItemService itemService,
                            AuctionService auctionService) {
        this.realmService = realmService;
        this.itemService = itemService;
        this.auctionService = auctionService;
    }

    @Timed("REST_GET_ALL_REALMS")
    @GetMapping("/load/realms")
    public ResponseEntity<List<Realm>> getAllRealms() {
        try {
            return ResponseEntity.ok(realmService.loadAllRealms());
        }
        catch (TokenException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @Timed("REST_GET_ITEM_BY_ID")
    @GetMapping("/load/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(itemService.loadItemById(id));
        }
        catch (ItemException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Timed("REST_GET_AUCTIONS_BY_ID")
    @GetMapping("/load/auctions/{realmId}")
    public ResponseEntity<List<Auction>> loadAuctionsByRealmId(@PathVariable("realmId") Long realmId) {
        try {
            return ResponseEntity.ok(auctionService.loadAllAuctionsByRealmId(realmId));
        }
        catch (TokenException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> loaderError(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}