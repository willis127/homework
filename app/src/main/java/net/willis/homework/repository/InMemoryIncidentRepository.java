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
    public Incident save(Incident incident) {
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
        if (incidentMap.containsKey(id)) {
            updatedIncident.updateTimestamp();
            incidentMap.put(id, updatedIncident);
            return updatedIncident;
        }
        return null;
    }
}