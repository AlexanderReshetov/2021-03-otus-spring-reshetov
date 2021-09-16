package ru.otus.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.main.domain.Auction;
import ru.otus.main.dto.TrendItemDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findAllByRealmId(Long realmId);
    @Query("select a from Auction a where a.localDateTime = :local_datetime and not exists(select 1 from Item i where a.itemBlizzardId = i.blizzardId)")
    List<Auction> findAllByItemIsNull(@Param("local_datetime") LocalDateTime localDateTime);
    @Query("select new ru.otus.main.dto.TrendItemDto(a.localDateTime, min(a.price)) from Auction a where a.realmId = :realm_id and a.itemBlizzardId = :item_blizzard_id group by a.localDateTime order by a.localDateTime")
    List<TrendItemDto> findTrendByBlizzardItemId(@Param("realm_id") Long realmId, @Param("item_blizzard_id") Long itemBlizzardId);
    @Query("select new ru.otus.main.dto.TrendItemDto(a.localDateTime, min(a.price)) from AuctionForView a inner join a.item i where a.realmId = :realm_id and i.id = :item_id group by a.localDateTime order by a.localDateTime")
    List<TrendItemDto> findTrendByItemId(@Param("realm_id") Long realmId, @Param("item_id") Long itemId);
}