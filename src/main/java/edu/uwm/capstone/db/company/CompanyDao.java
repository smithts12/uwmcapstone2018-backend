package edu.uwm.capstone.db.company;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

public class CompanyDao extends BaseDao<Company> {

    private static final Logger LOG = LoggerFactory.getLogger(CompanyDao.class);

    /**
     * Create a {@link Company} object.s
     * @param company {@link Company}
     * @return {@link Company}
     */
    @Override
    public Company create(Company company) {
        // validate input
        if (company == null) {
            throw new RuntimeException("Request to create a new company received null");
        } else if (company.getId() != null) {
            throw new RuntimeException("When creating a new company the id should be null, but was set to " + company.getId());
        }

        LOG.trace("Creating company {}", company);
        company.setCreatedDate(LocalDateTime.now());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = this.jdbcTemplate.update(sql("createCompany"),
                new MapSqlParameterSource(rowMapper.mapObject(company)), keyHolder, new String[]{BaseRowMapper.BaseColumnType.ID.name()});

        if (result != 1) {
            throw new RuntimeException("Failed attempt to create company " + company.toString() + " affected " + result + " rows");
        }

        Long id = keyHolder.getKey().longValue();
        company.setId(id);
        return company;
    }

    /**
     * Retrieve a {@link Company} object by its {@link Company#id}.
     * @param id long
     * @return {@link Company}
     */
    @Override
    public Company read(long id) {
        LOG.trace("Reading company {}", id);
        try {
            return (Company) this.jdbcTemplate.queryForObject(sql("readCompany"), new MapSqlParameterSource("id", id), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }
    
    /**
     * Retrieve a list of {@link Company} objects by the {@link Company#id} associated with it.
     * @param userId long
     * @return List<Map<String, Object>>
     */
    public List<Company> readMany(long userid) {
        LOG.trace("Reading companies for user {}", userid);
        try {
            return this.jdbcTemplate.query(sql("readManyCompanies"), new MapSqlParameterSource("user_id", userid), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }

    /**
     * Update the provided {@link Company} object.
     * @param company {@link Company}
     */
    @Override
    public void update(Company company) {
        if (company == null) {
            throw new RuntimeException("Request to update a company received null");
        } else if (company.getId() == null) {
            throw new RuntimeException("When updating a Company the id should not be null");
        }

        LOG.trace("Updating company {}", company);
        company.setUpdatedDate(LocalDateTime.now());
        int result = this.jdbcTemplate.update(sql("updateCompany"), new MapSqlParameterSource(rowMapper.mapObject(company)));

        if (result != 1) {
            throw new RuntimeException("Failed attempt to update company " + company.toString() + " affected " + result + " rows");
        }
    }

    /**
     * Delete a {@link Company} object by its {@link Company#id}.
     * @param id long
     */
    @Override
    public void delete(long id) {
        LOG.trace("Deleting company {}", id);
        int result = this.jdbcTemplate.update(sql("deleteCompany"), new MapSqlParameterSource("id", id));
        if (result != 1) {
            throw new RuntimeException("Failed attempt to update company " + id + " affected " + result + " rows");
        }
    }
}