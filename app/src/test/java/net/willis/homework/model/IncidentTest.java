package net.willis.homework.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class IncidentTest {

    private Incident incident;

    @BeforeEach
    public void setUp() {
        // Initialize an Incident object before each test
        incident = new Incident("Test Title", "Test Description", Incident.Status.OPEN, Incident.Priority.HIGH);
    }

    @Test
    public void testIncidentInitialization() {
        // Test that the Incident is initialized correctly
        assertEquals("Test Title", incident.getTitle());
        assertEquals("Test Description", incident.getDescription());
        assertEquals(Incident.Status.OPEN, incident.getStatus());
        assertEquals(Incident.Priority.HIGH, incident.getPriority());

        // Test that createdAt and updatedAt are not null and are close to the current time
        assertNotNull(incident.getCreatedAt());
        assertNotNull(incident.getUpdatedAt());

        // Allow a margin of error for creation timestamp
        LocalDateTime now = LocalDateTime.now();
        assertTrue(incident.getCreatedAt().isBefore(now) || incident.getCreatedAt().isEqual(now));
    }

    @Test
    public void testUpdateTimestamp() {
        // Record the current updatedAt time
        LocalDateTime originalUpdatedAt = incident.getUpdatedAt();

        // Wait a moment to ensure the timestamp difference
        try {
            Thread.sleep(10); // 10 milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Update the timestamp
        incident.updateTimestamp();
        LocalDateTime newUpdatedAt = incident.getUpdatedAt();

        // Verify that the updatedAt timestamp has changed
        assertNotEquals(originalUpdatedAt, newUpdatedAt);
        assertTrue(newUpdatedAt.isAfter(originalUpdatedAt));
    }

    @Test
    public void testSettersAndGetters() {
        // Test setters and getters
        incident.setTitle("Updated Title");
        assertEquals("Updated Title", incident.getTitle());

        incident.setDescription("Updated Description");
        assertEquals("Updated Description", incident.getDescription());

        incident.setStatus(Incident.Status.IN_PROGRESS);
        assertEquals(Incident.Status.IN_PROGRESS, incident.getStatus());

        incident.setPriority(Incident.Priority.MEDIUM);
        assertEquals(Incident.Priority.MEDIUM, incident.getPriority());
    }
}