package model;

import myDate.MyDate;

public class Driver extends Person{

    private int carID;
    private boolean status;
    private Coordinate currentCoordinate;


    public Driver(String firstName, String lastName, int nationalCode, String phoneNumber, MyDate birthDate, int carID, boolean travelStatus, Coordinate currentCoordinate) {
        super(firstName, lastName, nationalCode, phoneNumber, birthDate);
        this.carID = carID;
        this.status = travelStatus;
        this.currentCoordinate = currentCoordinate;
    }

    public Driver(int userID, String firstName, String lastName, int nationalCode, String phoneNumber, MyDate birthDate, int carID, boolean travelStatus, Coordinate currentCoordinate) {
        super(userID, firstName, lastName, nationalCode, phoneNumber, birthDate);
        this.carID = carID;
        this.status = travelStatus;
        this.currentCoordinate = currentCoordinate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Coordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    public void setCurrentCoordinate(Coordinate currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public int getStatus() {
        if(status)
            return 1;
        else return 0;
    }

    @Override
    public String toString() {
        return super.toString()+" , car id: "+carID +"\ncurrent coordinate: "+currentCoordinate.toString()+" ,travel status: "+ status;
    }
}
