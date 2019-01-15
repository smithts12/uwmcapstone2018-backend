package edu.uwm.capstone.db.education;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import edu.uwm.capstone.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import edu.uwm.capstone.model.education.Education;
import edu.uwm.capstone.sql.dao.BaseDao;
import edu.uwm.capstone.sql.dao.BaseRowMapper;

public class EducationDao extends BaseDao<Education> {

    private static final Logger LOG = LoggerFactory.getLogger(EducationDao.class);

    /**
     * Create a {@link Education} object.
     * @param education {@link Education}
     * @return {@link Education}
     */
    @Override
    public Education create(Education education) {
        // validate input
        if (education == null) {
            throw new RuntimeException("Request to create a new Education received null");
        } else if (education.getId() != null) {
            throw new RuntimeException("When creating a new Education the id should be null, but was set to " + education.getId());
        }

        LOG.trace("Creating education {}", education);
        education.setCreatedDate(LocalDateTime.now());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = this.jdbcTemplate.update(sql("createEducation"),
                new MapSqlParameterSource(rowMapper.mapObject(education)), keyHolder, new String[]{BaseRowMapper.BaseColumnType.ID.name()});

        if (result != 1) {
            throw new RuntimeException("Failed attempt to create education " + education.toString() + " affected " + result + " rows");
        }

        Long id = keyHolder.getKey().longValue();
        education.setId(id);
        return education;
    }

    /**
     * Retrieve a {@link Education} object by its {@link Education#id}.
     * @param id long
     * @return {@link Education}
     */
    @Override
    public Education read(long id) {
        LOG.trace("Reading education {}", id);
        try {
            return (Education) this.jdbcTemplate.queryForObject(sql("readEducation"), new MapSqlParameterSource("id", id), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }
    
    /**
     * Retrieve a list of {@link Education} objects by the {@link Education#id} associated with it.
     * @param userId long
     * @return List<Map<String, Object>>
     */
    public List<Education> readMany(long userid) {
        LOG.trace("Reading educations for user {}", userid);
        try {
            return this.jdbcTemplate.query(sql("readManyEducations"), new MapSqlParameterSource("user_id", userid), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }

    /**
     * Update the provided {@link Education} object.
     * @param education {@link Education}
     */
    @Override
    public void update(Education education) {
        if (education == null) {
            throw new RuntimeException("Request to update a Education received null");
        } else if (education.getId() == null) {
            throw new RuntimeException("When updating a Education the id should not be null");
        }

        LOG.trace("Updating education {}", education);
        education.setUpdatedDate(LocalDateTime.now());
        int result = this.jdbcTemplate.update(sql("updateEducation"), new MapSqlParameterSource(rowMapper.mapObject(education)));

        if (result != 1) {
            throw new RuntimeException("Failed attempt to update education " + education.toString() + " affected " + result + " rows");
        }
    }

    /**
     * Delete a {@link Education} object by its {@link Education#id}.
     * @param id long
     */
    @Override
    public void delete(long id) {
        LOG.trace("Deleting education {}", id);
        int result = this.jdbcTemplate.update(sql("deleteEducation"), new MapSqlParameterSource("id", id));
        if (result != 1) {
            throw new RuntimeException("Failed attempt to update education " + id + " affected " + result + " rows");
        }
    }
}
