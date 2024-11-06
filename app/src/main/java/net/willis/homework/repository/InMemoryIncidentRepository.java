package net.willis.homework.repository;

import net.willis.homework.model.Incident;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
@Repository
public class InMemoryIncidentRepository {

    // Using ConcurrentHashMap to store incident data
    private final Map<Long, Incident> incidentMap = new ConcurrentHashMap<>();

    public void clear() {
        incidentMap.clear();
    }
    // Save a new Incident
    public Incident save(String title, String description, Incident.Status status, Incident.Priority priority) {
        Incident incident = Incident.createNewIncident(title, description, status, priority);
        incidentMap.put(incident.getId(), incident);
        return incident;
    }

    // Retrieve an Incident by ID
    public Incident findById(Long id) {
        return incidentMap.get(id);
    }

    // Retrieve all Incidents
    public List<Incident> findAll() {
        return incidentMap.values().stream().collect(Collectors.toList());
    }

    // Delete an Incident by ID
    public boolean deleteById(Long id) {
        return incidentMap.remove(id) != null;
    }

    // Update an existing Incident
    public Incident update(Long id, Incident updatedIncident) {
        Incident existingIncident = incidentMap.get(id);
        if (existingIncident != null) {
            updatedIncident.updateTimestamp();
            updatedIncident.setId(id); // 保持原 id 不变
            updatedIncident.setCreatedAt(existingIncident.getCreatedAt()); // 保持原createAt
            incidentMap.put(id, updatedIncident);
            return updatedIncident;
        }
        return null;
    }
}