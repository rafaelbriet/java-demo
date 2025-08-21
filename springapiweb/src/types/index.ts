export interface Vehicle {
  id: number;
  model: string;
  brand: string;
  year: number;
  color: string;
  description: string;
  sold: boolean;
  created: string; // Using string for simplicity, can be parsed to Date if needed
  updated: string;
}

export interface VehicleRequest {
  model: string;
  brand: string;
  year: number;
  color?: string;
  description?: string;
  sold: boolean;
}