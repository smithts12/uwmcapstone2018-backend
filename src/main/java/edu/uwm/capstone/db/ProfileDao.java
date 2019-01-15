package edu.uwm.capstone.db;

import java.time.LocalDateTime;
import java.util.List;

import edu.uwm.capstone.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import edu.uwm.capstone.model.profile.Profile;
import edu.uwm.capstone.sql.dao.BaseDao;
import edu.uwm.capstone.sql.dao.BaseRowMapper;

public class ProfileDao extends BaseDao<Profile> {

    private static final Logger LOG = LoggerFactory.getLogger(ProfileDao.class);

    /**
     * Create a {@link Profile} object.
     * @param profile {@link Profile}
     * @return {@link Profile}
     */
    @Override
    public Profile create(Profile profile) {
        // validate input
        if (profile == null) {
            throw new RuntimeException("Request to create a new Profile received null");
        } else if (profile.getId() != null) {
            throw new RuntimeException("When creating a new Profile the id should be null, but was set to " + profile.getId());
        }

        LOG.trace("Creating profile {}", profile);
        profile.setCreatedDate(LocalDateTime.now());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = this.jdbcTemplate.update(sql("createProfile"),
                new MapSqlParameterSource(rowMapper.mapObject(profile)), keyHolder, new String[]{BaseRowMapper.BaseColumnType.ID.name()});

        if (result != 1) {
            throw new RuntimeException("Failed attempt to create profile " + profile.toString() + " affected " + result + " rows");
        }

        Long id = keyHolder.getKey().longValue();
        profile.setId(id);
        return profile;
    }

    /**
     * Retrieve a {@link Profile} object by its {@link Profile#id}.
     * @param id long
     * @return {@link Profile}
     */
    @Override
    public Profile read(long id) {
        LOG.trace("Reading profile {}", id);
        try {
            return (Profile) this.jdbcTemplate.queryForObject(sql("readProfile"), new MapSqlParameterSource("id", id), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }

    /**
     * Retrieve a list of {@link Profile} objects by the {@link User#id} associated with it.
     * @param userid long
     * @return List<Map<String, Object>>
     */
    public List<Profile> readMany(long userid) {
        LOG.trace("Reading positions for user {}", userid);
        try {
            return this.jdbcTemplate.query(sql("readManyProfiles"), new MapSqlParameterSource("user_id", userid), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Update the provided {@link Profile} object.
     * @param profile {@link Profile}
     */
    @Override
    public void update(Profile profile) {
        if (profile == null) {
            throw new RuntimeException("Request to update a Profile received null");
        } else if (profile.getId() == null) {
            throw new RuntimeException("When updating a Profile the id should not be null");
        }

        LOG.trace("Updating profile {}", profile);
        profile.setUpdatedDate(LocalDateTime.now());
        int result = this.jdbcTemplate.update(sql("updateProfile"), new MapSqlParameterSource(rowMapper.mapObject(profile)));

        if (result != 1) {
            throw new RuntimeException("Failed attempt to update profile " + profile.toString() + " affected " + result + " rows");
        }
    }

    /**
     * Delete a {@link Profile} object by its {@link Profile#id}.
     * @param id long
     */
    @Override
    public void delete(long id) {
        LOG.trace("Deleting profile {}", id);
        int result = this.jdbcTemplate.update(sql("deleteProfile"), new MapSqlParameterSource("id", id));
        if (result != 1) {
            throw new RuntimeException("Failed attempt to update profile " + id + " affected " + result + " rows");
        }
    }

}
