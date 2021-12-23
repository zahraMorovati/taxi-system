package model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Driver driver;
    @OneToOne
    private Passenger passenger;
    private Date startDate;
    private Date endDate;
    @Embedded
    private Coordinate origin;
    @Embedded
    private Coordinate destination;
    private double price;
    @Enumerated(EnumType.STRING)
    private TravelStatus travelStatus;

    public static final int travelRate=1000;
    public enum TravelStatus{
        ONGOING,WAITING_FOR_PAYMENT,FINISHED;
    }

    public static double calculatePrice(Coordinate c1,Coordinate c2){
        double distance =Coordinate.calculateDistance(c1,c2);
        return distance * travelRate;
    }

    @Override
    public String toString() {
        return "" +
                "travelID=" + id +
                ", driverID=" + driver.getUserID() +
                ", passengerID=" + passenger.getUserID() +
                ", price=" + price+
                ", status=" +travelStatus;
    }


    public static final class TravelBuilder {
        private Travel travel;

        private TravelBuilder() {
            travel = new Travel();
        }

        public static TravelBuilder aTravel() {
            return new TravelBuilder();
        }

        public TravelBuilder setId(int id) {
            travel.setId(id);
            return this;
        }

        public TravelBuilder setDriver(Driver driver) {
            travel.setDriver(driver);
            return this;
        }

        public TravelBuilder setPassenger(Passenger passenger) {
            travel.setPassenger(passenger);
            return this;
        }

        public TravelBuilder setStartDate(Date startDate) {
            travel.setStartDate(startDate);
            return this;
        }

        public TravelBuilder setEndDate(Date endDate) {
            travel.setEndDate(endDate);
            return this;
        }

        public TravelBuilder setOrigin(Coordinate origin) {
            travel.setOrigin(origin);
            return this;
        }

        public TravelBuilder setDestination(Coordinate destination) {
            travel.setDestination(destination);
            return this;
        }

        public TravelBuilder setPrice(double price) {
            travel.setPrice(price);
            return this;
        }

        public TravelBuilder setTravelStatus(TravelStatus travelStatus) {
            travel.setTravelStatus(travelStatus);
            return this;
        }

        public Travel build() {
            return travel;
        }
    }
}
