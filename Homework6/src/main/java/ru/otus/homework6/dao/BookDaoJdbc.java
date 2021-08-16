package ru.otus.homework6.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework6.dao.exception.BookNotExistsException;
import ru.otus.homework6.domain.Author;
import ru.otus.homework6.domain.Book;
import ru.otus.homework6.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    public void insert(Book book) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", book.getName());
        paramMap.put("authorId", book.getAuthor().getId());
        paramMap.put("genreId", book.getGenre().getId());
        namedParameterJdbcOperations.update("insert into book(name, authorId, genreId) values(:name, :authorId, :genreId)",
                paramMap);
    }

    public void update(Book book) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", book.getId());
        paramMap.put("name", book.getName());
        paramMap.put("authorId", book.getAuthor().getId());
        paramMap.put("genreId", book.getGenre().getId());
        namedParameterJdbcOperations.update("update book set name = :name, authorId = :authorId, genreId = :genreId where id = :id",
                paramMap);
    }

    public void delete(Long id) {
        namedParameterJdbcOperations.update("delete from book where id = :id", Collections.singletonMap("id", id));
    }

    public List<Book> findAll() {
        return namedParameterJdbcOperations.query("select b.id as id, b.name as name, a.id as authorId, a.name as authorName, g.id as genreId, g.name as genreName" +
                " from book b" +
                " inner join author a on a.id = b.authorId" +
                " inner join genre g on g.id = b.genreId" +
                " order by b.id", new BookMapper());
    }

    public Book findById(Long id) {
        List<Book> bookList = namedParameterJdbcOperations.query("select b.id as id, b.name as name, a.id as authorId, a.name as authorName, g.id as genreId, g.name as genreName" +
                " from book b" +
                " inner join author a on a.id = b.authorId" +
                " inner join genre g on g.id = b.genreId" +
                " where b.id = :id", Collections.singletonMap("id", id), new BookMapper());
        if (bookList.size() > 0) {
            return bookList.get(0);
        }
        else throw new BookNotExistsException("There is no book with id = " + id);
    }

    private static class BookMapper implements RowMapper<Book> {
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Book(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    new Author(resultSet.getLong("authorId"), resultSet.getString("authorName")),
                    new Genre(resultSet.getLong("genreId"), resultSet.getString("genreName")));
        }
    }
}
