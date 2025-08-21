import React, { useState, useEffect } from 'react';
import type { Vehicle, VehicleRequest } from '../types';

interface VehicleFormProps {
  vehicle?: Vehicle; // Optional, for editing existing vehicle
  onSave: (vehicle: VehicleRequest) => void;
  onCancel: () => void;
}

const VehicleForm: React.FC<VehicleFormProps> = ({ vehicle, onSave, onCancel }) => {
  const [model, setModel] = useState(vehicle?.model || '');
  const [brand, setBrand] = useState(vehicle?.brand || '');
  const [year, setYear] = useState<number | string>(vehicle?.year || '');
  const [color, setColor] = useState(vehicle?.color || '');
  const [description, setDescription] = useState(vehicle?.description || '');
  const [sold, setSold] = useState(vehicle?.sold || false);

  useEffect(() => {
    if (vehicle) {
      setModel(vehicle.model);
      setBrand(vehicle.brand);
      setYear(vehicle.year);
      setColor(vehicle.color);
      setDescription(vehicle.description);
      setSold(vehicle.sold);
    }
  }, [vehicle]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!model || !brand || !year) {
      alert('Model, Brand, and Year are required.');
      return;
    }

    onSave({
      model,
      brand,
      year: Number(year),
      color: color || undefined,
      description: description || undefined,
      sold,
    });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="mb-3">
        <label htmlFor="model" className="form-label">Modelo</label>
        <input type="text" className="form-control" id="model" value={model} onChange={(e) => setModel(e.target.value)} required />
      </div>
      <div className="mb-3">
        <label htmlFor="brand" className="form-label">Marca</label>
        <input type="text" className="form-control" id="brand" value={brand} onChange={(e) => setBrand(e.target.value)} required />
      </div>
      <div className="mb-3">
        <label htmlFor="year" className="form-label">Ano</label>
        <input type="number" className="form-control" id="year" value={year} onChange={(e) => setYear(e.target.value)} required />
      </div>
      <div className="mb-3">
        <label htmlFor="color" className="form-label">Cor</label>
        <input type="text" className="form-control" id="color" value={color} onChange={(e) => setColor(e.target.value)} />
      </div>
      <div className="mb-3">
        <label htmlFor="description" className="form-label">Descrição</label>
        <textarea className="form-control" id="description" value={description} onChange={(e) => setDescription(e.target.value)} />
      </div>
      <div className="mb-3 form-check">
        <input type="checkbox" className="form-check-input" id="sold" checked={sold} onChange={(e) => setSold(e.target.checked)} />
        <label className="form-check-label" htmlFor="sold">Vendido</label>
      </div>
      <button type="submit" className="btn btn-primary me-2">Salvar</button>
      <button type="button" className="btn btn-secondary" onClick={onCancel}>Cancelar</button>
    </form>
  );
};

export default VehicleForm;
