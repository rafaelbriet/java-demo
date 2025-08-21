package br.com.rafaelbriet.springapidemo.mappers;

import br.com.rafaelbriet.springapidemo.dtos.VehicleRequestDTO;
import br.com.rafaelbriet.springapidemo.dtos.VehicleResponseDTO;
import br.com.rafaelbriet.springapidemo.entities.Vehicle;
import org.springframework.stereotype.Component;

@Component // Or define methods as static if you don't want to inject it
public class VehicleMapper {

    public Vehicle toEntity(VehicleRequestDTO dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setModel(dto.getModel());
        vehicle.setBrand(dto.getBrand());
        vehicle.setYear(dto.getYear());
        vehicle.setDescription(dto.getDescription());
        vehicle.setSold(dto.isSold());
        return vehicle;
    }

    public VehicleResponseDTO toResponseDTO(Vehicle entity) {
        VehicleResponseDTO dto = new VehicleResponseDTO();
        dto.setId(entity.getId());
        dto.setModel(entity.getModel());
        dto.setBrand(entity.getBrand());
        dto.setYear(entity.getYear());
        dto.setDescription(entity.getDescription());
        dto.setSold(entity.isSold());
        dto.setCreated(entity.getCreated());
        dto.setUpdated(entity.getUpdated());
        return dto;
    }
}
