package ru.otus.homework7.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework7.repository.exception.BookNotExistsException;
import ru.otus.homework7.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BookDaoJpaImpl implements BookDaoJpa {
    @PersistenceContext
    private EntityManager entityManager;

    public Long insert(Book book) {
        entityManager.persist(book);
        return book.getId();
    }

    public void update(Book book) {
        entityManager.merge(book);
    }

    public void delete(Long id) {
        Query query = entityManager.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public List<Book> findAll() {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b order by b.id", Book.class);
        return query.getResultList();
    }

    public Book findById(Long id) {
        Book book = entityManager.find(Book.class, id);
        if (book != null) {
            return book;
        }
        else throw new BookNotExistsException("There is no book with id = " + id);
    }
}
