import { useState, useEffect } from 'react';
import type { Vehicle } from './types';
import { getVehicles } from './services/api';
import Filters from './components/Filters';
import VehicleList from './components/VehicleList';
import Statistics from './components/Statistics';

function App() {
  const [vehicles, setVehicles] = useState<Vehicle[]>([]);
  const [filters, setFilters] = useState<{ brand?: string; year?: number; color?: string }>({});

  useEffect(() => {
    fetchVehicles();
  }, [filters]);

  const fetchVehicles = async () => {
    try {
      const response = await getVehicles(filters);
      setVehicles(response.data);
    } catch (error) {
      console.error('Error fetching vehicles:', error);
    }
  };

  const handleFilterChange = (newFilters: { brand?: string; year?: number; color?: string }) => {
    setFilters(newFilters);
  };

  return (
    <div className="container mt-4">
      <h1 className="mb-4">Gerenciamento de Veículos</h1>
      
      <div className="row">
        <div className="col-md-8">
          <Filters onFilterChange={handleFilterChange} />
          <VehicleList vehicles={vehicles} />
        </div>
        <div className="col-md-4">
          <Statistics />
        </div>
      </div>
    </div>
  );
}

export default App;