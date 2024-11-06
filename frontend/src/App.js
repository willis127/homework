// App.js
import React, { useState } from 'react';
import IncidentList from './components/IncidentList';
import IncidentForm from './components/IncidentForm';
import './App.css';

const App = () => {
    const [refresh, setRefresh] = useState(false);
    const [currentIncident, setCurrentIncident] = useState(null);

    // Define toggleRefresh as an actual function to pass down
    const toggleRefresh = () => setRefresh(!refresh);

    const handleEdit = (incident) => {
        setCurrentIncident(incident);
    };

    const handleSuccess = () => {
        setCurrentIncident(null);
        toggleRefresh(); // This will trigger a refresh of the incident list
    };

    return (
        <div className="app">
            <h1>Incident Management</h1>
            <IncidentForm incident={currentIncident} onSuccess={handleSuccess} />
            {/* Pass toggleRefresh as onRefresh to IncidentList */}
            <IncidentList onEdit={handleEdit} onRefresh={toggleRefresh} />
        </div>
    );
};

export default App;