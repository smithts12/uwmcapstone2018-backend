package edu.uwm.capstone.db.contact;

import edu.uwm.capstone.model.contact.Contact;
import edu.uwm.capstone.model.project.Project;
import edu.uwm.capstone.model.user.User;
import edu.uwm.capstone.sql.dao.BaseDao;
import edu.uwm.capstone.sql.dao.BaseRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ContactDao extends BaseDao<Contact> {

    private static final Logger LOG = LoggerFactory.getLogger(ContactDao.class);

    /**
     * Create a {@link Contact} object.
     * @param contact {@link Contact}
     * @return {@link Contact}
     */
    @Override
    public Contact create(Contact contact) {
        // validate input
        if (contact == null) {
            throw new RuntimeException("Request to create a new Contact received null");
        } else if (contact.getId() != null) {
            throw new RuntimeException("When creating a new Contact the id should be null, but was set to " + contact.getId());
        }

        LOG.trace("Creating contact {}", contact);
        contact.setCreatedDate(LocalDateTime.now());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = this.jdbcTemplate.update(sql("createContact"),
                new MapSqlParameterSource(rowMapper.mapObject(contact)), keyHolder, new String[]{BaseRowMapper.BaseColumnType.ID.name()});

        if (result != 1) {
            throw new RuntimeException("Failed attempt to create contact " + contact.toString() + " affected " + result + " rows");
        }

        Long id = keyHolder.getKey().longValue();
        contact.setId(id);
        return contact;
    }


    /**
     * Retrieve a {@link Contact} object by its {@link Contact#id}.
     * @param id long
     * @return {@link Contact}
     */
    @Override
    public Contact read(long id) {
        LOG.trace("Reading contact {}", id);
        try {
            return (Contact) this.jdbcTemplate.queryForObject(sql("readContact"), new MapSqlParameterSource("id", id), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }
    
    /**
     * Retrieve a list of {@link Contact} objects by the {@link User#id} associated with it.
     * @param userid long
     * @return List<Map<String, Object>>
     */
    public List<Contact> readMany(long userid) {
        LOG.trace("Reading contacts for user {}", userid);
        try {
            return this.jdbcTemplate.query(sql("readManyContacts"), new MapSqlParameterSource("user_id", userid), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }

    /**
     * Update the provided {@link Contact} object.
     * @param contact {@link Contact}
     */
    @Override
    public void update(Contact contact) {
        if (contact == null) {
            throw new RuntimeException("Request to update a Contact received null");
        } else if (contact.getId() == null) {
            throw new RuntimeException("When updating a Contact the id should not be null");
        }

        LOG.trace("Updating contact {}", contact);
        contact.setUpdatedDate(LocalDateTime.now());
        int result = this.jdbcTemplate.update(sql("updateContact"), new MapSqlParameterSource(rowMapper.mapObject(contact)));

        if (result != 1) {
            throw new RuntimeException("Failed attempt to update contact " + contact.toString() + " affected " + result + " rows");
        }
    }

    /**
     * Delete a {@link Contact} object by its {@link Contact#id}.
     * @param id long
     */
    @Override
    public void delete(long id) {
        LOG.trace("Deleting contact {}", id);
        int result = this.jdbcTemplate.update(sql("deleteContact"), new MapSqlParameterSource("id", id));
        if (result != 1) {
            throw new RuntimeException("Failed attempt to update contact " + id + " affected " + result + " rows");
        }
    }

}
