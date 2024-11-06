package net.willis.homework.service;

import net.willis.homework.model.Incident;
import net.willis.homework.repository.InMemoryIncidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import java.util.List;

@Service
public class IncidentService {

    private final InMemoryIncidentRepository repository;

    // 使用构造函数注入
    public IncidentService(InMemoryIncidentRepository repository) {
        this.repository = repository;
    }

    // Clear all Incidents
    public void clearIncidents() {
        repository.clear();
    }
    // Create a new Incident
    public Incident createIncident(String title, String description, Incident.Status status, Incident.Priority priority) {
        return repository.save(title, description, status, priority);
    }

    // Get an Incident by ID
    @Cacheable(value = "incidents", key = "#id")
    public Incident getIncidentById(Long id) {
        return repository.findById(id);
    }

    // Get all Incidents
    public List<Incident> getAllIncidents() {
        return repository.findAll();
    }

    // Update an existing Incident
    public Incident updateIncident(Long id, Incident updatedIncident) {
        return repository.update(id, updatedIncident);
    }

    // Delete an Incident by ID
    @CacheEvict(value = "incidents", key = "#id")
    public boolean deleteIncident(Long id) {
        return repository.deleteById(id);
    }
}