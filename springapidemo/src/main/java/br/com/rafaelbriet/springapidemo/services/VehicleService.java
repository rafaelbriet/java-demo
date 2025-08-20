package br.com.rafaelbriet.springapidemo.services;

import br.com.rafaelbriet.springapidemo.entities.Vehicle;
import br.com.rafaelbriet.springapidemo.repositories.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Vehicle> findAll(String brand, String model, Integer year) {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Vehicle> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Vehicle create(Vehicle newVehicle) {
        return repository.save(newVehicle);
    }

    @Transactional
    public Optional<Vehicle> update(Long id, Vehicle updatedVehicle) {
        return repository.findById(id)
                .map(existingVehicle -> {
                    existingVehicle.setModel(updatedVehicle.getModel());
                    existingVehicle.setBrand(updatedVehicle.getBrand());
                    existingVehicle.setYear(updatedVehicle.getYear());
                    existingVehicle.setDescription(updatedVehicle.getDescription());
                    existingVehicle.setSold(updatedVehicle.isSold());
                    return repository.save(existingVehicle);
                });
    }

    @Transactional
    public Optional<Vehicle> patch(Long id, Map<String, Object> updates) {
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
                    return repository.save(existingVehicle);
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
}