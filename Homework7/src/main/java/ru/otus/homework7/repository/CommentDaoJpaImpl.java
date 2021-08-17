package ru.otus.homework7.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework7.model.Comment;
import ru.otus.homework7.repository.exception.CommentNotExistsException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentDaoJpaImpl implements CommentDaoJpa {
    @PersistenceContext
    private EntityManager entityManager;

    public Long insert(Comment comment) {
        entityManager.persist(comment);
        return comment.getId();
    }

    public void update(Comment comment) {
        entityManager.merge(comment);
    }

    public void delete(Long id) {
        Query query = entityManager.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public List<Comment> findAll() {
        TypedQuery<Comment> query = entityManager.createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }

    public Comment findById(Long id) {
        Comment comment = entityManager.find(Comment.class, id);
        if (comment != null) {
            return comment;
        }
        else throw new CommentNotExistsException("There is no comment with id = " + id);
    }
}
