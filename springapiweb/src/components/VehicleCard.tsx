import React from 'react';
import type { Vehicle } from '../types';

interface VehicleCardProps {
  vehicle: Vehicle;
}

const VehicleCard: React.FC<VehicleCardProps> = ({ vehicle }) => {
  return (
    <div className="card mb-3">
      <div className="card-body">
        <h5 className="card-title">{vehicle.brand} {vehicle.model} ({vehicle.year})</h5>
        <p className="card-text">{vehicle.description}</p>
        <ul className="list-group list-group-flush">
          <li className="list-group-item">Cor: {vehicle.color}</li>
          <li className="list-group-item">Vendido: {vehicle.sold ? 'Sim' : 'Não'}</li>
        </ul>
      </div>
    </div>
  );
};

export default VehicleCard;
