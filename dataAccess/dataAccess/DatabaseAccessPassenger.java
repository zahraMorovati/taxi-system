package dataAccess;

import model.Passenger;
import myDate.MyDate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseAccessPassenger extends DatabaseAccess {

    private final int passengerStartIndex = 490000000;

    public DatabaseAccessPassenger() throws ClassNotFoundException, SQLException {
        super();
        if (getConnection() != null) {
            DatabaseMetaData metaData = getConnection().getMetaData();
            ResultSet tables = metaData.getTables(null, null, "passenger", null);
            if (!tables.next()) {
                createPassengerTable();
            }
        }
    }

    private void createPassengerTable() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE passenger (" +
                "    user_id INT NOT NULL AUTO_INCREMENT," +
                "    first_name VARCHAR(25)," +
                "    last_name VARCHAR(25)," +
                "    nationalCode INT," +
                "    phoneNumber VARCHAR(11)," +
                "    birthDate Date," +
                "    balance DOUBLE," +
                "    status boolean DEFAULT false," +
                "    PRIMARY KEY (user_id))");
        statement.executeUpdate("    ALTER TABLE passenger AUTO_INCREMENT=490000000");
    }

    public int save(Passenger p) throws SQLException {
        if (getConnection() != null) {
            Statement statement = getConnection().createStatement();
            String sqlQuery = String.format("INSERT INTO passenger" +
                            " ( first_name,last_name,nationalCode,phoneNumber,birthDate,balance,status) " +
                            "VALUES ('%s','%s','%d','%s','%s','%f','%d')",
                    p.getFirstName(), p.getLastName(), p.getNationalCode(), p.getPhoneNumber(), p.getBirthDate().toString(), p.getBalance(), p.getStatus());
            int i = statement.executeUpdate(sqlQuery);
            return i;
        } else {
            return 0;
        }
    }

    public List<Passenger> findAllPassengers() throws SQLException {
        if (getConnection() != null) {
            List<Passenger> passengerList = new ArrayList<>();
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from passenger");
            while (resultSet.next()) {
                int userID = resultSet.getInt("user_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int nationalCode = resultSet.getInt("nationalCode");
                String phoneNumber = resultSet.getString("phoneNumber");
                MyDate birthDate = MyDate.convertStringToDate(resultSet.getString("birthDate"));
                double balance = resultSet.getDouble("balance");
                Boolean status = resultSet.getBoolean("status");
                Passenger passenger = new Passenger(userID, firstName, lastName, nationalCode, phoneNumber, birthDate, balance, status);
                passengerList.add(passenger);
            }
            return passengerList;
        } else {
            return Collections.emptyList();
        }
    }

    public void printAllPassenger() throws SQLException {
        List<Passenger> passengerList = findAllPassengers();
        System.out.println("passengers list: ");
        for (int i = 0; i < passengerList.size(); i++) {
            System.out.println(passengerList.get(i).toString());
        }
    }

    public boolean findPassengerByID(int userID) throws SQLException {
        List<Passenger> passengerList = findAllPassengers();
        for (int i = 0; i < passengerList.size(); i++) {
            if (passengerList.get(i).getUserID() == userID)
                return true;
        }
        return false;
    }

    public void printPassengerInfo(int userID) throws SQLException {
        List<Passenger> passengerList = findAllPassengers();
        if (findPassengerByID(userID)) {
            System.out.println(passengerList.get(userID - passengerStartIndex).toString());
        }
    }

    public void increaseBalance(int userID, double amount) throws SQLException {

        if (getConnection() != null) {
            if (findPassengerByID(userID)) {
                List<Passenger> passengerList = findAllPassengers();
                double balance = passengerList.get(userID - passengerStartIndex).getBalance();
                balance += amount;
                Statement statement = getConnection().createStatement();
                String sqlQuery = String.format("update passenger set balance='%f' where user_id='%d' ", balance, userID);
                int i = statement.executeUpdate(sqlQuery);
                if (i > 0)
                    System.out.println("your account balance has been updated.");
            } else {
                System.out.println("user not found!");
            }
        } else {
            System.out.println("cannot connect to database!");
        }
    }

    public void paymentTravel(int userID, double amount) throws SQLException {

        if (getConnection() != null) {
            if (findPassengerByID(userID)) {
                List<Passenger> passengerList = findAllPassengers();
                double balance = passengerList.get(userID - passengerStartIndex).getBalance();
                balance -= amount;
                if (balance > 0) {
                    Statement statement = getConnection().createStatement();
                    String sqlQuery = String.format("update passenger set balance='%f',status='%d' where user_id='%d' ", balance,1, userID);
                    int i = statement.executeUpdate(sqlQuery);
                    if (i > 0) {
                        System.out.println("your account balance has been updated.");
                    }
                }
            }

        } else {
            System.out.println("cannot connect to database!");
        }
    }

    public boolean checkBalanceIsEnough(int userID, double amount) throws SQLException {
        if (findPassengerByID(userID)) {
            List<Passenger> passengerList = findAllPassengers();
            double balance = passengerList.get(userID - passengerStartIndex).getBalance();
            balance -= amount;
            if (balance > 0) {
                return true;
            }
        }
        return false;
    }

    public void printOngoingTravels() throws SQLException {
        boolean emptyResult=false;
        List<Passenger> passengerList = findAllPassengers();
        for (int i = 0; i < passengerList.size(); i++) {
            if(passengerList.get(i).getStatus()==1){
                System.out.println(passengerList.get(i).toString());
                emptyResult=true;
            }
        }
        if(!emptyResult){
            System.out.println("there is no ongoing travel !");
        }
    }

}
