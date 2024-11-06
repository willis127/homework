import React, { useEffect, useState } from 'react';
import { getIncidents, deleteIncident } from '../api';

const IncidentList = () => {
    const [incidents, setIncidents] = useState([]);

    useEffect(() => {
        fetchIncidents();
    }, []);

    const fetchIncidents = async () => {
        const data = await getIncidents();
        setIncidents(data);
    };

    const handleDelete = async (id) => {
        await deleteIncident(id);
        fetchIncidents();
    };

    return (
        <div>
            <h2>Incident List</h2>
            <ul>
                {incidents.map((incident) => (
                    <li key={incident.id}>
                        {incident.title} - {incident.description}
                        <button onClick={() => handleDelete(incident.id)}>Delete</button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default IncidentList;