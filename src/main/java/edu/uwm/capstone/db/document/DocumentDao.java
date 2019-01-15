package edu.uwm.capstone.db.document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import edu.uwm.capstone.model.document.Document;
import edu.uwm.capstone.model.education.Education;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import edu.uwm.capstone.sql.dao.BaseDao;
import edu.uwm.capstone.sql.dao.BaseRowMapper;

public class DocumentDao extends BaseDao<Document> {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentDao.class);

    /**
     * Create a {@link Document} object.s
     * @param Document {@link Document}
     * @return {@link Document}
     */
    @Override
    public Document create(Document Document) {
        // validate input
        if (Document == null) {
            throw new RuntimeException("Request to create a new Document received null");
        } else if (Document.getId() != null) {
            throw new RuntimeException("When creating a new Document the id should be null, but was set to " + Document.getId());
        }

        LOG.trace("Creating Document {}", Document);
        Document.setCreatedDate(LocalDateTime.now());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = this.jdbcTemplate.update(sql("createDocument"),
                new MapSqlParameterSource(rowMapper.mapObject(Document)), keyHolder, new String[]{BaseRowMapper.BaseColumnType.ID.name()});

        if (result != 1) {
            throw new RuntimeException("Failed attempt to create Document " + Document.toString() + " affected " + result + " rows");
        }

        Long id = keyHolder.getKey().longValue();
        Document.setId(id);
        return Document;
    }

    /**
     * Retrieve a {@link Document} object by its {@link Document#id}.
     * @param id long
     * @return {@link Document}
     */
    @Override
    public Document read(long id) {
        LOG.trace("Reading Document {}", id);
        try {
            return (Document) this.jdbcTemplate.queryForObject(sql("readDocument"), new MapSqlParameterSource("id", id), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }

    /**
     * Retrieve a list of {@link Document} objects by the {@link Document#id} associated with it.
     * @param userId long
     * @return List<Map<String, Object>>
     */
    public List<Document> readMany(long userid) {
        LOG.trace("Reading documents for user {}", userid);
        try {
            return this.jdbcTemplate.query(sql("readManyDocuemnts"), new MapSqlParameterSource("user_id", userid), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOG.trace("Exception: {}", e);
            return null;
        }
    }

    /**
     * Update the provided {@link Document} object.
     * @param Document {@link Document}
     */
    @Override
    public void update(Document Document) {
        if (Document == null) {
            throw new RuntimeException("Request to update a Document received null");
        } else if (Document.getId() == null) {
            throw new RuntimeException("When updating a Document the id should not be null");
        }

        LOG.trace("Updating Document {}", Document);
        Document.setUpdatedDate(LocalDateTime.now());
        int result = this.jdbcTemplate.update(sql("updateDocument"), new MapSqlParameterSource(rowMapper.mapObject(Document)));

        if (result != 1) {
            throw new RuntimeException("Failed attempt to update Document " + Document.toString() + " affected " + result + " rows");
        }
    }

    /**
     * Delete a {@link Document} object by its {@link Document#id}.
     * @param id long
     */
    @Override
    public void delete(long id) {
        LOG.trace("Deleting Document {}", id);
        int result = this.jdbcTemplate.update(sql("deleteDocument"), new MapSqlParameterSource("id", id));
        if (result != 1) {
            throw new RuntimeException("Failed attempt to update Document " + id + " affected " + result + " rows");
        }
    }
}