package ru.otus.main.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.main.domain.Realm;
import ru.otus.main.service.exception.RealmNotExistsException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("Dao игровых миров должен")
public class RealmRepositoryTest {
    @Autowired
    private RealmRepository realmRepository;

    @Test
    @DisplayName("добавить игровой мир и получить его по ИД")
    void shouldAddRealmAndGetHimById() {
        final Long blizzardId = 1L;
        final String enName = "Goldrinn";
        final String ruName = "Голдринн";
        Realm realm = new Realm(null, blizzardId, enName, ruName);

        realm = realmRepository.save(realm);
        final Long newId = realm.getId();
        realm = realmRepository.findById(realm.getId()).orElseThrow(() -> new RealmNotExistsException("There is no realm with id = " + newId));

        assertEquals(blizzardId, realm.getBlizzardId());
        assertEquals(enName, realm.getEnName());
        assertEquals(ruName, realm.getRuName());
    }
}