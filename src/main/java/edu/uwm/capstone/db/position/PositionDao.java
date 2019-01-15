package edu.uwm.capstone.db.position;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import edu.uwm.capstone.model.position.Position;
import edu.uwm.capstone.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import edu.uwm.capstone.sql.dao.BaseDao;
import edu.uwm.capstone.sql.dao.BaseRowMapper;

public class PositionDao extends BaseDao<Position> {

    private static final Logger LOG = LoggerFactory.getLogger(PositionDao.class);

    /**
     * Create a {@link Position} object.s
     * @param position {@link Position}
     * @return {@link Position}
     */
    @Override
    public Position create(Position position) {
        // validate input
        if (position == null) {
            throw new RuntimeException("Request to create a new position received null");
        } else if (position.getId() != null) {
            throw new RuntimeException("When creating a new position the id should be null, but was set to " + position.getId());
        }

        LOG.trace("Creating position {}", position);
        position.setCreatedDate(LocalDateTime.now());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = this.jdbcTemplate.update(sql("createPosition"),
                new MapSqlParameterSource(rowMapper.mapObject(position)), keyHolder, new String[]{BaseRowMapper.BaseColumnType.ID.name()});

        if (result != 1) {
            throw new RuntimeException("Failed attempt to create position " + position.toString() + " affected " + result + " rows");
        }

        Long id = keyHolder.getKey().longValue();
        position.setId(id);
        return position;
    }

    /**
     * Retrieve a {@link Position} object by its {@link Position#id}.
     * @param id long
     * @return {@link Position}
     */
    @Override
    public Position read(long id) {
        LOG.trace("Reading position {}", id);
        try {
            return (Position) this.jdbcTemplate.queryForObject(sql("readPosition"), new MapSqlParameterSource("id", id), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }
    
    /**
     * Retrieve a list of {@link Position} objects by the {@link User#id} associated with it.
     * @param userid long
     * @return List<Map<String, Object>>
     */
    public List<Position> readMany(long userid) {
        LOG.trace("Reading positions for user {}", userid);
        try {
            return this.jdbcTemplate.query(sql("readManyPositions"), new MapSqlParameterSource("user_id", userid), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }

    /**
     * Update the provided {@link Position} object.
     * @param position {@link Position}
     */
    @Override
    public void update(Position position) {
        if (position == null) {
            throw new RuntimeException("Request to update a position received null");
        } else if (position.getId() == null) {
            throw new RuntimeException("When updating a Position the id should not be null");
        }

        LOG.trace("Updating position {}", position);
        position.setUpdatedDate(LocalDateTime.now());
        int result = this.jdbcTemplate.update(sql("updatePosition"), new MapSqlParameterSource(rowMapper.mapObject(position)));

        if (result != 1) {
            throw new RuntimeException("Failed attempt to update position " + position.toString() + " affected " + result + " rows");
        }
    }

    /**
     * Delete a {@link Position} object by its {@link Position#id}.
     * @param id long
     */
    @Override
    public void delete(long id) {
        LOG.trace("Deleting position {}", id);
        int result = this.jdbcTemplate.update(sql("deletePosition"), new MapSqlParameterSource("id", id));
        if (result != 1) {
            throw new RuntimeException("Failed attempt to update position " + id + " affected " + result + " rows");
        }
    }
}