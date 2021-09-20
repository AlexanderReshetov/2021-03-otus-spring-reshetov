package ru.otus.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.main.domain.Realm;

public interface RealmRepository extends JpaRepository<Realm, Long> {
}
