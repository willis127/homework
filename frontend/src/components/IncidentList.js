// IncidentList.js
import React, { useEffect, useState } from 'react';
import { getIncidents, deleteIncident } from '../api';
import './IncidentList.css';

const IncidentList = ({ onEdit, onRefresh }) => {
    const [incidents, setIncidents] = useState([]);

    const fetchIncidents = async () => {
        const data = await getIncidents();
        setIncidents(data);
    };

    useEffect(() => {
        fetchIncidents();
    }, [onRefresh]);

    const handleDelete = async (id) => {
        await deleteIncident(id);
        onRefresh();
    };

    const formatDateTime = (dateTime) => {
        return new Date(dateTime).toLocaleString();
    };

    return (
        <div className="incident-list">
            <h2>Incident List</h2>
            <ul>
                {incidents.map((incident) => (
                    <li key={incident.id}>
                        <div>
                            <strong>Title:</strong> {incident.title} <br />
                            <strong>Description:</strong> {incident.description} <br />
                            <strong>Status:</strong> {incident.status} <br />
                            <strong>Priority:</strong> {incident.priority} <br />
                            <strong>Created At:</strong> {formatDateTime(incident.createdAt)} <br />
                            <strong>Updated At:</strong> {formatDateTime(incident.updatedAt)}
                        </div>
                        <div className="incident-actions">
                            <button onClick={() => onEdit(incident)}>Edit</button>
                            <button onClick={() => handleDelete(incident.id)}>Delete</button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default IncidentList;