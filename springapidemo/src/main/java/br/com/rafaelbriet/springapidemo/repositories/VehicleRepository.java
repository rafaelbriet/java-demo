package br.com.rafaelbriet.springapidemo.repositories;

import br.com.rafaelbriet.springapidemo.dtos.DecadeCount;
import br.com.rafaelbriet.springapidemo.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {

    long countBySold(boolean sold);

    @Query("SELECT new br.com.rafaelbriet.springapidemo.dtos.DecadeCount(floor(v.year / 10) * 10, COUNT(v)) FROM Vehicle v GROUP BY floor(v.year / 10) * 10 ORDER BY floor(v.year / 10) * 10")
    List<DecadeCount> findVehicleCountByDecade();
}
