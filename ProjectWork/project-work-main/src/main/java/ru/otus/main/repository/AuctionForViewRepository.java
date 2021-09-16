package ru.otus.main.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.main.domain.AuctionForView;

import java.util.List;

public interface AuctionForViewRepository extends JpaRepository<AuctionForView, Long> {
    @EntityGraph(value = "AuctionForView.fields")
    List<AuctionForView> findAllByRealmId(Long realmId);
}
