package br.com.rafaelbriet.springapidemo.specifications;

import br.com.rafaelbriet.springapidemo.entities.Vehicle;
import org.springframework.data.jpa.domain.Specification;

public final class VehicleSpecification {

    public static Specification<Vehicle> brandEquals(String brand) {
        return (root, query, criteriaBuilder) -> 
            brand == null ? null : criteriaBuilder.equal(root.get("brand"), brand);
    }

    public static Specification<Vehicle> yearEquals(Integer year) {
        return (root, query, criteriaBuilder) -> 
            year == null ? null : criteriaBuilder.equal(root.get("year"), year);
    }

    public static Specification<Vehicle> colorEquals(String color) {
        return (root, query, criteriaBuilder) -> 
            color == null ? null : criteriaBuilder.equal(root.get("color"), color);
    }
}
