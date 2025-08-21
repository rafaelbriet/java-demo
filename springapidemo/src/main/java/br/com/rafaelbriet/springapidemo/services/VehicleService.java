package br.com.rafaelbriet.springapidemo.services;

import br.com.rafaelbriet.springapidemo.dtos.VehicleRequestDTO;
import br.com.rafaelbriet.springapidemo.dtos.VehicleResponseDTO;
import br.com.rafaelbriet.springapidemo.entities.Vehicle;
import br.com.rafaelbriet.springapidemo.mappers.VehicleMapper;
import br.com.rafaelbriet.springapidemo.repositories.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final VehicleRepository repository;
    private final VehicleMapper mapper;

    public VehicleService(VehicleRepository repository, VehicleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<VehicleResponseDTO> findAll(String brand, String model, Integer year) {
        // A lógica de filtro avançado será adicionada aqui posteriormente.
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<VehicleResponseDTO> findById(Long id) {
        return repository.findById(id).map(mapper::toResponseDTO);
    }

    @Transactional
    public VehicleResponseDTO create(VehicleRequestDTO requestDTO) {
        Vehicle vehicle = mapper.toEntity(requestDTO);
        Vehicle savedVehicle = repository.save(vehicle);
        return mapper.toResponseDTO(savedVehicle);
    }

    @Transactional
    public Optional<VehicleResponseDTO> update(Long id, VehicleRequestDTO requestDTO) {
        return repository.findById(id)
                .map(existingVehicle -> {
                    existingVehicle.setModel(requestDTO.getModel());
                    existingVehicle.setBrand(requestDTO.getBrand());
                    existingVehicle.setYear(requestDTO.getYear());
                    existingVehicle.setDescription(requestDTO.getDescription());
                    existingVehicle.setSold(requestDTO.isSold());
                    Vehicle updatedVehicle = repository.save(existingVehicle);
                    return mapper.toResponseDTO(updatedVehicle);
                });
    }

    @Transactional
    public Optional<VehicleResponseDTO> patch(Long id, Map<String, Object> updates) {
        return repository.findById(id)
                .map(existingVehicle -> {
                    updates.forEach((key, value) -> {
                        switch (key) {
                            case "model":
                                existingVehicle.setModel((String) value);
                                break;
                            case "brand":
                                existingVehicle.setBrand((String) value);
                                break;
                            case "year":
                                existingVehicle.setYear(((Number) value).intValue());
                                break;
                            case "description":
                                existingVehicle.setDescription((String) value);
                                break;
                            case "sold":
                                existingVehicle.setSold((Boolean) value);
                                break;
                        }
                    });
                    Vehicle patchedVehicle = repository.save(existingVehicle);
                    return mapper.toResponseDTO(patchedVehicle);
                });
    }

    @Transactional
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public long countBySoldStatus(boolean sold) {
        return repository.countBySold(sold);
    }
}