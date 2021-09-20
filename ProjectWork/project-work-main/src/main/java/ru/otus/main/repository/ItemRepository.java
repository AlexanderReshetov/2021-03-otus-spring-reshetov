package ru.otus.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.main.domain.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByBlizzardId(Long blizzardId);
    @Query("select i from Item i where lower(i.ruName) like lower(concat('%', concat(:ru_name, '%')))")
    List<Item> findAllByRuName(@Param("ru_name") String ruName);
    @Query("select i from Item i where lower(i.enName) like lower(concat('%', concat(:en_name, '%')))")
    List<Item> findAllByEnName(@Param("en_name") String enName);
}
