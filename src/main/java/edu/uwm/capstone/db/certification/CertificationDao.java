package edu.uwm.capstone.db.certification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import edu.uwm.capstone.model.certification.Certification;
import edu.uwm.capstone.model.company.Company;

import edu.uwm.capstone.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import edu.uwm.capstone.sql.dao.BaseDao;
import edu.uwm.capstone.sql.dao.BaseRowMapper;

public class CertificationDao extends BaseDao<Certification> {

    private static final Logger LOG = LoggerFactory.getLogger(CertificationDao.class);

    /**
     * Create a {@link Certification} object.s
     * @param Certification {@link Certification}
     * @return {@link Certification}
     */
    @Override
    public Certification create(Certification Certification) {
        // validate input
        if (Certification == null) {
            throw new RuntimeException("Request to create a new Certification received null");
        } else if (Certification.getId() != null) {
            throw new RuntimeException("When creating a new Certification the id should be null, but was set to " + Certification.getId());
        }

        LOG.trace("Creating Certification {}", Certification);
        Certification.setCreatedDate(LocalDateTime.now());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = this.jdbcTemplate.update(sql("createCertification"),
                new MapSqlParameterSource(rowMapper.mapObject(Certification)), keyHolder, new String[]{BaseRowMapper.BaseColumnType.ID.name()});

        if (result != 1) {
            throw new RuntimeException("Failed attempt to create Certification " + Certification.toString() + " affected " + result + " rows");
        }

        Long id = keyHolder.getKey().longValue();
        Certification.setId(id);
        return Certification;
    }

    /**
     * Retrieve a {@link Certification} object by its {@link Certification#id}.
     * @param id long
     * @return {@link Certification}
     */
    @Override
    public Certification read(long id) {
        LOG.trace("Reading Certification {}", id);
        try {
            return (Certification) this.jdbcTemplate.queryForObject(sql("readCertification"), new MapSqlParameterSource("id", id), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }
    
    /**
     * Retrieve a list of {@link Certification} objects by the {@link Certification#id} associated with it.
     * @param userId long
     * @return List<Map<String, Object>>
     */
    public List<Certification> readMany(long userid) {
        LOG.trace("Reading certifications for user {}", userid);
        try {
            return this.jdbcTemplate.query(sql("readManyCertifications"), new MapSqlParameterSource("user_id", userid), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }

    /**
     * Update the provided {@link Certification} object.
     * @param Certification {@link Certification}
     */
    @Override
    public void update(Certification Certification) {
        if (Certification == null) {
            throw new RuntimeException("Request to update a Certification received null");
        } else if (Certification.getId() == null) {
            throw new RuntimeException("When updating a Certification the id should not be null");
        }

        LOG.trace("Updating Certification {}", Certification);
        Certification.setUpdatedDate(LocalDateTime.now());
        int result = this.jdbcTemplate.update(sql("updateCertification"), new MapSqlParameterSource(rowMapper.mapObject(Certification)));

        if (result != 1) {
            throw new RuntimeException("Failed attempt to update Certification " + Certification.toString() + " affected " + result + " rows");
        }
    }

    /**
     * Delete a {@link Certification} object by its {@link Certification#id}.
     * @param id long
     */
    @Override
    public void delete(long id) {
        LOG.trace("Deleting Certification {}", id);
        int result = this.jdbcTemplate.update(sql("deleteCertification"), new MapSqlParameterSource("id", id));
        if (result != 1) {
            throw new RuntimeException("Failed attempt to update Certification " + id + " affected " + result + " rows");
        }
    }
}