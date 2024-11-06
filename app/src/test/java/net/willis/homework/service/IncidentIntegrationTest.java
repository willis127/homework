package net.willis.homework.service;

import net.willis.homework.model.Incident;
import net.willis.homework.model.Incident.Priority;
import net.willis.homework.model.Incident.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableCaching
class IncidentIntegrationTest {

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        // 清除缓存和仓库中的数据，确保每个测试的独立性
        if (cacheManager.getCache("incidents") != null) {
            cacheManager.getCache("incidents").clear();
        }
        incidentService.clearIncidents(); // 清空仓库中的所有数据
    }

    @Test
    void testCreateAndRetrieveIncident() {
        Incident incident = new Incident("Network Outage", "The network is down", Status.OPEN, Priority.HIGH);
        Incident createdIncident = incidentService.createIncident(incident);

        assertNotNull(createdIncident);
        assertEquals("Network Outage", createdIncident.getTitle());
        assertEquals("The network is down", createdIncident.getDescription());
        assertEquals(Status.OPEN, createdIncident.getStatus());
        assertEquals(Priority.HIGH, createdIncident.getPriority());

        Incident retrievedIncident = incidentService.getIncidentById(createdIncident.getId());
        assertNotNull(retrievedIncident);
        assertEquals(createdIncident.getId(), retrievedIncident.getId());
        assertEquals(createdIncident.getTitle(), retrievedIncident.getTitle());
        assertEquals(createdIncident.getDescription(), retrievedIncident.getDescription());

        // 验证缓存
        assertNotNull(cacheManager.getCache("incidents").get(createdIncident.getId()));
    }

    @Test
    void testGetAllIncidents() {
        Incident incident1 = new Incident("Server Issue", "Server is down", Status.IN_PROGRESS, Priority.HIGH);
        Incident incident2 = new Incident("Database Issue", "Database is slow", Status.OPEN, Priority.MEDIUM);

        incidentService.createIncident(incident1);
        incidentService.createIncident(incident2);

        List<Incident> incidents = incidentService.getAllIncidents();
        assertEquals(2, incidents.size());
    }
    @Test
    void testUpdateIncident() {
        Incident incident = new Incident("Login Issue", "Cannot login to system", Status.OPEN, Priority.LOW);
        Incident createdIncident = incidentService.createIncident(incident);

        // 更新标题和状态
        createdIncident.setTitle("Updated Login Issue");
        createdIncident.setStatus(Status.IN_PROGRESS);
        incidentService.updateIncident(createdIncident.getId(), createdIncident);

        // 通过调用 getIncidentById 确保缓存更新
        Incident cachedIncident = incidentService.getIncidentById(createdIncident.getId());

        assertNotNull(cachedIncident);
        assertEquals("Updated Login Issue", cachedIncident.getTitle());
        assertEquals(Status.IN_PROGRESS, cachedIncident.getStatus());
    }
    @Test
    void testDeleteIncident() {
        Incident incident = new Incident("Memory Leak", "High memory usage detected", Status.IN_PROGRESS, Priority.MEDIUM);
        Incident createdIncident = incidentService.createIncident(incident);

        boolean isDeleted = incidentService.deleteIncident(createdIncident.getId());
        assertTrue(isDeleted);

        Incident retrievedIncident = incidentService.getIncidentById(createdIncident.getId());
        assertNull(retrievedIncident);

        // 验证缓存是否移除
        assertTrue(cacheManager.getCache("incidents").get(createdIncident.getId()) == null ||
                cacheManager.getCache("incidents").get(createdIncident.getId()).get() == null);
    }

}