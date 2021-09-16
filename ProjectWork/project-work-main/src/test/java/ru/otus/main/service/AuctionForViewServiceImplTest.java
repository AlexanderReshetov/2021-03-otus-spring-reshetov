package ru.otus.main.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.main.repository.AuctionForViewRepository;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис получения данных аукционов для отображения должен")
public class AuctionForViewServiceImplTest {
    @Mock
    private AuctionForViewRepository auctionForViewRepository;
    @InjectMocks
    private AuctionForViewServiceImpl auctionForViewService;

    @Test
    @DisplayName("получить данные из репозитория аукционов")
    void shouldGetDataFromAuctionRepository() {
        auctionForViewService.getAllAuctionsByRealmId(1L);

        verify(auctionForViewRepository).findAllByRealmId(1L);
    }
}
