package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Driver extends Person {

    @OneToOne
    private Car car;
    private boolean status;
    @Embedded
    private Coordinate currentCoordinate;

    public int getStatus() {
        if (status)
            return 1;
        else return 0;
    }

    public boolean getBooleanStatus(){
        return status;
    }

    @Override
    public String toString() {
        return super.toString() + " , car id: " + car.getId() + "\ncurrent coordinate: " + currentCoordinate.toString() + " ,travel status: " + status;
    }


    public static final class DriverBuilder {
        private Driver driver;

        private DriverBuilder() {
            driver = new Driver();
        }

        public static DriverBuilder aDriver() {
            return new DriverBuilder();
        }

        public DriverBuilder setCar(Car car) {
            driver.setCar(car);
            return this;
        }

        public DriverBuilder setStatus(boolean status) {
            driver.setStatus(status);
            return this;
        }

        public DriverBuilder setCurrentCoordinate(Coordinate currentCoordinate) {
            driver.setCurrentCoordinate(currentCoordinate);
            return this;
        }

        public DriverBuilder setUserID(int userID) {
            driver.setUserID(userID);
            return this;
        }

        public DriverBuilder setFirstName(String firstName) {
            driver.setFirstName(firstName);
            return this;
        }

        public DriverBuilder setLastName(String lastName) {
            driver.setLastName(lastName);
            return this;
        }

        public DriverBuilder setNationalCode(int nationalCode) {
            driver.setNationalCode(nationalCode);
            return this;
        }

        public DriverBuilder setPhoneNumber(String phoneNumber) {
            driver.setPhoneNumber(phoneNumber);
            return this;
        }

        public DriverBuilder setBirthDate(Date birthDate) {
            driver.setBirthDate(birthDate);
            return this;
        }

        public Driver build() {
            return driver;
        }
    }
}
