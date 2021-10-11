package dataAccess;

import model.Coordinate;
import model.Driver;
import myDate.MyDate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static model.Coordinate.stringToCoordinate;
import static validData.ConsoleColors.*;

public class DatabaseAccessDriver extends DatabaseAccess{

    private final int driverStartIndex=370000000;
    public DatabaseAccessDriver() throws ClassNotFoundException, SQLException {
        super();
        if (getConnection() != null) {
            DatabaseMetaData metaData = getConnection().getMetaData();
            ResultSet tables = metaData.getTables(null, null, "driver", null);
            if (!tables.next()) {
                createDriverTable();
            }
        }
    }

    private void createDriverTable() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE driver (" +
                "    user_id INT NOT NULL  AUTO_INCREMENT," +
                "    first_name VARCHAR(25)," +
                "    last_name VARCHAR(25)," +
                "    nationalCode INT," +
                "    phoneNumber VARCHAR(11)," +
                "    birthDate Date," +
                "    car_id INT," +
                "    status boolean DEFAULT false," +
                "    current_coordinate VARCHAR(25)," +
                "    PRIMARY KEY (user_id) ," +
                "    FOREIGN KEY (car_id) REFERENCES car(id))");

        statement.executeUpdate("ALTER TABLE driver AUTO_INCREMENT=370000000");
    }

    public int save(Driver d) throws SQLException {
        if (getConnection() != null) {
            Statement statement = getConnection().createStatement();
            String sqlQuery = String.format("INSERT INTO driver" +
                            " ( first_name,last_name,nationalCode,phoneNumber,birthDate,car_id,status,current_coordinate) " +
                            "VALUES ('%s','%s','%d','%s','%s','%d','%d','%s')",
                 d.getFirstName(),d.getLastName(),d.getNationalCode(),d.getPhoneNumber(),d.getBirthDate().toString(),d.getCarID(),d.getStatus(),d.getCurrentCoordinate().toString());
            int i = statement.executeUpdate(sqlQuery);
            return i;
        } else {
            return 0;
        }
    }

    public List<Driver> findAllDrivers() throws SQLException {
        if (getConnection() != null) {
            List<Driver> driverList = new ArrayList<>();
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from driver");
            while (resultSet.next()) {
                int userID=resultSet.getInt("user_id");
                String firstName=resultSet.getString("first_name");
                String lastName=resultSet.getString("last_name");
                int nationalCode=resultSet.getInt("nationalCode");
                String phoneNumber=resultSet.getString("phoneNumber");
                String strBirthDate=resultSet.getString("birthDate");
                MyDate birthDate=MyDate.convertStringToDate(strBirthDate);
                int carID=resultSet.getInt("car_id");
                Boolean status = resultSet.getBoolean("status");
                Coordinate coordinate=stringToCoordinate(resultSet.getString("current_coordinate"));
                Driver driver = new Driver(userID,firstName,lastName,nationalCode,phoneNumber,birthDate,carID,status,coordinate);
                driverList.add(driver);
            }
            return driverList;
        } else {
            return Collections.emptyList();
        }
    }

    public boolean findDriverByID(int userID) throws SQLException {
        List<Driver> driverList = findAllDrivers();
        for (int i = 0; i < driverList.size(); i++) {
            if (driverList.get(i).getUserID() == userID)
                return true;
        }
        return false;
    }

    public void printDriverInfo(int userID) throws SQLException {
        List<Driver> driverList = findAllDrivers();
        if(findDriverByID(userID)){
            System.out.println(driverList.get(userID-driverStartIndex).toString());
        }
    }

    public void printAllDrivers() throws SQLException {
        List<Driver> driverList = findAllDrivers();
        for (int i = 0; i < driverList.size(); i++) {
            System.out.println(driverList.get(i).toString());
        }
    }

    public void updateDriver(int userID,String firstName,String lastName,int nationalCode,String phoneNumber) throws SQLException {
        if (getConnection() != null) {
            if (findDriverByID(userID)) {
                Statement statement = getConnection().createStatement();
                String sqlQuery = String.format("update driver set " +
                        "first_name='%s',last_name='%s',nationalCode='%d',phoneNumber='%s' where user_id='%d'",
                        firstName,lastName,nationalCode,phoneNumber,userID);
                int i = statement.executeUpdate(sqlQuery);
                if (i >= 0)
                    System.out.println(GREEN+"your information has successfully registered."+RESET);
            } else {
                System.out.println(RED+"user not found!"+RESET);
            }
        } else {
            System.out.println(RED+"cannot connect to database!"+RESET);
        }
    }

}
