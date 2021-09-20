package ru.otus.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.main.domain.Item;
import ru.otus.main.domain.Token;
import ru.otus.main.dto.ItemAndTokenDto;
import ru.otus.main.dto.ResponseItemDto;
import ru.otus.main.repository.ItemRepository;
import ru.otus.main.service.exception.ItemException;
import ru.otus.main.service.exception.TokenException;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    private final TokenService tokenService;
    private final RequestEntityService requestEntityService;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(TokenService tokenService, RequestEntityService requestEntityService, ItemRepository itemRepository) {
        this.tokenService = tokenService;
        this.requestEntityService = requestEntityService;
        this.itemRepository = itemRepository;
    }

    public Item loadItemById(Long id) {
        try {
            return getItemByIdFromBlizzard(tokenService.getToken().getToken(), id);
        }
        catch (TokenException e) {
            return getItemById(id).orElseThrow(() -> new ItemException("There are no such item!"));
        }
    }

    public Item loadItemById(String token, Long id) {
        try {
            return getItemByIdFromBlizzard(token, id);
        }
        catch (TokenException e) {
            return getItemById(id).orElseThrow(() -> new ItemException("There are no such item!"));
        }
    }

    public ItemAndTokenDto loadItemWithTokenById(Long id) {
        final Token token = tokenService.getToken();
        return new ItemAndTokenDto(getItemByIdFromBlizzard(token.getToken(), id), token);
    }

    @Transactional(readOnly = true)
    private Optional<Item> getItemById(Long id) {
        return Optional.ofNullable(itemRepository.findByBlizzardId(id));
    }

    private Item getItemByIdFromBlizzard(String token, Long id) {
        ResponseEntity<ResponseItemDto> responseEntity = requestEntityService.getResponseItemDto(token, id);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Item item = ResponseItemDto.toDomain(responseEntity.getBody());
            return updateItemInRepository(item);
        }
        else {
            return getItemById(id).orElseThrow(() -> new ItemException("There are no such item!"));
        }
    }

    @Transactional
    private Item updateItemInRepository(Item item) {
        final Item oldItem = itemRepository.findByBlizzardId(item.getBlizzardId());
        if (oldItem != null) {
            itemRepository.save(oldItem);
            return oldItem;
        }
        else {
            itemRepository.save(item);
            return item;
        }
    }
}
