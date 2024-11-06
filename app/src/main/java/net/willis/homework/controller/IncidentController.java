package net.willis.homework.controller;

import net.willis.homework.model.Incident;
import net.willis.homework.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
@CrossOrigin(origins = "http://localhost:3000")
public class IncidentController {

    private final IncidentService incidentService;

    // 使用构造函数注入 IncidentService
    @Autowired
    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    // Create a new Incident
    @PostMapping
    public ResponseEntity<Incident> createIncident(@RequestBody Incident incident) {
        Incident savedIncident = incidentService.createIncident(
                incident.getTitle(), incident.getDescription(), incident.getStatus(), incident.getPriority()
        );
        return new ResponseEntity<>(savedIncident, HttpStatus.CREATED);
    }

    // Get an Incident by ID
    @GetMapping("/{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable Long id) {
        Incident incident = incidentService.getIncidentById(id);
        if (incident != null) {
            return new ResponseEntity<>(incident, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all Incidents
    @GetMapping
    public ResponseEntity<List<Incident>> getAllIncidents() {
        List<Incident> incidents = incidentService.getAllIncidents();
        return new ResponseEntity<>(incidents, HttpStatus.OK);
    }

    // Update an Incident by ID
    @PutMapping("/{id}")
    public ResponseEntity<Incident> updateIncident(@PathVariable Long id, @RequestBody Incident updatedIncident) {
        Incident incident = incidentService.updateIncident(id, updatedIncident);
        if (incident != null) {
            return new ResponseEntity<>(incident, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an Incident by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        boolean deleted = incidentService.deleteIncident(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}