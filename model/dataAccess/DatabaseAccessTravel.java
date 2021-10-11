package dataAccess;

import model.Coordinate;
import model.Travel;
import myDate.MyDate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static model.Coordinate.calculateDistance;
import static model.Coordinate.stringToCoordinate;
import static validData.ConsoleColors.*;

public class DatabaseAccessTravel extends DatabaseAccess {

    public DatabaseAccessTravel() throws ClassNotFoundException, SQLException {
        super();
        if (getConnection() != null) {
            DatabaseMetaData metaData = getConnection().getMetaData();
            ResultSet tables = metaData.getTables(null, null, "travel", null);
            if (!tables.next()) {
                createTravelTable();
            }
        }
    }

    private void createTravelTable() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE travel (" +
                "    id INT NOT NULL AUTO_INCREMENT," +
                "    user_id_driver INT NOT NULL ," +
                "    user_id_passenger INT NOT NULL ," +
                "    startDate Date," +
                "    endDate Date," +
                "    origin VARCHAR(50)," +
                "    destination VARCHAR(50)," +
                "    price DOUBLE," +
                "    status VARCHAR(25)," +
                "    PRIMARY KEY (id) ," +
                "    FOREIGN KEY (user_id_driver) REFERENCES driver(user_id)," +
                "    FOREIGN KEY (user_id_passenger) REFERENCES passenger(user_id))");
    }

    public void save(Travel t) throws SQLException {
        if (getConnection() != null) {
            Statement statement = getConnection().createStatement();
            String sqlQuery = String.format("INSERT INTO travel" +
                            " ( user_id_driver,user_id_passenger,startDate,endDate,origin,destination,price,status) " +
                            "VALUES ('%d','%d','%s','%s','%s','%s','%f','%d')",
                    t.getDriverID(),t.getPassengerID(),t.getStartDate().toString(),t.getEndDate().toString(),
            t.getOrigin().toString(),t.getDestination().toString(),t.getPrice(),t.getTravelStatus());
            int i = statement.executeUpdate(sqlQuery);
            if(i>=0){
                System.out.println(GREEN+"your information successfully saved!"+RESET);
            }else {
                System.out.println(RED+"cannot save your information!"+RESET);
            }

        } else {
            System.out.println(RED+"cannot connect to database!"+RESET);
        }
    }

    public List<Travel> findAllTravels() throws SQLException {
        if (getConnection() != null) {
            List<Travel> travelList = new ArrayList<>();
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from travel");
            while (resultSet.next()) {
                int user_id_driver = resultSet.getInt("user_id_driver");
                int user_id_passenger = resultSet.getInt("user_id_passenger");
                MyDate startDate = MyDate.convertStringToDate(resultSet.getString("startDate"));
                MyDate endDate = MyDate.convertStringToDate(resultSet.getString("endDate"));
                Coordinate origin=stringToCoordinate(resultSet.getString("origin"));
                Coordinate destination=stringToCoordinate(resultSet.getString("destination"));
                Travel.TravelStatus status=setValidTravelStatus(resultSet.getString("status"));
                Travel travel = new Travel(user_id_driver,user_id_passenger,startDate,endDate,origin,destination,status);
                travelList.add(travel);
            }
            return travelList;
        } else {
            return Collections.emptyList();
        }
    }

    public Travel.TravelStatus setValidTravelStatus(String str){
        if(str.equals(Travel.TravelStatus.WAITING_FOR_PAYMENT)){
            return Travel.TravelStatus.WAITING_FOR_PAYMENT;
        }else if(str.equals(Travel.TravelStatus.ONGOING)){
            return Travel.TravelStatus.ONGOING;
        }else if(str.equals(Travel.TravelStatus.FINISHED)){
            return Travel.TravelStatus.FINISHED;
        }else return null;
    }

    public void printAllTravel() throws SQLException {
        List<Travel> travelList = findAllTravels();
        System.out.println("travel list: ");
        for (int i = 0; i < travelList.size(); i++) {
            System.out.println(travelList.get(i).toString());
        }
    }

    public void printOngoingTravels() throws SQLException {
        List<Travel> travelList = findAllTravels();
        boolean notFound=false;
        for (Travel travel : travelList) {
            if(travel.getTravelStatus().equals(Travel.TravelStatus.ONGOING)){
                System.out.println(travel.toString());
                notFound=true;
            }
        }
        if(!notFound){
            System.out.println("there is no ongoing travel !");
        }
    }



}
