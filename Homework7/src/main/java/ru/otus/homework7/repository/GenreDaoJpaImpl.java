package ru.otus.homework7.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework7.repository.exception.GenreNotExistsException;
import ru.otus.homework7.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class GenreDaoJpaImpl implements GenreDaoJpa {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Genre> findAll() {
        TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    public Genre findById(Long id) {
        Genre genre = entityManager.find(Genre.class, id);
        if (genre != null) {
            return genre;
        }
        else throw new GenreNotExistsException("There is no genre with id = " + id);
    }
}