package edu.uwm.capstone.db.address;

import edu.uwm.capstone.model.address.Address;
import edu.uwm.capstone.model.user.User;
import edu.uwm.capstone.sql.dao.BaseDao;
import edu.uwm.capstone.sql.dao.BaseRowMapper;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AddressDao extends BaseDao<Address> {

    private static final Logger LOG = LoggerFactory.getLogger(AddressDao.class);

    /**
     * Create a {@link Address} object.
     * @param address {@link Address}
     * @return {@link Address}
     */
    @Override
    public Address create(Address address) {
        // validate input
        if (address == null) {
            throw new RuntimeException("Request to create a new Address received null");
        } else if (address.getId() != null) {
            throw new RuntimeException("When creating a new Address the id should be null, but was set to " + address.getId());
        }

        LOG.trace("Creating address {}", address);
        address.setCreatedDate(LocalDateTime.now());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = this.jdbcTemplate.update(sql("createAddress"),
                new MapSqlParameterSource(rowMapper.mapObject(address)), keyHolder, new String[]{BaseRowMapper.BaseColumnType.ID.name()});

        if (result != 1) {
            throw new RuntimeException("Failed attempt to create address " + address.toString() + " affected " + result + " rows");
        }

        Long id = keyHolder.getKey().longValue();
        address.setId(id);
        return address;
    }

    /**
     * Retrieve a {@link Address} object by its {@link Address#id}.
     * @param id long
     * @return {@link Address}
     */
    @Override
    public Address read(long id) {
        LOG.trace("Reading address {}", id);
        try {
            return (Address) this.jdbcTemplate.queryForObject(sql("readAddress"), new MapSqlParameterSource("id", id), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }
    
    /**
     * Retrieve a list of {@link Address} objects by the {@link User#id} associated with it.
     * @param userid long
     * @return List<Map<String, Address>>
     */
    public List<Address> readMany(long userid) {
        LOG.trace("Reading addresses for user {}", userid);
        try {
            return this.jdbcTemplate.query(sql("readManyAddresses"), new MapSqlParameterSource("user_id", userid), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }

    /**
     * Update the provided {@link Address} object.
     * @param address {@link Address}
     */
    @Override
    public void update(Address address) {
        if (address == null) {
            throw new RuntimeException("Request to update a Address received null");
        } else if (address.getId() == null) {
            throw new RuntimeException("When updating a Address the id should not be null");
        }

        LOG.trace("Updating address {}", address);
        address.setUpdatedDate(LocalDateTime.now());
        int result = this.jdbcTemplate.update(sql("updateAddress"), new MapSqlParameterSource(rowMapper.mapObject(address)));

        if (result != 1) {
            throw new RuntimeException("Failed attempt to update address " + address.toString() + " affected " + result + " rows");
        }
    }

    /**
     * Delete a {@link Address} object by its {@link Address#id}.
     * @param id long
     */
    @Override
    public void delete(long id) {
        LOG.trace("Deleting address {}", id);
        int result = this.jdbcTemplate.update(sql("deleteAddress"), new MapSqlParameterSource("id", id));
        if (result != 1) {
            throw new RuntimeException("Failed attempt to update address " + id + " affected " + result + " rows");
        }
    }
}