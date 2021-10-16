package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.FoodQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Food;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.FoodDao;
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
import java.util.ArrayList;
import java.util.List;

@Repository
public class FoodJdbcDao implements FoodDao {
    private final JdbcTemplate jdbcTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String TABLE_NAME = "FOOD";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
    private static final String SQL_CREATE = "INSERT INTO " + TABLE_NAME + " (NAME, DESCRIPTION, CALORIES) VALUES ( ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

    public FoodJdbcDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}


    @Override
    public List<Food> getAll(FoodQueryParamsDto qParams) {
        List<String> queries = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder(SQL_SELECT_ALL);

        if(qParams.name() != null) queries.add("UPPER(name) LIKE ?");
        if(qParams.description() != null) queries.add("UPPER(description) LIKE ?");
        if(qParams.calories() != null) queries.add("calories = ?");

        if(!queries.isEmpty())
            queryBuilder.append(" WHERE ").append(String.join(" AND ", queries)).append(";");
        String query = queryBuilder.toString();

        try {
            return jdbcTemplate.query(con -> {
                PreparedStatement ps = con.prepareStatement(query);
                int counter = 0;

                if (qParams.name() != null)
                    ps.setString(++counter, "%" + qParams.name().toUpperCase() + "%");
                if (qParams.description() != null)
                    ps.setString(++counter, "%" + qParams.description().toUpperCase() + "%");
                if (qParams.calories() != null)
                    ps.setDouble(++counter, qParams.calories());

                return ps;
            }, this::mapRow);
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not query all food", e);
        }
    }

    @Override
    public Food getFood(long id) {
        try {
            List<Food> result = jdbcTemplate.query( con -> {
                PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID);
                ps.setLong(1, id);
                return ps;
            }, this::mapRow);

            if (result.size() == 1) return result.get(0);
            else if (result.size() == 0) throw new NotFoundException("Food with id '" + id + "' not found in database");
            else throw new PersistenceException("Getting by id returned multiple values for id " + id);
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not retrieve food with id '" + id + "' from the database");
        }
    }

    @Override
    public Food createFood(FoodDataDto dto) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update( con -> {
                PreparedStatement ps = con.prepareStatement(SQL_CREATE, new String[] {"ID"});
                ps.setString(1, dto.name());
                ps.setString(2, dto.description());
                ps.setObject(3, dto.calories(), java.sql.Types.DOUBLE);
                return ps;
            }, keyHolder);

            Number id = keyHolder.getKey();
            if (id == null) throw new NotFoundException("Id '" + id + "' for food does not exist in the database!");
            return getFood(id.longValue());
        } catch (DataAccessException e) {
            throw new PersistenceException("Could not create horse in the database", e);
        }
    }

    private Food mapRow(ResultSet result, int rownum) throws SQLException {
        Food food = new Food();
        food.setId(result.getLong("id"));
        food.setName(result.getString("name"));
        food.setDescription(result.getString("description"));
        food.setCalories((Double) result.getObject("calories"));

        return food;
    }
}
