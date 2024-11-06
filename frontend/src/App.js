import React, { useState } from 'react';
import IncidentList from './components/IncidentList';
import IncidentForm from './components/IncidentForm';

const App = () => {
  const [refresh, setRefresh] = useState(false);

  const toggleRefresh = () => setRefresh(!refresh);

  return (
      <div>
        <h1>Incident Management</h1>
        <IncidentForm onSuccess={toggleRefresh} />
        <IncidentList key={refresh} />
      </div>
  );
};

export default App;