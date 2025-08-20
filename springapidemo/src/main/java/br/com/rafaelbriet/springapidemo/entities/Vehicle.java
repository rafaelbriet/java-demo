package br.com.rafaelbriet.springapidemo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "veiculos")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "veiculo")
    private String model;

    @Column(name = "marca")
    private String brand;

    @Column(name = "ano")
    private int year;

    @Column(name = "descricao")
    private String description;

    @Column(name = "vendido")
    private boolean sold;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated")
    private LocalDateTime updated;

    // Constructors
    public Vehicle() {
        // Default constructor for JPA
    }

    public Vehicle(Long id, String model, String brand, int year, String description, boolean sold, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.description = description;
        this.sold = sold;
        this.created = created;
        this.updated = updated;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public int getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSold() {
        return sold;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
