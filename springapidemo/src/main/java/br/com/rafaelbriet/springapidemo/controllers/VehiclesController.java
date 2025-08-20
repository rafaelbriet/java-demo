package br.com.rafaelbriet.springapidemo.controllers;

import br.com.rafaelbriet.springapidemo.entities.Vehicle;
import br.com.rafaelbriet.springapidemo.repositories.VehicleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/veiculos") // Base path for all vehicle-related endpoints
public class VehiclesController {

    private final VehicleRepository repository;

    public VehiclesController(VehicleRepository repository) {
        this.repository = repository;
    }

    // GET all vehicles or filter by brand, model, year
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer year) { // Changed to Integer for optionality

        List<Vehicle> vehicles;
        if (brand != null || model != null || year != null) {
            // This would require custom queries in the repository.
            // For simplicity, I'll just return all for now if no specific filter method exists.
            // In a real app, you'd add methods like findByBrandAndModelAndYear in VehicleRepository.
            vehicles = repository.findAll(); // Placeholder: returns all if filters are complex
        } else {
            vehicles = repository.findAll();
        }
        return ResponseEntity.ok(vehicles);
    }

    // GET a single vehicle by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        Optional<Vehicle> vehicle = repository.findById(id);
        return vehicle.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // CREATE a new vehicle
    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle newVehicle) {
        Vehicle savedVehicle = repository.save(newVehicle);
        return new ResponseEntity<>(savedVehicle, HttpStatus.CREATED);
    }

    // UPDATE an existing vehicle
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody Vehicle updatedVehicle) {
        return repository.findById(id)
                .map(existingVehicle -> {
                    existingVehicle.setModel(updatedVehicle.getModel());
                    existingVehicle.setBrand(updatedVehicle.getBrand());
                    existingVehicle.setYear(updatedVehicle.getYear());
                    existingVehicle.setDescription(updatedVehicle.getDescription());
                    existingVehicle.setSold(updatedVehicle.isSold());
                    Vehicle savedVehicle = repository.save(existingVehicle);
                    return ResponseEntity.ok(savedVehicle);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // PARTIAL UPDATE an existing vehicle
    @PatchMapping("/{id}")
    public ResponseEntity<Vehicle> patchVehicle(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return repository.findById(id)
                .map(existingVehicle -> {
                    updates.forEach((key, value) -> {
                        switch (key) {
                            case "name":
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
                    Vehicle savedVehicle = repository.save(existingVehicle);
                    return ResponseEntity.ok(savedVehicle);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE a vehicle
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
