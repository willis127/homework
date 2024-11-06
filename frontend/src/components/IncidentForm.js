import React, { useState } from 'react';
import { createIncident, updateIncident } from '../api';

const IncidentForm = ({ incident, onSuccess }) => {
    const [title, setTitle] = useState(incident ? incident.title : '');
    const [description, setDescription] = useState(incident ? incident.description : '');

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (incident) {
            await updateIncident(incident.id, { title, description });
        } else {
            await createIncident({ title, description });
        }
        onSuccess();
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                placeholder="Title"
                required
            />
            <textarea
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                placeholder="Description"
                required
            ></textarea>
            <button type="submit">{incident ? 'Update' : 'Create'} Incident</button>
        </form>
    );
};

export default IncidentForm;