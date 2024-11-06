# backend
1. These are the source codes for backend springboot project
2. The datas are storaged by a Concurrent Hash Map in class InMemoryIncidentRespository class from package net.willis.homework.repository
3. All unit tests are passed
4. Due to network issue, jar is built and uploaded by me.

## Test the apis directly, runs in bash or zsh
1.	Create indident:
```bash
      curl -X POST http://localhost:8080/api/incidents \
      -H "Content-Type: application/json" \
      -d '{"title": "Sample Incident", "description": "This is a test incident", "status": "OPEN", "priority": "HIGH"}'
```
2.	Get all incidents:
```bash
      curl -X GET http://localhost:8080/api/incidents
```
3.	Get by id 1:
```bash
      curl -X GET http://localhost:8080/api/incidents/1
```
4.	Update by id 1
```bash
      curl -X PUT http://localhost:8080/api/incidents/1 \
      -H "Content-Type: application/json" \
      -d '{"title": "Updated Incident", "description": "Updated description", "status": "IN_PROGRESS", "priority": "MEDIUM"}'
```
5.	Delete by id 1:
```bash
      curl -X DELETE http://localhost:8080/api/incidents/1
```
6.  Load test:
```bash
      ab -n 1000 -c 100 http://localhost:8080/api/incidents
```