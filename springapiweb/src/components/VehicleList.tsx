import React from 'react';
import type { Vehicle } from '../types';
import VehicleCard from './VehicleCard';

interface VehicleListProps {
  vehicles: Vehicle[];
}

const VehicleList: React.FC<VehicleListProps> = ({ vehicles }) => {
  return (
    <div className="row">
      {vehicles.length === 0 ? (
        <p>Nenhum veículo encontrado.</p>
      ) : (
        vehicles.map(vehicle => (
          <div key={vehicle.id} className="col-md-6 mb-4">
            <VehicleCard vehicle={vehicle} />
          </div>
        ))
      )}
    </div>
  );
};

export default VehicleList;
