package model;

import myDate.MyDate;

public class Travel {
    private int id;
    private int driverID;
    private int passengerID;
    private MyDate startDate;
    private MyDate endDate;
    private String origin;
    private String destination;
    private double price;


    public Travel(int driverId, int passengerID, MyDate startDate, MyDate endDate, String origin, String destination, double price) {
        this.driverID = driverId;
        this.passengerID = passengerID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.origin = origin;
        this.destination = destination;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public int getPassengerID() {
        return passengerID;
    }

    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }

    public MyDate getStartDate() {
        return startDate;
    }

    public void setStartDate(MyDate startDate) {
        this.startDate = startDate;
    }

    public MyDate getEndDate() {
        return endDate;
    }

    public void setEndDate(MyDate endDate) {
        this.endDate = endDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "" +
                "driverID=" + driverID +
                ", passengerID=" + passengerID +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", price=" + price;
    }
}
