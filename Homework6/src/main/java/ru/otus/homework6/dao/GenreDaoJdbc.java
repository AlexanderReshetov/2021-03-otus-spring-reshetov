package ru.otus.homework6.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework6.dao.exception.GenreNotExistsException;
import ru.otus.homework6.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    public List<Genre> findAll() {
        return namedParameterJdbcOperations.query("select id, name from genre order by id", new GenreMapper());
    }

    public Genre findById(Long id) {
        List<Genre> genreList = namedParameterJdbcOperations.query("select id, name from genre where id = :id",
                Collections.singletonMap("id", id),
                new GenreMapper());
        if (genreList.size() > 0) {
            return genreList.get(0);
        }
        else throw new GenreNotExistsException("There is no genre with id = " + id);
    }

    private static class GenreMapper implements RowMapper<Genre> {
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Genre(resultSet.getLong("id"), resultSet.getString("name"));
        }
    }
}