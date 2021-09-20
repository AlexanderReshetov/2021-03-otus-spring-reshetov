package ru.otus.loader.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.loader.dto.ResponseItemDto;
import ru.otus.loader.service.LoadItemService;
import ru.otus.loader.service.exception.ItemException;

@RestController
public class ItemController {
    private final LoadItemService loadItemService;

    @Autowired
    public ItemController(LoadItemService loadItemService) {
        this.loadItemService = loadItemService;
    }

    @Timed("REST_GET_ITEM_BY_ID")
    @GetMapping("/items/{id}")
    public ResponseEntity<ResponseItemDto> getItemById(@RequestHeader("Authorization") String token, @PathVariable("id") Long id) {
        return ResponseEntity.ok(ResponseItemDto.toDto(loadItemService.getItemById(token, id)));
    }

    @ExceptionHandler(ItemException.class)
    public ResponseEntity<String> errorLoadingItem(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
