package edu.uwm.capstone.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.uwm.capstone.jsoncomponent.LocalDateTimeJsonDeserializer;
import edu.uwm.capstone.jsoncomponent.LocalDateTimeJsonSerializer;
import edu.uwm.capstone.model.position.Position;
import edu.uwm.capstone.util.TestDataUtility;
import static edu.uwm.capstone.util.TestDataUtility.dateTimeFormatter;
import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PositionTest {

    @Autowired
    ApplicationContext applicationContext;

    @JsonSerialize(using = LocalDateTimeJsonSerializer.class)
    @JsonDeserialize(using = LocalDateTimeJsonDeserializer.class)
    private Position position;

    @Before
    public void setUp() {
        this.position = TestDataUtility.positionWithTestValues();
        this.position.setId(TestDataUtility.randomLong());
        this.position.setCreatedDate(TestDataUtility.randomLocalDateTime());
        this.position.setUpdatedDate(TestDataUtility.randomLocalDateTime());
    }

    @Test
    public void validateDeserialization() throws IOException, JSONException {
        String posJSON = new ObjectMapper().writeValueAsString(this.position);
        assertNotNull(posJSON);

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(posJSON);
        } catch (Exception e) {
            fail("Not valid JSON");
        }

        assertNotNull(jsonObj);

        assertEquals(dateTimeFormatter().format(this.position.getStartDate()), jsonObj.getString("startDate"));
        assertEquals(dateTimeFormatter().format(this.position.getEndDate()), jsonObj.getString("endDate"));
        assertEquals(dateTimeFormatter().format(this.position.getCreatedDate()), jsonObj.getString("createdDate"));
        assertEquals(dateTimeFormatter().format(this.position.getUpdatedDate()), jsonObj.getString("updatedDate"));
    }

    @Test
    public void validateSerialization() throws IOException, JSONException {
        String posJSON = new ObjectMapper().writeValueAsString(this.position);
        assertNotNull(posJSON);

        Position createdPos = new ObjectMapper().readValue(posJSON, Position.class);

        assertNotNull(createdPos);

        assertEquals(LocalDateTime.of(this.position.getStartDate().toLocalDate(), LocalTime.MIDNIGHT), createdPos.getStartDate());
        assertEquals(LocalDateTime.of(this.position.getEndDate().toLocalDate(), LocalTime.MIDNIGHT), createdPos.getEndDate());
        assertEquals(LocalDateTime.of(this.position.getCreatedDate().toLocalDate(), LocalTime.MIDNIGHT), createdPos.getCreatedDate());
        assertEquals(LocalDateTime.of(this.position.getUpdatedDate().toLocalDate(), LocalTime.MIDNIGHT), createdPos.getUpdatedDate());
    }
}
