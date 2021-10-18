package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import aj.org.objectweb.asm.Type;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HorseJdbcDao implements HorseDao {
    private final JdbcTemplate jdbcTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final FoodJdbcDao foodJdbcDao;
    private final HorseMapper mapper;

    private static final String TABLE_NAME = "horse";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
    private static final String SQL_CREATE = "INSERT INTO " + TABLE_NAME + " (NAME, DESCRIPTION, DOB, SEX, FOODID, FATHERID, MOTHERID) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET name = ?, description = ?, dob = ?, sex = ?, foodid = ?, fatherId = ?, motherId = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";


    public HorseJdbcDao(JdbcTemplate jdbcTemplate, FoodJdbcDao foodJdbcDao, HorseMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.foodJdbcDao = foodJdbcDao;
        this.mapper = mapper;
    }

    @Override
    public List<Horse> getAll(HorseQueryParamsDto qParams) {
        List<String> queries = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder(SQL_SELECT_ALL);

        if (qParams.name() != null) queries.add("UPPER(name) LIKE ?");
        if (qParams.description() != null) queries.add("UPPER(description) LIKE ?");
        if (qParams.dob() != null) queries.add(("dob <= ?"));
        if (qParams.sex() != null) queries.add( "UPPER(sex) = ?");
        if (qParams.foodId() != null) queries.add("foodId = ?");

        if (!queries.isEmpty())
            queryBuilder.append(" WHERE ").append(String.join(" AND ", queries));
        if (qParams.limit() != null) queryBuilder.append(" LIMIT ?");
        String query = queryBuilder.append(";").toString();

        try {
            return jdbcTemplate.query( con -> {
                PreparedStatement ps = con.prepareStatement(query);
                int counter = 0;

                if (qParams.name() != null)
                    ps.setString(++counter, "%" + qParams.name().toUpperCase() + "%");
                if (qParams.description() != null)
                    ps.setString(++counter, "%" + qParams.description().toUpperCase() + "%");
                if (qParams.dob() != null)
                    ps.setObject(++counter, qParams.dob());
                if (qParams.sex() != null)
                    ps.setString(++counter, qParams.sex().toString().toUpperCase());
                if (qParams.foodId() != null)
                    ps.setLong(++counter, qParams.foodId());
                if (qParams.limit() != null)
                    ps.setLong(++counter, qParams.limit());

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
                ps.setObject(3, dto.dob());
                ps.setString(4, dto.sex().toString());
                ps.setObject(5, dto.foodId(), Type.LONG);
                ps.setObject(6, dto.fatherId(), Type.LONG);
                ps.setObject(7, dto.motherId(), Type.LONG);
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
                ps.setObject(3, dto.dob());
                ps.setString(4, dto.sex().toString());
                ps.setObject(5, dto.foodId(), Type.LONG);
                ps.setObject(6, dto.fatherId(), Type.LONG);
                ps.setObject(7, dto.motherId(), Type.LONG);
                ps.setLong(8, id);
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
        horse.setDob(result.getObject("dob", LocalDate.class));
        horse.setSex(Sex.valueOf(result.getString("sex")));

        Long foodId = (Long) result.getObject("foodId");
        horse.setFood(foodId == null? null : foodJdbcDao.getFood(foodId));

        Long fatherId = (Long) result.getObject("fatherId");
        horse.setFather(fatherId == null? null : mapper.entityToShallowEntity(getHorse(fatherId)));

        Long motherId = (Long) result.getObject("motherId");
        horse.setMother(motherId == null? null : mapper.entityToShallowEntity(getHorse(motherId)));

        return horse;
    }
}
