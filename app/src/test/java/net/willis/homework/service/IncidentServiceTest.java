package net.willis.homework.service;

import net.willis.homework.model.Incident;
import net.willis.homework.model.Incident.Priority;
import net.willis.homework.model.Incident.Status;
import net.willis.homework.repository.InMemoryIncidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IncidentServiceTest {

    @Mock
    private InMemoryIncidentRepository repository;

    @InjectMocks
    private IncidentService incidentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateIncident() {
        Incident incident = new Incident("Network Issue", "Network down", Status.OPEN, Priority.HIGH);
        when(repository.save(incident)).thenReturn(incident);

        Incident createdIncident = incidentService.createIncident(incident);

        assertNotNull(createdIncident);
        assertEquals("Network Issue", createdIncident.getTitle());
        verify(repository, times(1)).save(incident);
    }

    @Test
    void testGetIncidentById() {
        Incident incident = new Incident("Server Issue", "Server is down", Status.IN_PROGRESS, Priority.MEDIUM);
        when(repository.findById(incident.getId())).thenReturn(incident);

        Incident retrievedIncident = incidentService.getIncidentById(incident.getId());

        assertNotNull(retrievedIncident);
        assertEquals("Server Issue", retrievedIncident.getTitle());
        assertEquals(Status.IN_PROGRESS, retrievedIncident.getStatus());
        verify(repository, times(1)).findById(incident.getId());
    }

    @Test
    void testGetAllIncidents() {
        Incident incident1 = new Incident("Login Issue", "Cannot login", Status.OPEN, Priority.LOW);
        Incident incident2 = new Incident("Payment Issue", "Payment failed", Status.CLOSED, Priority.HIGH);
        List<Incident> incidents = Arrays.asList(incident1, incident2);
        when(repository.findAll()).thenReturn(incidents);

        List<Incident> allIncidents = incidentService.getAllIncidents();

        assertEquals(2, allIncidents.size());
        assertEquals("Login Issue", allIncidents.get(0).getTitle());
        assertEquals("Payment Issue", allIncidents.get(1).getTitle());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testUpdateIncident() {
        Incident incident = new Incident("Database Issue", "Database slow", Status.OPEN, Priority.MEDIUM);
        when(repository.update(incident.getId(), incident)).thenReturn(incident);

        Incident updatedIncident = incidentService.updateIncident(incident.getId(), incident);

        assertNotNull(updatedIncident);
        assertEquals("Database Issue", updatedIncident.getTitle());
        verify(repository, times(1)).update(incident.getId(), incident);
    }

    @Test
    void testDeleteIncident() {
        Long incidentId = 1L;
        when(repository.deleteById(incidentId)).thenReturn(true);

        boolean isDeleted = incidentService.deleteIncident(incidentId);

        assertTrue(isDeleted);
        verify(repository, times(1)).deleteById(incidentId);
    }
}