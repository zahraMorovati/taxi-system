package model;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String Plaque;
    private String ownerFirstName;
    private String ownerLAstName;
    @Enumerated(EnumType.STRING)
    private VehicleColor vehicleColor;

}
