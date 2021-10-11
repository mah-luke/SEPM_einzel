package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import enums.Sex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
public class HorseJdbcDao implements HorseDao {
    private final JdbcTemplate jdbcTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final FoodJdbcDao foodJdbcDao;

    private static final String TABLE_NAME = "horse";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
    private static final String SQL_CREATE = "INSERT INTO " + TABLE_NAME + " (NAME, DESCRIPTION, DOB, SEX, FOODID) VALUES ( ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET name = ?, description = ?, dob = ?, sex = ?, foodid = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";


    public HorseJdbcDao(JdbcTemplate jdbcTemplate, FoodJdbcDao foodJdbcDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.foodJdbcDao = foodJdbcDao;
    }

    @Override
    public List<Horse> getAll(Map<String, String> qparams) {
        StringBuilder query = new StringBuilder(SQL_SELECT_ALL);

        Iterator<String> keys = qparams.keySet().iterator();
        if (keys.hasNext()) query.append(" WHERE");
        for (int i = 0; i < qparams.size(); i++) {
            String key = keys.next();
            if (key.equalsIgnoreCase("DOB")) query.append(String.format(" UPPER(%s) <= ?", key));
            else if (key.equalsIgnoreCase("SEX")) query.append(String.format(" UPPER(%s) = ?", key));
            else if (key.equalsIgnoreCase("FOODID")) query.append(String.format(" %s = ?", key));
            else query.append(String.format(" UPPER(%s) like ?", key));

            query.append(keys.hasNext()? " AND" : ";");
        }

        try {
            String finalQuery = query.toString();
            return jdbcTemplate.query( con -> {
                PreparedStatement ps = con.prepareStatement(finalQuery);
                Iterator<String> keyIter = qparams.keySet().iterator();
                for (int i = 0; i < qparams.size(); i++) {

                    String key = keyIter.next();
                    if (key.equalsIgnoreCase("SEX") ||
                        key.equalsIgnoreCase("FOODID")
                    ) ps.setString(i + 1, qparams.get(key).toUpperCase());
                    else if (key.equalsIgnoreCase("DOB")) ps.setDate(i + 1, Date.valueOf(qparams.get(key)));
                    else ps.setString(i + 1, "%" + qparams.get(key).toUpperCase() + "%");
                }
                return ps;
            }, this::mapRow);
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not query all horses", e);
        }
    }

    @Override
    public Horse getHorse(long id) {
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
    public Horse createHorse(HorseDataDto dto) {
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
            if (id == null) throw new NotFoundException("Id '" + id + "' for horse does not exist in the database!");
            return getHorse(id.longValue());
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not create horse in the database", e);
        }
    }

    @Override
    public Horse editHorse(long id, HorseDataDto dto) {
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
    public Horse deleteHorse(long id) {
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
        horse.setFood(foodJdbcDao.getFood(result.getLong("foodId")));

        return horse;
    }
}
