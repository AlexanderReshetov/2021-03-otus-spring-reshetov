package ru.otus.homework7.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework7.repository.exception.AuthorNotExistsException;
import ru.otus.homework7.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AuthorDaoJpaImpl implements AuthorDaoJpa {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Author> findAll() {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    public Author findById(Long id) {
        Author author = entityManager.find(Author.class, id);
        if (author != null) {
            return author;
        }
        else throw new AuthorNotExistsException("There is no author with id = " + id);
    }
}
