package ru.otus.loader.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.loader.dto.ResponseItemDto;
import ru.otus.loader.service.LoadItemService;

@RestController
public class ItemController {
    private final LoadItemService loadItemService;

    @Autowired
    public ItemController(LoadItemService loadItemService) {
        this.loadItemService = loadItemService;
    }

    @Timed("REST_GET_ITEM_BY_ID")
    @GetMapping("/items/{id}")
    public ResponseEntity<ResponseItemDto> getItemById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ResponseItemDto.toDto(loadItemService.getItemById(id)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorLoadingAuction(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
