package ru.otus.homework6.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework6.dao.exception.AuthorNotExistsException;
import ru.otus.homework6.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    public List<Author> findAll() {
        return namedParameterJdbcOperations.query("select id, name from author order by id", new AuthorMapper());
    }

    public Author findById(Long id) {
        List<Author> authorList = namedParameterJdbcOperations.query("select id, name from author where id = :id",
                Collections.singletonMap("id", id),
                new AuthorMapper());
        if (authorList.size() > 0) {
            return authorList.get(0);
        }
        else throw new AuthorNotExistsException("There is no author with id = " + id);
    }

    private static class AuthorMapper implements RowMapper<Author> {
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Author(resultSet.getLong("id"), resultSet.getString("name"));
        }
    }
}
