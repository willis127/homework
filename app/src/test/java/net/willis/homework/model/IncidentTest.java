package net.willis.homework.model;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.math.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class IncidentTest {

    private List<Incident> incidents;

    @BeforeEach
    void setUp() {
        incidents = List.of(
                Incident.createNewIncident("title1", "description1", Incident.Status.OPEN, Incident.Priority.LOW),
                Incident.createNewIncident("title2", "description2", Incident.Status.IN_PROGRESS, Incident.Priority.MEDIUM),
                Incident.createNewIncident("title3", "description3", Incident.Status.CLOSED, Incident.Priority.HIGH)
        );
    }

    @Test
    void createNewIncident_ShouldCreateIncidentWithUniqueID() {
        Long previousId = 0L;
        for (Incident incident : incidents) {
            Long currentId = incident.getId();
            assertTrue(currentId > previousId, "ID should be greater than the previous ID");
            previousId = currentId;
        }
    }

    @Test
    void createNewIncident_ShouldSetCorrectProperties() {
        for (Incident incident : incidents) {
            assertNotNull(incident.getId(), "ID should not be null");
            assertNotNull(incident.getTitle(), "Title should not be null");
            assertNotNull(incident.getDescription(), "Description should not be null");
            assertNotNull(incident.getStatus(), "Status should not be null");
            assertNotNull(incident.getPriority(), "Priority should not be null");
            assertNotNull(incident.getCreatedAt(), "CreatedAt should not be null");
            assertNotNull(incident.getUpdatedAt(), "UpdatedAt should not be null");

            // 检查 createdAt 和 updatedAt 是否在合理的时间范围内
            LocalDateTime createdAt = incident.getCreatedAt();
            LocalDateTime updatedAt = incident.getUpdatedAt();
            ZonedDateTime createdZoned = createdAt.atZone(ZoneId.systemDefault());
            ZonedDateTime updatedZoned = updatedAt.atZone(ZoneId.systemDefault());

            long diffMillis = Math.abs(createdZoned.toInstant().toEpochMilli() - updatedZoned.toInstant().toEpochMilli());


            assertTrue(diffMillis <= 10, "CreatedAt and UpdatedAt should be within 10 milliseconds of each other upon creation");
        }
    }

    @Test
    void updateTimestamp_ShouldUpdateTimestamp() {
        LocalDateTime initialTime = LocalDateTime.now();
        Incident incident = incidents.get(0);
        incident.updateTimestamp();
        LocalDateTime updatedTime = incident.getUpdatedAt();
        assertTrue(updatedTime.isAfter(initialTime), "UpdatedAt should be after the initial time");
    }

    @Test
    void toString_ShouldReturnCorrectStringRepresentation() {
        for (Incident incident : incidents) {
            String expectedString = "Incident{" +
                    "id=" + incident.getId() +
                    ", title='" + incident.getTitle() + '\'' +
                    ", description='" + incident.getDescription() + '\'' +
                    ", status=" + incident.getStatus() +
                    ", priority=" + incident.getPriority() +
                    ", createdAt=" + incident.getCreatedAt() +
                    ", updatedAt=" + incident.getUpdatedAt() +
                    '}';
            assertEquals(expectedString, incident.toString(), "toString method should return correct string representation");
        }
    }
}
