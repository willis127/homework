package net.willis.homework.service;

import java.time.LocalDateTime;
import java.util.List;

import net.willis.homework.model.Incident;
import net.willis.homework.repository.InMemoryIncidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@EnableCaching
public class IncidentServiceTest {

    @InjectMocks
    private IncidentService incidentService;

    @Mock
    private InMemoryIncidentRepository incidentRepository;

    @Mock
    private CacheManager cacheManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Clear the cache before each test
        when(cacheManager.getCache("incidents")).thenReturn(mock(org.springframework.cache.Cache.class));
    }

    @Test
    void createIncident_ShouldCreateNewIncident() {
        // Arrange
        Incident incident = Incident.createNewIncident(
                "Test Title", "Test Description", Incident.Status.OPEN, Incident.Priority.HIGH);

        when(incidentRepository.save(any(String.class), any(String.class), any(Incident.Status.class), any(Incident.Priority.class))).thenReturn(incident);

        // Act
        Incident createdIncident = incidentService.createIncident(
                "Test Title", "Test Description", Incident.Status.OPEN, Incident.Priority.HIGH);

        // Assert
        assertNotNull(createdIncident);
        assertNotNull(createdIncident.getId());
        assertEquals("Test Title", createdIncident.getTitle());
        assertEquals("Test Description", createdIncident.getDescription());
        assertEquals(Incident.Status.OPEN, createdIncident.getStatus());
        assertEquals(Incident.Priority.HIGH, createdIncident.getPriority());

        verify(incidentRepository, times(1)).save(any(String.class), any(String.class), any(Incident.Status.class), any(Incident.Priority.class));
    }

    @Test
    public void getIncidentById_ShouldReturnIncident_WhenIdExists() {
        Incident incident = Incident.createNewIncident(
                "Test Title", "Test Description", Incident.Status.OPEN, Incident.Priority.HIGH);

        when(incidentRepository.findById(incident.getId())).thenReturn(incident);

        Incident retrievedIncident = incidentService.getIncidentById(incident.getId());

        assertNotNull(retrievedIncident);
        assertEquals(incident.getId(), retrievedIncident.getId());
        assertEquals("Test Title", retrievedIncident.getTitle());
    }

    @Test
    public void getIncidentById_ShouldReturnNull_WhenIdDoesNotExist() {
        when(incidentRepository.findById(1L)).thenReturn(null);

        Incident retrievedIncident = incidentService.getIncidentById(1L);
        assertNull(retrievedIncident);
    }

    @Test
    public void getAllIncidents_ShouldReturnEmptyList_WhenNoIncidents() {
        when(incidentRepository.findAll()).thenReturn(List.of());

        List<Incident> incidents = incidentService.getAllIncidents();
        assertNotNull(incidents);
        assertTrue(incidents.isEmpty());
    }

    @Test
    public void getAllIncidents_ShouldReturnListOfIncidents_WhenIncidentsExist() {
        Incident incident1 = Incident.createNewIncident(
                "Title1", "Description1", Incident.Status.OPEN, Incident.Priority.HIGH);
        Incident incident2 = Incident.createNewIncident(
                "Title2", "Description2", Incident.Status.IN_PROGRESS, Incident.Priority.MEDIUM);

        when(incidentRepository.findAll()).thenReturn(List.of(incident1, incident2));

        List<Incident> incidents = incidentService.getAllIncidents();
        assertNotNull(incidents);
        assertEquals(2, incidents.size());
    }

    @Test
    public void updateIncident_ShouldUpdateExistingIncident() {
        // Arrange
        Incident incident = Incident.createNewIncident(
                "Test Title", "Test Description", Incident.Status.OPEN, Incident.Priority.HIGH);

        Incident updatedIncident = new Incident();
        updatedIncident.setId(incident.getId());
        updatedIncident.setTitle("Updated Title");
        updatedIncident.setDescription("Updated Description");
        updatedIncident.setStatus(Incident.Status.IN_PROGRESS);
        updatedIncident.setPriority(Incident.Priority.LOW);
        updatedIncident.setCreatedAt(incident.getCreatedAt());
        updatedIncident.updateTimestamp();

        // 使用 anyLong() 匹配器来匹配 ID
        when(incidentRepository.update(anyLong(), any(Incident.class))).thenReturn(updatedIncident);

        // Act
        Incident result = incidentService.updateIncident(incident.getId(), updatedIncident);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(Incident.Status.IN_PROGRESS, result.getStatus());
        assertEquals(Incident.Priority.LOW, result.getPriority());
    }

    @Test
    public void deleteIncident_ShouldReturnTrue_WhenIdExists() {
        Incident incident = Incident.createNewIncident(
                "Test Title", "Test Description", Incident.Status.OPEN, Incident.Priority.HIGH);

        when(incidentRepository.deleteById(incident.getId())).thenReturn(true);

        boolean result = incidentService.deleteIncident(incident.getId());

        assertTrue(result);
    }

    @Test
    public void deleteIncident_ShouldReturnFalse_WhenIdDoesNotExist() {
        when(incidentRepository.deleteById(1L)).thenReturn(false);

        boolean result = incidentService.deleteIncident(1L);

        assertFalse(result);
    }

    @Test
    public void clearIncidents_ShouldClearAllIncidents() {
        Incident incident1 = Incident.createNewIncident(
                "Title1", "Description1", Incident.Status.OPEN, Incident.Priority.HIGH);
        Incident incident2 = Incident.createNewIncident(
                "Title2", "Description2", Incident.Status.IN_PROGRESS, Incident.Priority.MEDIUM);

        when(incidentRepository.findAll()).thenReturn(List.of(incident1, incident2));

        incidentService.clearIncidents();

        verify(incidentRepository, times(1)).clear();
    }
}
