import React, { useState, useEffect } from 'react';
import {
  getUnsoldVehicleCount,
  getVehicleDistributionByDecade,
  getVehicleDistributionByBrand,
  getRecentlyRegisteredVehicles,
} from '../services/api';
import type { Vehicle } from '../types';

const Statistics: React.FC = () => {
    const [unsoldCount, setUnsoldCount] = useState(0);
    const [decadeDistribution, setDecadeDistribution] = useState<Record<string, number>>({});
    const [brandDistribution, setBrandDistribution] = useState<Record<string, number>>({});
    const [recentVehicles, setRecentVehicles] = useState<Vehicle[]>([]);

    useEffect(() => {
        const fetchStatistics = async () => {
            try {
                const [
                    unsoldCountRes,
                    decadeDistRes,
                    brandDistRes,
                    recentVehiclesRes,
                ] = await Promise.all([
                    getUnsoldVehicleCount(),
                    getVehicleDistributionByDecade(),
                    getVehicleDistributionByBrand(),
                    getRecentlyRegisteredVehicles(),
                ]);

                setUnsoldCount(unsoldCountRes.data.unsoldCount);
                setDecadeDistribution(decadeDistRes.data);
                setBrandDistribution(brandDistRes.data);
                setRecentVehicles(recentVehiclesRes.data);

            } catch (error) {
                console.error("Error fetching statistics:", error);
            }
        };

        fetchStatistics();
    }, []);

    return (
        <div className="card">
            <div className="card-body">
                <h5 className="card-title">Estatísticas</h5>
                
                <p><strong>Total de veículos não vendidos:</strong> {unsoldCount}</p>

                <h6>Veículos por Década</h6>
                <ul className="list-group mb-3">
                    {Object.entries(decadeDistribution).map(([decade, count]) => (
                        <li key={decade} className="list-group-item d-flex justify-content-between align-items-center">
                            Década de {decade}
                            <span className="badge bg-primary rounded-pill">{count}</span>
                        </li>
                    ))}
                </ul>

                <h6>Veículos por Marca</h6>
                <ul className="list-group mb-3">
                    {Object.entries(brandDistribution).map(([brand, count]) => (
                        <li key={brand} className="list-group-item d-flex justify-content-between align-items-center">
                            {brand}
                            <span className="badge bg-primary rounded-pill">{count}</span>
                        </li>
                    ))}
                </ul>

                <h6>Veículos Adicionados Recentemente</h6>
                <ul className="list-group">
                    {recentVehicles.map(vehicle => (
                        <li key={vehicle.id} className="list-group-item">
                            {vehicle.brand} {vehicle.model} ({vehicle.year})
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default Statistics;
