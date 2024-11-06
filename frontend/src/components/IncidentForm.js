// IncidentForm.js
import React, { useState, useEffect } from 'react';
import { createIncident, updateIncident } from '../api';
import './IncidentForm.css';

const IncidentForm = ({ incident, onSuccess }) => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [status, setStatus] = useState('OPEN');
    const [priority, setPriority] = useState('LOW');

    useEffect(() => {
        if (incident) {
            setTitle(incident.title);
            setDescription(incident.description);
            setStatus(incident.status);
            setPriority(incident.priority);
        } else {
            resetForm();
        }
    }, [incident]);

    const resetForm = () => {
        setTitle('');
        setDescription('');
        setStatus('OPEN');
        setPriority('LOW');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const newIncident = { title, description, status, priority };
        if (incident && incident.id) {
            await updateIncident(incident.id, newIncident);
        } else {
            await createIncident(newIncident);
        }
        onSuccess();
        resetForm();
    };

    return (
        <form className="incident-form" onSubmit={handleSubmit}>
            <h2>{incident ? "Edit Incident" : "New Incident"}</h2>
            <input
                type="text"
                placeholder="Title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
            />
            <textarea
                placeholder="Description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                required
            />
            <label>
                Status:
                <select value={status} onChange={(e) => setStatus(e.target.value)}>
                    <option value="OPEN">OPEN</option>
                    <option value="IN_PROGRESS">IN_PROGRESS</option>
                    <option value="CLOSED">CLOSED</option>
                </select>
            </label>
            <label>
                Priority:
                <select value={priority} onChange={(e) => setPriority(e.target.value)}>
                    <option value="LOW">LOW</option>
                    <option value="MEDIUM">MEDIUM</option>
                    <option value="HIGH">HIGH</option>
                </select>
            </label>
            <button type="submit">{incident ? "Update" : "Create"} Incident</button>
        </form>
    );
};

export default IncidentForm;