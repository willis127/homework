package net.willis.homework.controller;

import net.willis.homework.controller.IncidentController;
import net.willis.homework.model.Incident;
import net.willis.homework.service.IncidentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IncidentControllerTest {

    @Mock
    private IncidentService incidentService;

    @InjectMocks
    private IncidentController incidentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateIncident() {
        // Arrange
        String title = "Test Title";
        String description = "Test Description";
        Incident.Status status = Incident.Status.OPEN;
        Incident.Priority priority = Incident.Priority.HIGH;

        Incident requestIncident = new Incident(title, description, status, priority);
        Incident createdIncident = new Incident(title, description, status, priority);
        createdIncident.setId(1L); // 假设生成的ID为1

        when(incidentService.createIncident(eq(title), eq(description), eq(status), eq(priority))).thenReturn(createdIncident);

        // Act
        ResponseEntity<Incident> response = incidentController.createIncident(requestIncident);

        // Assert
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(createdIncident.getId(), response.getBody().getId());
        assertEquals(createdIncident.getTitle(), response.getBody().getTitle());
        assertEquals(createdIncident.getDescription(), response.getBody().getDescription());
        assertEquals(createdIncident.getStatus(), response.getBody().getStatus());
        assertEquals(createdIncident.getPriority(), response.getBody().getPriority());
    }

    @Test
    public void testGetAllIncidents() {
        List<Incident> incidents = new ArrayList<>();
        incidents.add(new Incident("Title1", "Description1", Incident.Status.OPEN, Incident.Priority.MEDIUM));
        when(incidentService.getAllIncidents()).thenReturn(incidents);

        ResponseEntity<List<Incident>> response = incidentController.getAllIncidents();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(incidents, response.getBody());
    }
}