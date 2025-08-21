package br.com.rafaelbriet.springapidemo.controllers;

import br.com.rafaelbriet.springapidemo.dtos.VehicleRequestDTO;
import br.com.rafaelbriet.springapidemo.dtos.VehicleResponseDTO;
import br.com.rafaelbriet.springapidemo.services.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/veiculos") // Base path for all vehicle-related endpoints
public class VehiclesController {

    private final VehicleService service;

    public VehiclesController(VehicleService service) {
        this.service = service;
    }

    // GET all vehicles or filter by brand, model, year
    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehicles(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer year) {
        List<VehicleResponseDTO> vehicles = service.findAll(brand, model, year);
        return ResponseEntity.ok(vehicles);
    }

    // GET a single vehicle by ID
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE a new vehicle
    @PostMapping
    public ResponseEntity<VehicleResponseDTO> createVehicle(@Validated @RequestBody VehicleRequestDTO newVehicle) {
        VehicleResponseDTO savedVehicle = service.create(newVehicle);
        return new ResponseEntity<>(savedVehicle, HttpStatus.CREATED);
    }

    // UPDATE an existing vehicle
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(@PathVariable Long id, @Validated @RequestBody VehicleRequestDTO updatedVehicle) {
        return service.update(id, updatedVehicle)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PARTIAL UPDATE an existing vehicle
    @PatchMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> patchVehicle(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return service.patch(id, updates)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE a vehicle
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
