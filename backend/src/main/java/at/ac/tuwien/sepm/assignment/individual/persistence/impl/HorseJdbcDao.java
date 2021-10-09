package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import enums.Sex;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HorseJdbcDao implements HorseDao {
    private static final String TABLE_NAME = "horse";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
    private static final String SQL_CREATE = "INSERT INTO " + TABLE_NAME + " (NAME, DESCRIPTION, DOB, SEX, FOODID) VALUES ( ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET name = ?, description = ?, dob = ?, sex = ?, foodid = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public HorseJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Horse> getAll() throws PersistenceException {
        try {
            return jdbcTemplate.query( con -> con.prepareStatement(SQL_SELECT_ALL), this::mapRow);
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not query all horses", e);
        }
    }

    @Override
    public Horse getHorse(Long id) throws PersistenceException {
        try {
            List<Horse> result = jdbcTemplate.query( con -> {
                PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID);
                ps.setLong(1, id);
                return ps;
            }, this::mapRow);

            if (result.size() == 1) return result.get(0);
            else if (result.size() == 0) throw new NotFoundException("Horse with id '" + id + "' not found in database");
            else throw new PersistenceException("Getting by id returned multiple values for id " + id);
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not retrieve horse with id '" + id + "' from the database", e);
        }
    }

    @Override
    public Horse createHorse(HorseDataDto dto) throws PersistenceException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update( con -> {
                PreparedStatement ps = con.prepareStatement(SQL_CREATE, new String[] {"ID"});
                ps.setString(1, dto.name());
                ps.setString(2, dto.description());
                ps.setDate(3, dto.dob());
                ps.setString(4, dto.sex().toString());
                ps.setLong(5, dto.foodId());
                return ps;
            }, keyHolder);

            Number id = keyHolder.getKey();
            if (id == null) throw new NotFoundException("Id does not exist in the database!");
            return getHorse(id.longValue());
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not create horse in the database", e);
        }
    }

    @Override
    public Horse editHorse(long id, HorseDataDto dto) throws PersistenceException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update( con -> {
                PreparedStatement ps = con.prepareStatement(SQL_UPDATE, new String[] {"ID"});
                ps.setString(1, dto.name());
                ps.setString(2, dto.description());
                ps.setDate(3, dto.dob());
                ps.setString(4, dto.sex().toString());
                ps.setLong(5, dto.foodId());
                ps.setLong(6, id);
                return ps;
            }, keyHolder);

            Number ret_id = keyHolder.getKey();
            if (ret_id == null) throw new NotFoundException("Id does not exist in the database!");
            return getHorse(ret_id.longValue());
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not modify the given horse in the database", e);
        }
    }

    @Override
    public Horse deleteHorse(Long id) throws PersistenceException {
        Horse horse = getHorse(id);
        try {
            jdbcTemplate.update( con -> {
                PreparedStatement ps = con.prepareStatement(SQL_DELETE);
                ps.setLong(1, id);
                return ps;
            });
            return horse;
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not delete the given horse from the database", e);
        }
    }

    private Horse mapRow(ResultSet result, int rownum) throws SQLException {
        Horse horse = new Horse();
        horse.setId(result.getLong("id"));
        horse.setName(result.getString("name"));
        horse.setDescription(result.getString("description"));
        horse.setDob(result.getDate("dob"));
        horse.setSex(Sex.valueOf(result.getString("sex")));
        horse.setFoodId(result.getLong("foodId"));

        return horse;
    }
}
