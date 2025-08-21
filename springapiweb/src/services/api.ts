import axios from 'axios';
import type { Vehicle } from '../types';

const api = axios.create({
  baseURL: '/veiculos', // The proxy will handle this
});

export const getVehicles = (params: { brand?: string; year?: number; color?: string }) => {
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
