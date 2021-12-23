package model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Car extends Vehicle {

    @Enumerated(EnumType.STRING)
    private CarType carType;

    public static final class CarBuilder {
        private Car car;

        private CarBuilder() {
            car = new Car();
        }

        public static CarBuilder aCar() {
            return new CarBuilder();
        }

        public CarBuilder setCarType(CarType carType) {
            car.setCarType(carType);
            return this;
        }

        public CarBuilder setId(int id) {
            car.setId(id);
            return this;
        }

        public CarBuilder setPlaque(String Plaque) {
            car.setPlaque(Plaque);
            return this;
        }

        public CarBuilder setOwnerFirstName(String ownerFirstName) {
            car.setOwnerFirstName(ownerFirstName);
            return this;
        }

        public CarBuilder setOwnerLAstName(String ownerLAstName) {
            car.setOwnerLAstName(ownerLAstName);
            return this;
        }

        public CarBuilder setVehicleColor(VehicleColor vehicleColor) {
            car.setVehicleColor(vehicleColor);
            return this;
        }

        public CarBuilder but() {
            return aCar().setCarType(car.getCarType()).setId(car.getId()).setPlaque(car.getPlaque()).setOwnerFirstName(car.getOwnerFirstName()).setOwnerLAstName(car.getOwnerLAstName()).setVehicleColor(car.getVehicleColor());
        }

        public Car build() {
            return car;
        }
    }
}
