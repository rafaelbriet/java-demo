import { useState, useEffect } from 'react';
import type { Vehicle } from './types';
import { getVehicles } from './services/api';
import Filters from './components/Filters';
import VehicleList from './components/VehicleList';
import Statistics from './components/Statistics';

function App() {
  const [vehicles, setVehicles] = useState<Vehicle[]>([]);
  const [filters, setFilters] = useState<{ marca?: string; ano?: number; cor?: string }>({});

  // Effect to read filters from URL on initial load
  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const initialFilters: { marca?: string; ano?: number; cor?: string } = {};
    if (params.has('marca')) initialFilters.marca = params.get('marca') || undefined;
    if (params.has('ano')) initialFilters.ano = parseInt(params.get('ano') || '') || undefined;
    if (params.has('cor')) initialFilters.cor = params.get('cor') || undefined;
    setFilters(initialFilters);
  }, []); // Run only once on mount

  // Effect to fetch vehicles when filters change (including initial load from URL)
  useEffect(() => {
    fetchVehicles();
    // Update URL when filters change
    const newParams = new URLSearchParams();
    if (filters.marca) newParams.set('marca', filters.marca);
    if (filters.ano) newParams.set('ano', filters.ano.toString());
    if (filters.cor) newParams.set('cor', filters.cor);

    const queryString = newParams.toString();
    const newUrl = queryString ? `${window.location.pathname}?${queryString}` : window.location.pathname;
    window.history.replaceState({}, '', newUrl);
  }, [filters]);

  const fetchVehicles = async () => {
    try {
      const response = await getVehicles(filters);
      setVehicles(response.data);
    } catch (error) {
      console.error('Error fetching vehicles:', error);
    }
  };

  const handleFilterChange = (newFilters: { marca?: string; ano?: number; cor?: string }) => {
    setFilters(newFilters);
  };

  return (
    <div className="container mt-4">
      <h1 className="mb-4">Gerenciamento de Veículos</h1>
      
      <div className="row">
        <div className="col-md-8">
          <Filters onFilterChange={handleFilterChange} currentFilters={filters} /> {/* Pass currentFilters to Filters */}
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
