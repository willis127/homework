package net.willis.homework.controller;

import net.willis.homework.model.Incident;
import net.willis.homework.service.IncidentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IncidentControllerTest {

    @Mock
    private IncidentService incidentService;

    @InjectMocks
    private IncidentController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateIncident() {
        Incident incident = new Incident("Test Title", "Test Description", Incident.Status.OPEN, Incident.Priority.HIGH);
        when(incidentService.createIncident(any(Incident.class))).thenReturn(incident);

        ResponseEntity<Incident> response = controller.createIncident(incident);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(incident, response.getBody());
    }

    @Test
    public void testGetIncidentById_NotFound() {
        when(incidentService.getIncidentById(1L)).thenReturn(null);

        ResponseEntity<Incident> response = controller.getIncidentById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Add tests for other endpoints as needed
}