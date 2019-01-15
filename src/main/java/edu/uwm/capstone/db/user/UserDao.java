package edu.uwm.capstone.db.user;

import java.time.LocalDateTime;
import java.util.List;
import edu.uwm.capstone.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import edu.uwm.capstone.sql.dao.BaseDao;
import edu.uwm.capstone.sql.dao.BaseRowMapper;

public class UserDao extends BaseDao<User> {

    private static final Logger LOG = LoggerFactory.getLogger(UserDao.class);

    /**
     * Create a {@link User} object.s
     * @param user {@link User}
     * @return {@link User}
     */
    @Override
    public User create(User user) {
        // validate input
        if (user == null) {
            throw new RuntimeException("Request to create a new user received null");
        } else if (user.getId() != null) {
            throw new RuntimeException("When creating a new user the id should be null, but was set to " + user.getId());
        }

        LOG.trace("Creating user {}", user);
        user.setCreatedDate(LocalDateTime.now());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = this.jdbcTemplate.update(sql("createUser"),
                new MapSqlParameterSource(rowMapper.mapObject(user)), keyHolder, new String[]{BaseRowMapper.BaseColumnType.ID.name()});

        if (result != 1) {
            throw new RuntimeException("Failed attempt to create user " + user.toString() + " affected " + result + " rows");
        }

        Long id = keyHolder.getKey().longValue();
        user.setId(id);
        return user;
    }

    /**
     * Retrieve a {@link User} object by its {@link User#id}.
     * @param id long
     * @return {@link User}
     */
    @Override
    public User read(long id) {
        LOG.trace("Reading user {}", id);
        try {
            return (User) this.jdbcTemplate.queryForObject(sql("readUser"), new MapSqlParameterSource("id", id), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }
    
    /**
     * Retrieve a {@link User} object by its {@link User#email}.
     * @param email String
     * @return {@link User}
     */
    public User readByEmail(String email) {
        LOG.trace("Reading user {}", email);
        try {
            return (User) this.jdbcTemplate.queryForObject(sql("readUserByEmail"), new MapSqlParameterSource("email", email), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }

    /**
     * Update the provided {@link User} object.
     * @param user {@link User}
     */
    @Override
    public void update(User user) {
        if (user == null) {
            throw new RuntimeException("Request to update a user received null");
        } else if (user.getId() == null) {
            throw new RuntimeException("When updating a User the id should not be null");
        }

        LOG.trace("Updating user {}", user);
        user.setUpdatedDate(LocalDateTime.now());
        int result = this.jdbcTemplate.update(sql("updateUser"), new MapSqlParameterSource(rowMapper.mapObject(user)));

        if (result != 1) {
            throw new RuntimeException("Failed attempt to update user " + user.toString() + " affected " + result + " rows");
        }
    }

    /**
     * Delete a {@link User} object by its {@link User#id}.
     * @param id long
     */
    @Override
    public void delete(long id) {
        LOG.trace("Deleting user {}", id);
        int result = this.jdbcTemplate.update(sql("deleteUser"), new MapSqlParameterSource("id", id));
        if (result != 1) {
            throw new RuntimeException("Failed attempt to update user " + id + " affected " + result + " rows");
        }
    }
    
    /**
     * Delete a {@link User} object by its {@link User#email}.
     * @param email String
     */
    public void delete_by_email(String email) {
        LOG.trace("Deleting user {}", email);
        int result = this.jdbcTemplate.update(sql("deleteUserByEmail"), new MapSqlParameterSource("email", email));
        if (result != 1) {
            throw new RuntimeException("Failed attempt to update user " + email + " affected " + result + " rows");
        }
    }

    public List<User> readMany(long userid) {
        throw new RuntimeException("Can't retreive many users.");
    }
}