import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

export const getIncidents = async () => {
    const response = await axios.get(`${API_URL}/incidents`);
    return response.data;
};

export const createIncident = async (incident) => {
    const response = await axios.post(`${API_URL}/incidents`, incident);
    return response.data;
};

export const updateIncident = async (id, updatedIncident) => {
    const response = await axios.put(`${API_URL}/incidents/${id}`, updatedIncident);
    return response.data;
};

export const deleteIncident = async (id) => {
    const response = await axios.delete(`${API_URL}/incidents/${id}`);
    return response.data;
};