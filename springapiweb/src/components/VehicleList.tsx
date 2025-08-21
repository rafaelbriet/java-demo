import React from 'react';
import type { Vehicle } from '../types';
import VehicleCard from './VehicleCard';

interface VehicleListProps {
  vehicles: Vehicle[];
  onEdit: (vehicle: Vehicle) => void;
  onDelete: (id: number) => void;
}

const VehicleList: React.FC<VehicleListProps> = ({ vehicles, onEdit, onDelete }) => {
  return (
    <div className="row">
      {vehicles.length === 0 ? (
        <p>Nenhum veículo encontrado.</p>
      ) : (
        vehicles.map(vehicle => (
          <div key={vehicle.id} className="col-md-6 mb-4">
            <VehicleCard vehicle={vehicle} onEdit={onEdit} onDelete={onDelete} />
          </div>
        ))
      )}
    </div>
  );
};

export default VehicleList;
