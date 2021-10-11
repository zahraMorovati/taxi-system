package model;

import myDate.MyDate;

public class Travel {
    private int id;
    private int driverID;
    private int passengerID;
    private MyDate startDate;
    private MyDate endDate;
    private Coordinate origin;
    private Coordinate destination;
    private double price;
    private TravelStatus travelStatus;

    private final int travelRate=1000;
    public enum TravelStatus{
        ONGOING,WAITING_FOR_PAYMENT,FINISHED;
    }

    public Travel(int driverID, int passengerID, MyDate startDate, MyDate endDate, Coordinate origin, Coordinate destination, TravelStatus travelStatus) {
        this.driverID = driverID;
        this.passengerID = passengerID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.origin = origin;
        this.destination = destination;
        setPrice();
        this.travelStatus=travelStatus;
    }

    public Travel(int id, int driverID, int passengerID, MyDate startDate, MyDate endDate, Coordinate origin, Coordinate destination,TravelStatus travelStatus) {
        this.id = id;
        this.driverID = driverID;
        this.passengerID = passengerID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.origin = origin;
        this.destination = destination;
        setPrice();
        this.travelStatus=travelStatus;
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

    public Coordinate getOrigin() {
        return origin;
    }

    public void setOrigin(Coordinate origin) {
        this.origin = origin;
    }

    public Coordinate getDestination() {
        return destination;
    }

    public void setDestination(Coordinate destination) {
        this.destination = destination;
    }

    public TravelStatus getTravelStatus() {
        return travelStatus;
    }



    public void setTravelStatus(TravelStatus travelStatus) {
        this.travelStatus = travelStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice() {
        double distance=Coordinate.calculateDistance(origin,destination);
        this.price=travelRate*distance;
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
