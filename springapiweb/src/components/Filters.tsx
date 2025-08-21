import React, { useState, useEffect } from 'react';

interface FiltersProps {
  onFilterChange: (filters: { marca?: string; ano?: number; cor?: string }) => void;
  currentFilters: { marca?: string; ano?: number; cor?: string }; // New prop
}

const Filters: React.FC<FiltersProps> = ({ onFilterChange, currentFilters }) => { // Destructure new prop
  const [brand, setBrand] = useState<string>(currentFilters.marca || ''); // Initialize from prop
  const [year, setYear] = useState<string>(currentFilters.ano?.toString() || ''); // Initialize from prop
  const [color, setColor] = useState<string>(currentFilters.cor || ''); // Initialize from prop

  // Effect to update internal state when currentFilters prop changes (e.g., from URL)
  useEffect(() => {
    setBrand(currentFilters.marca || '');
    setYear(currentFilters.ano?.toString() || '');
    setColor(currentFilters.cor || '');
  }, [currentFilters]);

  const handleApplyFilters = () => {
    onFilterChange({
      marca: brand || undefined,
      ano: year ? parseInt(year) : undefined,
      cor: color || undefined,
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