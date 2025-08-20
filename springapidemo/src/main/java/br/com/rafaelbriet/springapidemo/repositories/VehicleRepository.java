package br.com.rafaelbriet.springapidemo.repositories;

import br.com.rafaelbriet.springapidemo.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
