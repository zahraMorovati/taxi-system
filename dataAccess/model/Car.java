package model;

public class Car extends Vehicle {

    private CarType carType;

    public Car(int id, String plaque, String ownerFirstName, String ownerLAstName, VehicleColor vehicleColor, CarType carType) {
        super(id, plaque, ownerFirstName, ownerLAstName, vehicleColor);
        this.carType = carType;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }


}
