package br.com.rafaelbriet.springapidemo.services;

import br.com.rafaelbriet.springapidemo.dtos.BrandCount;
import br.com.rafaelbriet.springapidemo.dtos.DecadeCount;
import br.com.rafaelbriet.springapidemo.dtos.VehicleRequestDTO;
import br.com.rafaelbriet.springapidemo.dtos.VehicleResponseDTO;
import br.com.rafaelbriet.springapidemo.entities.Brand;
import br.com.rafaelbriet.springapidemo.entities.Vehicle;
import br.com.rafaelbriet.springapidemo.exceptions.InvalidBrandException;
import br.com.rafaelbriet.springapidemo.mappers.VehicleMapper;
import br.com.rafaelbriet.springapidemo.repositories.BrandRepository;
import br.com.rafaelbriet.springapidemo.repositories.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap; // For preserving order
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final BrandRepository brandRepository;
    private final VehicleMapper mapper;

    public VehicleService(VehicleRepository vehicleRepository, BrandRepository brandRepository, VehicleMapper mapper) {
        this.vehicleRepository = vehicleRepository;
        this.brandRepository = brandRepository;
        this.mapper = mapper;
    }

    private String validateAndGetOfficialBrandName(String brandName) {
        return brandRepository.findByNameIgnoreCase(brandName)
                .map(Brand::getName)
                .orElseThrow(() -> new InvalidBrandException("Invalid brand: '" + brandName + "'. Please use a valid brand."));
    }

    @Transactional(readOnly = true)
    public List<VehicleResponseDTO> findAll(String brand, String model, Integer year) {
        // A lógica de filtro avançado será adicionada aqui posteriormente.
        return vehicleRepository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<VehicleResponseDTO> findById(Long id) {
        return vehicleRepository.findById(id).map(mapper::toResponseDTO);
    }

    @Transactional
    public VehicleResponseDTO create(VehicleRequestDTO requestDTO) {
        String officialBrandName = validateAndGetOfficialBrandName(requestDTO.getBrand());
        requestDTO.setBrand(officialBrandName); // Set the official name before mapping

        Vehicle vehicle = mapper.toEntity(requestDTO);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return mapper.toResponseDTO(savedVehicle);
    }

    @Transactional
    public Optional<VehicleResponseDTO> update(Long id, VehicleRequestDTO requestDTO) {
        String officialBrandName = validateAndGetOfficialBrandName(requestDTO.getBrand());
        requestDTO.setBrand(officialBrandName); // Set the official name before updating

        return vehicleRepository.findById(id)
                .map(existingVehicle -> {
                    existingVehicle.setModel(requestDTO.getModel());
                    existingVehicle.setBrand(requestDTO.getBrand());
                    existingVehicle.setYear(requestDTO.getYear());
                    existingVehicle.setDescription(requestDTO.getDescription());
                    existingVehicle.setSold(requestDTO.isSold());
                    Vehicle updatedVehicle = vehicleRepository.save(existingVehicle);
                    return mapper.toResponseDTO(updatedVehicle);
                });
    }

    @Transactional
    public Optional<VehicleResponseDTO> patch(Long id, Map<String, Object> updates) {
        return vehicleRepository.findById(id)
                .map(existingVehicle -> {
                    updates.forEach((key, value) -> {
                        switch (key) {
                            case "model":
                                existingVehicle.setModel((String) value);
                                break;
                            case "brand":
                                String officialBrandName = validateAndGetOfficialBrandName((String) value);
                                existingVehicle.setBrand(officialBrandName);
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
                    Vehicle patchedVehicle = vehicleRepository.save(existingVehicle);
                    return mapper.toResponseDTO(patchedVehicle);
                });
    }

    @Transactional
    public boolean delete(Long id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public long countBySoldStatus(boolean sold) {
        return vehicleRepository.countBySold(sold);
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getVehicleCountByDecade() {
        List<DecadeCount> counts = vehicleRepository.findVehicleCountByDecade();
        // Using LinkedHashMap to preserve the order returned by the query
        return counts.stream()
                .collect(Collectors.toMap(
                        decadeCount -> "Decade " + decadeCount.getDecade(),
                        DecadeCount::getCount,
                        (v1, v2) -> v1, // Merge function, not really needed here
                        LinkedHashMap::new
                ));
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getVehicleCountByBrand() {
        List<BrandCount> counts = vehicleRepository.findVehicleCountByBrand();
        return counts.stream()
                .collect(Collectors.toMap(
                        BrandCount::getBrand,
                        BrandCount::getCount,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    @Transactional(readOnly = true)
    public List<VehicleResponseDTO> findVehiclesRegisteredLastWeek() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        List<Vehicle> vehicles = vehicleRepository.findByCreatedAfter(oneWeekAgo);
        return vehicles.stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}