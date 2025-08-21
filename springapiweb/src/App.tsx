import { useState, useEffect } from 'react';
import type { Vehicle, VehicleRequest } from './types'; // Import VehicleRequest
import { getVehicles, createVehicle, updateVehicle, deleteVehicle } from './services/api'; // Import CRUD functions
import Filters from './components/Filters';
import VehicleList from './components/VehicleList';
import Statistics from './components/Statistics';
import VehicleForm from './components/VehicleForm'; // Import VehicleForm

function App() {
  const [vehicles, setVehicles] = useState<Vehicle[]>([]);
  const [filters, setFilters] = useState<{ marca?: string; ano?: number; cor?: string }>({});
  const [showForm, setShowForm] = useState(false); // State to control form modal visibility
  const [editingVehicle, setEditingVehicle] = useState<Vehicle | undefined>(undefined); // State to hold vehicle being edited

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

  const handleCreateClick = () => {
    setEditingVehicle(undefined); // Clear any previous editing state
    setShowForm(true);
  };

  const handleEditClick = (vehicle: Vehicle) => {
    setEditingVehicle(vehicle);
    setShowForm(true);
  };

  const handleDeleteClick = async (id: number) => {
    if (window.confirm('Tem certeza que deseja deletar este veículo?')) {
      try {
        await deleteVehicle(id);
        fetchVehicles(); // Refresh list after deletion
      } catch (error) {
        console.error('Error deleting vehicle:', error);
      }
    }
  };

  const handleSaveVehicle = async (vehicleData: VehicleRequest) => {
    try {
      if (editingVehicle) {
        await updateVehicle(editingVehicle.id, vehicleData);
      } else {
        await createVehicle(vehicleData);
      }
      setShowForm(false); // Close modal
      fetchVehicles(); // Refresh list
    } catch (error) {
      console.error('Error saving vehicle:', error);
    }
  };

  const handleCancelForm = () => {
    setShowForm(false); // Close modal
    setEditingVehicle(undefined); // Clear editing state
  };

  return (
    <div className="container mt-4">
      <h1 className="mb-4">Gerenciamento de Veículos</h1>
      
      <div className="mb-3">
        <button className="btn btn-success" onClick={handleCreateClick}>
          Criar Novo Veículo
        </button>
      </div>

      <div className="row">
        <div className="col-md-8">
          <Filters onFilterChange={handleFilterChange} currentFilters={filters} />
          <VehicleList vehicles={vehicles} onEdit={handleEditClick} onDelete={handleDeleteClick} /> {/* Pass edit/delete handlers */}
        </div>
        <div className="col-md-4">
          <Statistics />
        </div>
      </div>

      {/* Vehicle Form Modal */}
      {showForm && (
        <div className="modal show d-block" tabIndex={-1} style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}>
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">{editingVehicle ? 'Editar Veículo' : 'Criar Novo Veículo'}</h5>
                <button type="button" className="btn-close" onClick={handleCancelForm}></button>
              </div>
              <div className="modal-body">
                <VehicleForm
                  vehicle={editingVehicle}
                  onSave={handleSaveVehicle}
                  onCancel={handleCancelForm}
                />
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;