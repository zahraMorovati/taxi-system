package model;

import myDate.MyDate;

public class Driver extends Person{

    private int carID;


    public Driver(String firstName, String lastName, int nationalCode, String phoneNumber, MyDate birthDate, int carId) {
        super(firstName, lastName, nationalCode, phoneNumber, birthDate);
        this.carID = carId;
    }

    public Driver(int userID, String firstName, String lastName, int nationalCode, String phoneNumber, MyDate birthDate, int carID) {
        super(userID,firstName, lastName, nationalCode, phoneNumber, birthDate);
        this.carID=carID;
    }
    

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    @Override
    public String toString() {
        return super.toString()+" , car id: "+carID ;
    }
}
