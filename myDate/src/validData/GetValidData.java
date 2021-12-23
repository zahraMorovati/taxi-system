package validData;
import dataAccess.*;
import dataAccess.DaoDriver;
import dataAccess.DaoPassenger;
import model.*;
import myDate.MyDate;
import java.sql.SQLException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static model.Travel.TravelStatus.FINISHED;
import static validData.ConsoleColors.*;

public class GetValidData {

    public static Scanner input = new Scanner(System.in);
    public static DaoDriver daoDriver = new DaoDriver();
    public static DaoPassenger daoPassenger = new DaoPassenger();

    public static String getValidName(String message) {
        System.out.print(BLUE_BRIGHT + message + RESET);
        String name = input.next();
        if (name.matches("[a-zA-Z]*")) {
            return name;
        } else {
            System.out.println(RED + "invalid input!" + RESET);
            return getValidName(message);
        }
    }

    public static Date getValidBirthDate() {
        try {
            System.out.print(BLUE_BRIGHT + "enter birth date (year month day):" + RESET);
            MyDate birthDate = new MyDate(input.nextInt(), input.nextInt(), input.nextInt());
            if(MyDate.isValidDate(birthDate.getYear(),birthDate.getMonth(),birthDate.getDay())){
                return new Date(birthDate.getYear(),birthDate.getMonth(),birthDate.getDay());
            }else {
                System.out.println(RED + "invalid date!" + RESET);
                return getValidBirthDate();
            }

        } catch (InputMismatchException e) {
            System.out.println(RED + "invalid date!" + RESET);
            return getValidBirthDate();
        }
    }

    public static Date getValidDate(String message) {
        try {
            System.out.print(BLUE_BRIGHT + message + " (year month day):" + RESET);
            MyDate birthDate = new MyDate(input.nextInt(), input.nextInt(), input.nextInt());
            return new Date(birthDate.getYear(),birthDate.getMonth(),birthDate.getDay());
        } catch (InputMismatchException e) {
            System.out.println(RED + "invalid date!" + RESET);
            return getValidDate(message);
        }
    }

    public static String getValidPhoneNumber(String message) {
        System.out.print(BLUE_BRIGHT + message + RESET);
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
        System.out.print(BLUE_BRIGHT + message + RESET);
        String str = input.next();
        if (isNumeric(str)) {
            return Integer.parseInt(str);
        } else {
            System.out.println(RED + "invalid input!" + RESET);
            return getValidInt(message);
        }
    }

    public static double getValidDouble(String message) {
        System.out.print(BLUE_BRIGHT + message + RESET);
        String str = input.next();
        if (isNumeric(str)) {
            return Double.parseDouble(str);
        } else {
            System.out.println(RED + "invalid input!" + RESET);
            return getValidDouble(message);
        }
    }

    public static Coordinate getValidCoordinate(String message) {
        try {
            int length = getValidInt(message + " length: ");
            int width = getValidInt(message + " width: ");
            Coordinate coordinate=Coordinate.CoordinateBuilder.aCoordinate().setLength(length).setWidth(width).build();
            return coordinate;
        } catch (InputMismatchException e) {
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
        int choice = getValidChoice(CYAN_BOLD + "1)WHITE, 2)BLACK, 3)GRAY, 4)SILVER, 5)RED, 6)BLUE, 7)BROWN, 8)GREEN, 9)BEIGE\nenter your choice: " + RESET, 9);
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

    public static CarType getValidCarType() {
        int choice = getValidChoice(CYAN_BOLD + "1)PERIDE, 2)SAMAND, 3)PEJO, 4)PEJO206, 5)PEJOPARS, 6)PEIKAN\nenter your choice:" + RESET, 6);
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

    public static Driver getDriver(DaoCar databaseAccessCar) throws SQLException {
        String firstName = getValidName("first name: ");
        String lastName = getValidName("last name: ");
        int nationalCode = getValidInt("national code: ");
        String phoneNumber = getValidPhoneNumber("phone number: ");
        Date birthDate = getValidBirthDate();
        Car car = getCarInfo();
        databaseAccessCar.save(car);
        int carID = databaseAccessCar.lastCarID();
        Driver driver = setDriver(firstName, lastName, nationalCode, phoneNumber, birthDate, car);
        return driver;
    }

    private static Driver setDriver(String firstName, String lastName, int nationalCode, String phoneNumber, Date birthDate, Car car) {
        Driver driver =Driver.DriverBuilder.aDriver()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setNationalCode(nationalCode)
                .setPhoneNumber(phoneNumber)
                .setBirthDate(birthDate)
                .setCar(car)
                .setStatus(false)
                .setCurrentCoordinate(Coordinate.defaultCoordinate()).build();
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
        Car car = Car.CarBuilder.aCar()
                .setId(1)
                .setPlaque(plaque)
                .setOwnerFirstName(ownerFirstName)
                .setOwnerLAstName(ownerLastname)
                .setVehicleColor(vehicleColor)
                .setCarType(carType)
                .build();

        return car;
    }

    public static Passenger getPassengerInfo() {
        String firstName = getValidName("first name: ");
        String lastName = getValidName("last name: ");
        int nationalCode = getValidInt("national code: ");
        String phoneNumber = getValidPhoneNumber("phone number: ");
        Date birthDate = getValidBirthDate();
        double balance = getValidDouble("balance: ");
        Passenger passenger = setPassenger(firstName, lastName, nationalCode, phoneNumber, birthDate, balance);
        return passenger;
    }

    private static Passenger setPassenger(String firstName, String lastName, int nationalCode, String phoneNumber, Date birthDate, double balance) {
        Passenger passenger =Passenger.PassengerBuilder.aPassenger()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setNationalCode(nationalCode)
                .setPhoneNumber(phoneNumber)
                .setBirthDate(birthDate)
                .setBalance(balance)
                .setStatus(false).build();
        return passenger;
    }

    public static Travel getTravelInfo(Travel.TravelStatus travelStatus, int userIdDriver, int userIdPassenger,
                                       DaoPassenger accessPassenger, Boolean payByBalance) throws SQLException {
        Date startDate = getValidDate("startDate: ");
        Date endDate = getValidDate("startDate: ");
        Coordinate origin = getValidCoordinate("origin: ");
        Coordinate destination = getValidCoordinate("destination: ");
        double price = Travel.calculatePrice(origin, destination);
        Travel travel = setTravel(travelStatus, userIdDriver, userIdPassenger, startDate, endDate, origin, destination);
        if (accessPassenger.checkBalanceIsEnough(userIdPassenger, price) && payByBalance==true) {
            return travel;
        } else if(!payByBalance){
            return travel;
        }else {
            return null;
        }
    }

    private static Travel setTravel(Travel.TravelStatus travelStatus, int userIdDriver, int userIdPassenger, Date startDate, Date endDate, Coordinate origin, Coordinate destination) {
        Travel travel = Travel.TravelBuilder.aTravel()
                .setDriver(daoDriver.getDriverByID(userIdDriver))
                .setPassenger(daoPassenger.getPassengerByID(userIdPassenger))
                .setEndDate(endDate).setOrigin(origin)
                .setDestination(destination).setPrice(Travel.calculatePrice(origin,destination))
                .setTravelStatus(travelStatus).build();

        return new Travel();
    }


    public static void saveTravelByCash(DaoTravel accessTravel,
                                        DaoDriver accessDriver,
                                        DaoPassenger accessPassenger) throws SQLException {

        int userIdDriver = getValidInt("user id driver: ");
        int userIdPassenger = getValidInt("user id passenger: ");

        //print list driver
        if (appropriateMessageForSavingTravel(accessDriver, accessPassenger, userIdDriver, userIdPassenger)) {
            Travel newTravel = getTravelInfo(Travel.TravelStatus.WAITING_FOR_PAYMENT, userIdDriver, userIdPassenger, accessPassenger,false);
            accessTravel.save(newTravel);
            accessDriver.updateStatus(userIdDriver,true);
            accessPassenger.updateStatus(userIdPassenger,true);
            System.out.println(GREEN + "travel saved successfully!" + RESET);
        }
    }


    public static void saveTravelByBalance(DaoTravel accessTravel,
                                           DaoDriver accessDriver,
                                           DaoPassenger accessPassenger) throws SQLException {

        int userIdDriver = getValidInt("user id driver: ");
        int userIdPassenger = getValidInt("user id passenger: ");
        if (appropriateMessageForSavingTravel(accessDriver, accessPassenger, userIdDriver, userIdPassenger)) {
            Travel newTravel = getTravelInfo(Travel.TravelStatus.ONGOING, userIdDriver, userIdPassenger, accessPassenger,true);
            if (newTravel != null) {
                accessTravel.save(newTravel);
                double price=Travel.calculatePrice(newTravel.getOrigin(),newTravel.getDestination());
                accessPassenger.paymentTravelByBalance(userIdPassenger, price);
                accessDriver.updateStatus(userIdDriver,true);
            }else {
                System.out.println(RED+"balance is not enough!"+RESET);
            }
        }
    }

    public static void finishingTravel(DaoPassenger accessPassenger
            , DaoDriver accessDriver, DaoTravel accessTravel) throws SQLException {

        boolean dataFound = false;
        int idTravel = getValidInt("enter travel id :");
        List<Travel> travelList = accessTravel.findAllTravels();
        for (Travel travel : travelList) {
            if (travel.getId() == idTravel) {
                int userIdPassenger = travel.getPassenger().getUserID();
                int userIdDriver = travel.getDriver().getUserID();
                String currentCoordinate = travel.getDestination().toString();
                accessDriver.updateStatus(userIdDriver, false);
                accessDriver.updateCurrentCoordinate(userIdDriver, currentCoordinate);
                accessPassenger.updateStatus(userIdPassenger, false);
                accessTravel.updateStatus(idTravel, FINISHED);
                dataFound = true;

            }
        }

        if (!dataFound)
            System.out.println(RED + "operation hasn't done successfully!" + RESET);
        else
            System.out.println(GREEN + "operation has done successfully!" + RESET);

    }

    public static boolean appropriateMessageForSavingTravel(DaoDriver accessDriver,
                                                            DaoPassenger accessPassenger,
                                                            int userIdDriver, int userIdPassenger) throws SQLException {
        if (accessDriver.checkDriverStatus(userIdDriver)) {
            if (accessPassenger.checkPassengerStatus(userIdPassenger)) {
                return true;
            } else {
                System.out.println(RED + "invalid passenger userID!" + RESET);
                return false;
            }
        } else {
            System.out.println(RED + "this driver is unavailable!" + RESET);
            return false;
        }
    }


}
