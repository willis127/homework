package net.willis.homework.repository;

import java.util.*;
import java.math.*;

import net.willis.homework.model.Incident;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class InMemoryIncidentRepositoryTest {

    private InMemoryIncidentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryIncidentRepository();
    }

    @Test
    void save_ShouldAddNewIncident() {
        Incident incident = repository.save("Test Title", "Test Description", Incident.Status.OPEN, Incident.Priority.HIGH);
        assertNotNull(incident);
        assertNotNull(incident.getId());
        assertEquals("Test Title", incident.getTitle());
        assertEquals("Test Description", incident.getDescription());
    }

    @Test
    void findById_ShouldReturnIncidentById() {
        Incident incident = repository.save("Test Title", "Test Description", Incident.Status.OPEN, Incident.Priority.HIGH);
        Incident foundIncident = repository.findById(incident.getId());
        assertNotNull(foundIncident);
        assertEquals(incident.getId(), foundIncident.getId());
    }

    @Test
    void findAll_ShouldReturnAllIncidents() {
        repository.save("Test Title 1", "Test Description 1", Incident.Status.OPEN, Incident.Priority.HIGH);
        repository.save("Test Title 2", "Test Description 2", Incident.Status.CLOSED, Incident.Priority.MEDIUM);
        List<Incident> incidents = repository.findAll();
        assertNotNull(incidents);
        assertTrue(incidents.size() >= 2);
    }

    @Test
    void deleteById_ShouldRemoveIncidentById() {
        Incident incident = repository.save("Test Title", "Test Description", Incident.Status.OPEN, Incident.Priority.HIGH);
        boolean isDeleted = repository.deleteById(incident.getId());
        assertTrue(isDeleted);
        assertNull(repository.findById(incident.getId()));
    }

    @Test
    void update_ShouldUpdateExistingIncident() {
        Incident incident = repository.save("Test Title", "Test Description", Incident.Status.OPEN, Incident.Priority.HIGH);
        Incident updatedIncident = new Incident("Updated Title", "Updated Description", Incident.Status.IN_PROGRESS, Incident.Priority.LOW);
        Incident result = repository.update(incident.getId(), updatedIncident);
        assertNotNull(result);
        assertEquals(incident.getId(), result.getId());
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
    }
}
