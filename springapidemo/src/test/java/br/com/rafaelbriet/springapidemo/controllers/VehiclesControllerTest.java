package br.com.rafaelbriet.springapidemo.controllers;

import br.com.rafaelbriet.springapidemo.dtos.VehicleRequestDTO;
import br.com.rafaelbriet.springapidemo.entities.Vehicle;
import br.com.rafaelbriet.springapidemo.repositories.VehicleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VehiclesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VehicleRepository vehicleRepository;

    private Vehicle testVehicle;

    @BeforeEach
    void setUp() {
        vehicleRepository.deleteAll();
        testVehicle = new Vehicle();
        testVehicle.setModel("Civic");
        testVehicle.setBrand("Honda");
        testVehicle.setYear(2020);
        testVehicle.setColor("Preto");
        testVehicle.setDescription("A nice car");
        testVehicle.setSold(false);
        vehicleRepository.save(testVehicle);
    }

    @AfterEach
    void tearDown() {
        vehicleRepository.deleteAll();
    }

    @Test
    void createVehicle_shouldReturnCreatedVehicle() throws Exception {
        VehicleRequestDTO newVehicle = new VehicleRequestDTO();
        newVehicle.setModel("Corolla");
        newVehicle.setBrand("Toyota");
        newVehicle.setYear(2023);
        newVehicle.setColor("Branco");
        newVehicle.setDescription("A reliable car");
        newVehicle.setSold(false);

        mockMvc.perform(post("/veiculos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newVehicle)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.model").value("Corolla"))
                .andExpect(jsonPath("$.brand").value("Toyota"));
    }

    @Test
    void createVehicle_shouldReturnBadRequest_whenInvalidData() throws Exception {
        VehicleRequestDTO invalidVehicle = new VehicleRequestDTO();
        invalidVehicle.setBrand("Ford");
        invalidVehicle.setYear(2022);

        mockMvc.perform(post("/veiculos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidVehicle)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllVehicles_shouldReturnAllVehicles() throws Exception {
        mockMvc.perform(get("/veiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].model").value("Civic"));
    }

    @Test
    void getAllVehicles_shouldFilterByBrand() throws Exception {
        Vehicle anotherVehicle = new Vehicle();
        anotherVehicle.setModel("Fusion");
        anotherVehicle.setBrand("Ford");
        anotherVehicle.setYear(2018);
        anotherVehicle.setSold(false);
        vehicleRepository.save(anotherVehicle);

        mockMvc.perform(get("/veiculos?marca=Ford"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].brand").value("Ford"));
    }

    @Test
    void getVehicleById_shouldReturnVehicle() throws Exception {
        mockMvc.perform(get("/veiculos/{id}", testVehicle.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testVehicle.getId()))
                .andExpect(jsonPath("$.model").value("Civic"));
    }

    @Test
    void getVehicleById_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/veiculos/{id}", 999L)) // Non-existent ID
                .andExpect(status().isNotFound());
    }

    @Test
    void updateVehicle_shouldReturnUpdatedVehicle() throws Exception {
        VehicleRequestDTO updatedVehicle = new VehicleRequestDTO();
        updatedVehicle.setModel("Civic Updated");
        updatedVehicle.setBrand("Honda");
        updatedVehicle.setYear(2021);
        updatedVehicle.setColor("Vermelho");
        updatedVehicle.setDescription("Updated description");
        updatedVehicle.setSold(true);

        mockMvc.perform(put("/veiculos/{id}", testVehicle.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedVehicle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("Civic Updated"))
                .andExpect(jsonPath("$.sold").value(true));
    }

    @Test
    void updateVehicle_shouldReturnNotFound_whenNonExistentId() throws Exception {
        VehicleRequestDTO updatedVehicle = new VehicleRequestDTO();
        updatedVehicle.setModel("Non Existent");
        updatedVehicle.setBrand("Ford");
        updatedVehicle.setYear(2000);
        updatedVehicle.setSold(false);
        updatedVehicle.setColor("Preto");

        mockMvc.perform(put("/veiculos/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedVehicle)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteVehicle_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/veiculos/{id}", testVehicle.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/veiculos/{id}", testVehicle.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteVehicle_shouldReturnNotFound_whenNonExistentId() throws Exception {
        mockMvc.perform(delete("/veiculos/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnsoldVehicleCount_shouldReturnCount() throws Exception {
        mockMvc.perform(get("/veiculos/total-nao-vendidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.unsoldCount").value(1));
    }

    @Test
    void getVehicleDistributionByDecade_shouldReturnDistribution() throws Exception {
        mockMvc.perform(get("/veiculos/total-por-decada"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['Decade 2020']").value(1));
    }

    @Test
    void getVehicleDistributionByBrand_shouldReturnDistribution() throws Exception {
        mockMvc.perform(get("/veiculos/total-por-marca"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Honda").value(1));
    }

    @Test
    void getRecentlyRegisteredVehicles_shouldReturnRecentVehicles() throws Exception {
        mockMvc.perform(get("/veiculos/recentes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].model").value("Civic"));
    }
}
