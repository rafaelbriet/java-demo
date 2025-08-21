package br.com.rafaelbriet.springapidemo.dtos;

public class BrandCount {

    private final String brand;
    private final Long count;

    public BrandCount(String brand, Long count) {
        this.brand = brand;
        this.count = count;
    }

    public String getBrand() {
        return brand;
    }

    public Long getCount() {
        return count;
    }
}
