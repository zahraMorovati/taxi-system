package model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import myDate.MyDate;

import javax.persistence.Entity;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Passenger extends Person{

    private double balance;
    private boolean status;


    public int getStatus() {
        if(status)
            return 1;
        else return 0;
    }

    @Override
    public String toString() {
        return super.toString()+", balance=" + balance + ", status=" + status ;
    }


    public static final class PassengerBuilder {
        private Passenger passenger;

        private PassengerBuilder() {
            passenger = new Passenger();
        }

        public static PassengerBuilder aPassenger() {
            return new PassengerBuilder();
        }

        public PassengerBuilder setBalance(double balance) {
            passenger.setBalance(balance);
            return this;
        }

        public PassengerBuilder setStatus(boolean status) {
            passenger.setStatus(status);
            return this;
        }

        public PassengerBuilder setUserID(int userID) {
            passenger.setUserID(userID);
            return this;
        }

        public PassengerBuilder setFirstName(String firstName) {
            passenger.setFirstName(firstName);
            return this;
        }

        public PassengerBuilder setLastName(String lastName) {
            passenger.setLastName(lastName);
            return this;
        }

        public PassengerBuilder setNationalCode(int nationalCode) {
            passenger.setNationalCode(nationalCode);
            return this;
        }

        public PassengerBuilder setPhoneNumber(String phoneNumber) {
            passenger.setPhoneNumber(phoneNumber);
            return this;
        }

        public PassengerBuilder setBirthDate(Date birthDate) {
            passenger.setBirthDate(birthDate);
            return this;
        }

        public Passenger build() {
            return passenger;
        }
    }
}
