import React, { useState } from 'react';

interface FiltersProps {
  onFilterChange: (filters: { brand?: string; year?: number; color?: string }) => void;
}

const Filters: React.FC<FiltersProps> = ({ onFilterChange }) => {
  const [brand, setBrand] = useState<string>('');
  const [year, setYear] = useState<string>('');
  const [color, setColor] = useState<string>('');

  const handleApplyFilters = () => {
    onFilterChange({
      brand: brand || undefined,
      year: year ? parseInt(year) : undefined,
      color: color || undefined,
    });
  };

  const handleClearFilters = () => {
    setBrand('');
    setYear('');
    setColor('');
    onFilterChange({});
  };

  return (
    <div className="card mb-4">
      <div className="card-body">
        <h5 className="card-title">Filtros</h5>
        <div className="row g-3">
          <div className="col-md-4">
            <label htmlFor="brand" className="form-label">Marca</label>
            <input
              type="text"
              className="form-control"
              id="brand"
              value={brand}
              onChange={(e) => setBrand(e.target.value)}
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="year" className="form-label">Ano</label>
            <input
              type="number"
              className="form-control"
              id="year"
              value={year}
              onChange={(e) => setYear(e.target.value)}
            />
          </div>
          <div className="col-md-4">
            <label htmlFor="color" className="form-label">Cor</label>
            <input
              type="text"
              className="form-control"
              id="color"
              value={color}
              onChange={(e) => setColor(e.target.value)}
            />
          </div>
        </div>
        <div className="mt-3">
          <button className="btn btn-primary me-2" onClick={handleApplyFilters}>Aplicar Filtros</button>
          <button className="btn btn-secondary" onClick={handleClearFilters}>Limpar Filtros</button>
        </div>
      </div>
    </div>
  );
};

export default Filters;
