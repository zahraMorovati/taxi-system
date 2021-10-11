package dataAccess;

import model.Passenger;
import model.Travel;
import myDate.MyDate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                "    PRIMARY KEY (id) ," +
                "    FOREIGN KEY (user_id_driver) REFERENCES driver(user_id)," +
                "    FOREIGN KEY (user_id_passenger) REFERENCES passenger(user_id))");
    }

    public void save(Travel t) throws SQLException {
        if (getConnection() != null) {
            Statement statement = getConnection().createStatement();
            String sqlQuery = String.format("INSERT INTO travel" +
                            " ( user_id_driver,user_id_passenger,startDate,endDate,origin,destination,price) " +
                            "VALUES ('%d','%d','%s','%s','%s','%s','%f')",
                    t.getDriverID(),t.getPassengerID(),t.getStartDate().toString(),t.getEndDate().toString(),
            t.getOrigin(),t.getDestination(),t.getPrice());
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
                String origin = resultSet.getString("origin");
                String destination = resultSet.getString("destination");
                double price = resultSet.getDouble("price");
                Travel travel = new Travel(user_id_driver,user_id_passenger,startDate,endDate,origin,destination,price);
                travelList.add(travel);
            }
            return travelList;
        } else {
            return Collections.emptyList();
        }
    }

    public void printAllTravel() throws SQLException {
        List<Travel> travelList = findAllTravels();
        System.out.println("travel list: ");
        for (int i = 0; i < travelList.size(); i++) {
            System.out.println(travelList.get(i).toString());
        }
    }

}
