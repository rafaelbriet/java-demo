import axios from 'axios';
import type { Vehicle, VehicleRequest } from '../types'; // Added VehicleRequest

const api = axios.create({
  baseURL: '/veiculos', // The proxy will handle this
});

export const getVehicles = (params: { marca?: string; ano?: number; cor?: string }) => {
  return api.get<Vehicle[]>('', { params });
};

export const getUnsoldVehicleCount = () => {
  return api.get<{ unsoldCount: number }>('/total-nao-vendidos');
};

export const getVehicleDistributionByDecade = () => {
  return api.get<Record<string, number>>('/total-por-decada');
};

export const getVehicleDistributionByBrand = () => {
  return api.get<Record<string, number>>('/total-por-marca');
};

export const getRecentlyRegisteredVehicles = () => {
  return api.get<Vehicle[]>('/recentes');
};

export const createVehicle = (vehicleData: VehicleRequest) => {
  return api.post<Vehicle>('', vehicleData);
};

export const updateVehicle = (id: number, vehicleData: VehicleRequest) => {
  return api.put<Vehicle>(`/${id}`, vehicleData);
};

export const deleteVehicle = (id: number) => {
  return api.delete<void>(`/${id}`);
};