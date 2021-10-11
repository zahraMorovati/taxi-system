package model;

import model.VehicleColor;

public class Vehicle {

    private int id;
    private String Plaque;
    private String ownerFirstName;
    private String ownerLAstName;
    private VehicleColor vehicleColor;

    public Vehicle(int id, String plaque, String ownerFirstName, String ownerLAstName, VehicleColor vehicleColor) {
        this.id = id;
        Plaque = plaque;
        this.ownerFirstName = ownerFirstName;
        this.ownerLAstName = ownerLAstName;
        this.vehicleColor = vehicleColor;
    }

    public String getPlaque() {
        return Plaque;
    }

    public void setPlaque(String plaque) {
        Plaque = plaque;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLAstName() {
        return ownerLAstName;
    }

    public void setOwnerLAstName(String ownerLAstName) {
        this.ownerLAstName = ownerLAstName;
    }

    public VehicleColor getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(VehicleColor vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
