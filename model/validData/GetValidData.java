package validData;

import dataAccess.DatabaseAccessCar;
import model.*;
import myDate.MyDate;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static validData.ConsoleColors.*;


public class GetValidData {

    public static Scanner input=new Scanner(System.in);

    public static String getValidName(String message) {
        System.out.print(BLUE_BRIGHT+message+RESET);
        String name = input.next();
        if (name.matches("[a-zA-Z]*")) {
            return name;
        } else {
            System.out.println(RED + "invalid input!" + RESET);
            return getValidName(message);
        }
    }

    public static MyDate getValidBirthDate() {
        try {
            System.out.print(BLUE_BRIGHT+"enter birth date (year month day):"+RESET);
            MyDate birthDate = new MyDate(input.nextInt(), input.nextInt(), input.nextInt());
            return birthDate;
        } catch (InputMismatchException e) {
            System.out.println(RED + "invalid date!" + RESET);
            return getValidBirthDate();
        }
    }

    public static MyDate getValidDate(String message) {
        try {
            System.out.print(BLUE_BRIGHT+message+" (year month day):"+RESET);
            MyDate birthDate = new MyDate(input.nextInt(), input.nextInt(), input.nextInt());
            return birthDate;
        } catch (InputMismatchException e) {
            System.out.println(RED + "invalid date!" + RESET);
            return getValidBirthDate();
        }
    }

    public static String getValidPhoneNumber(String message) {
        System.out.print(BLUE_BRIGHT+message+RESET);
        String phoneNumber = input.next();
        String str = phoneNumber.substring(0);
        if (isNumeric(str)) {
            return phoneNumber;
        } else {
            System.out.println(RED + "invalid phone number!" + RESET);
            return getValidPhoneNumber(message);
        }
    }

    public static int getValidInt(String message) {
        System.out.print(BLUE_BRIGHT+message+RESET);
        String str = input.next();
        if (isNumeric(str)) {
            return Integer.parseInt(str);
        } else {
            System.out.println(RED + "invalid input!" + RESET);
            return getValidInt(message);
        }
    }

    public static double getValidDouble(String message) {
        System.out.print(BLUE_BRIGHT+message+RESET);
        String str = input.next();
        if (isNumeric(str)) {
            return Double.parseDouble(str);
        } else {
            System.out.println(RED + "invalid input!" + RESET);
            return getValidDouble(message);
        }
    }

    public static Coordinate getValidCoordinate(String message){
        try {
            int length=getValidInt(message+" length: ");
            int width=getValidInt(message+" width: ");
            Coordinate coordinate=new Coordinate(length,width);
            return coordinate;
        }catch (InputMismatchException e){
            return getValidCoordinate(message);
        }
    }

    public static int getValidChoice(String message, int maxChoice) {
        int number = getValidInt(message);
        for (int i = 1; i < maxChoice + 1; i++) {
            if (i == number) {
                return number;
            }
        }
        System.out.println(RED + "invalid choice!" + RESET);
        return getValidChoice(message, maxChoice);
    }

    public static boolean isNumeric(String str) {

        if (str == null || str.length() == 0) {
            return false;
        }

        try {
            Double.parseDouble(str);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }

    }

    public static VehicleColor getValidColor() {
        int choice = getValidChoice(CYAN_BOLD+"1)WHITE, 2)BLACK, 3)GRAY, 4)SILVER, 5)RED, 6)BLUE, 7)BROWN, 8)GREEN, 9)BEIGE\nenter your choice: "+RESET, 9);
        switch (choice) {
            case 1:
                return VehicleColor.WHITE;
            case 2:
                return VehicleColor.BLACK;
            case 3:
                return VehicleColor.GRAY;
            case 4:
                return VehicleColor.SILVER;
            case 5:
                return VehicleColor.RED;
            case 6:
                return VehicleColor.BLUE;
            case 7:
                return VehicleColor.BROWN;
            case 8:
                return VehicleColor.GREEN;
            case 9:
                return VehicleColor.BEIGE;
            default:
                return null;
        }
    }

    public static CarType getValidCarType(){
        int choice = getValidChoice(CYAN_BOLD+"1)PERIDE, 2)SAMAND, 3)PEJO, 4)PEJO206, 5)PEJOPARS, 6)PEIKAN\nenter your choice:"+RESET, 6);
        switch (choice) {
            case 1:
                return CarType.PERIDE;
            case 2:
                return CarType.SAMAND;
            case 3:
                return CarType.PEJO;
            case 4:
                return CarType.PEJO206;
            case 5:
                return CarType.PEJOPARS;
            case 6:
                return CarType.PEIKAN;
            default:
                return null;
        }
    }

    public static Driver getDriver(DatabaseAccessCar databaseAccessCar) throws SQLException {
        String firstName = getValidName("first name: ");
        String lastName = getValidName("last name: ");
        int nationalCode = getValidInt("national code: ");
        String phoneNumber = getValidPhoneNumber("phone number: ");
        MyDate birthDate = getValidBirthDate();
        Car car = getCarInfo();
        databaseAccessCar.save(car);
        int carID = databaseAccessCar.lastCarID();
        Driver driver = new Driver(firstName, lastName, nationalCode, phoneNumber, birthDate, carID,false,Coordinate.defaultCoordinate());
        return driver;
    }

    public static Car getCarInfo() {
        System.out.println("enter car info:");
        System.out.print(BLUE_BRIGHT + "plaque: " + RESET);
        String plaque = input.next();
        String ownerFirstName = getValidName("owner first name: ");
        String ownerLastname = getValidName("owner last name: ");
        VehicleColor vehicleColor = getValidColor();
        CarType carType = getValidCarType();
        Car car = new Car(1, plaque, ownerFirstName, ownerLastname, vehicleColor, carType);
        return car;
    }

    public static Passenger getPassengerInfo() {
        String firstName = getValidName("first name: ");
        String lastName = getValidName("last name: ");
        int nationalCode = getValidInt("national code: ");
        String phoneNumber = getValidPhoneNumber("phone number: ");
        MyDate birthDate = getValidBirthDate();
        double balance = getValidDouble("balance: ");
        Passenger passenger = new Passenger(firstName, lastName, nationalCode, phoneNumber, birthDate, balance, false);
        return passenger;
    }

    public static void getTravelInfo(Travel.TravelStatus travelStatus){
        int user_id_driver = getValidInt("user_id_driver: ");
        int user_id_passenger = getValidInt("user_id_passenger: ");
        MyDate startDate = getValidDate("startDate: ");
        MyDate endDate = getValidDate("startDate: ");
        Coordinate origin = getValidCoordinate("origin: ");
        Coordinate destination = getValidCoordinate("destination: ");
        Travel travel=new Travel(user_id_driver,user_id_passenger,startDate,endDate,origin,destination, travelStatus);
    }


}
