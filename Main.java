import dataAccess.DatabaseAccessCar;
import dataAccess.DatabaseAccessDriver;
import dataAccess.DatabaseAccessPassenger;
import dataAccess.DatabaseAccessTravel;
import model.*;
import myDate.MyDate;

import java.sql.SQLException;
import java.util.Scanner;

import static validData.ConsoleColors.*;
import static validData.GetValidData.*;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        printMenu();
    }

    public static void printMenu() throws SQLException, ClassNotFoundException {

        try {
            DatabaseAccessDriver databaseAccessDriver = new DatabaseAccessDriver();
            DatabaseAccessPassenger databaseAccessPassenger = new DatabaseAccessPassenger();
            DatabaseAccessCar databaseAccessCar = new DatabaseAccessCar();
            DatabaseAccessTravel databaseAccessTravel=new DatabaseAccessTravel();

            System.out.println(PURPLE_BRIGHT + "*********** welcome *************" + RESET);
            int choice = getValidChoice(PURPLE_BOLD + "1)add a group of passengers" +
                    "\n2)add a group of drivers" +
                    "\n3)login or register passenger" +
                    "\n4)login or register driver" +
                    "\n5)ongoing travels" +
                    "\n6)list passengers" +
                    "\n7)list drivers" +
                    "\n8)exit" +
                    "\nenter your choice: " + RESET, 8);


            switch (choice) {

                case 1: {
                    int n = getValidInt("enter number of passengers:");
                    int savedPassengers = 0;
                    for (int i = 1; i < n + 1; i++) {
                        System.out.println(WHITE_BOLD + "enter passenger number " + i + " info:" + RESET);
                        Passenger passenger = getPassengerInfo();
                        savedPassengers += databaseAccessPassenger.save(passenger);
                    }
                    if (savedPassengers == n) {
                        System.out.println(GREEN + "your information has successfully added." + RESET);
                    }
                    printMenu();
                }
                break;
                case 2: {
                    int n = getValidInt("enter number of drivers:");
                    int savedDrivers = 0;
                    for (int i = 1; i < n + 1; i++) {
                        System.out.println(WHITE_BOLD + "enter driver number " + i + " info:" + RESET);
                        Driver driver = getDriver(databaseAccessCar);
                        savedDrivers += databaseAccessDriver.save(driver);
                    }
                    if (savedDrivers == n) {
                        System.out.println(GREEN_BOLD + "your information has successfully added." + RESET);
                    }
                    printMenu();
                }
                break;
                case 3: {
                    RegisterOrLoginPassenger(databaseAccessPassenger);
                    printMenu();
                }
                break;
                case 4: {
                    registerOrLoginDriver(databaseAccessDriver, databaseAccessCar);
                    printMenu();
                }
                break;
                case 5: {
                    ongoingTravelMenu(databaseAccessTravel,databaseAccessDriver,databaseAccessPassenger);
                }
                break;
                case 6: {
                    databaseAccessPassenger.printAllPassenger();
                    printMenu();
                }
                break;
                case 7: {
                    databaseAccessDriver.printAllDrivers();
                    printMenu();
                }
                break;
                case 8: {
                    System.exit(0);
                }

                default: {
                    System.out.println(RED + "invalid choice!" + RESET);
                    printMenu();
                }

            }
        }catch (RuntimeException e){
            System.out.println(e.getCause());
            printMenu();
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
        Driver driver = new Driver(firstName, lastName, nationalCode, phoneNumber, birthDate, carID,false,null);
        return driver;
    }

    public static Car getCarInfo() {
        System.out.println("enter car info:");
        System.out.print(BLUE_BRIGHT + "plaque: " + RESET);
        String plaque = scanner.next();
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

    public static void RegisterOrLoginPassenger(DatabaseAccessPassenger dap) throws SQLException, ClassNotFoundException {
        int userId = getValidInt("enter user id: ");
        if (dap.findPassengerByID(userId)) {
            dap.printPassengerInfo(userId);
            increaseBalanceMenu(dap, userId);
        } else {
            System.out.println("user not found!");
            int choice = getValidChoice(PURPLE_BRIGHT + "1)add a new passenger \n2)enter user id again \n3)back to main menu \nenter your choice" + RESET, 3);
            switch (choice) {
                case 1: {
                    Passenger passenger = getPassengerInfo();
                    dap.save(passenger);
                    printMenu();
                }
                break;
                case 2: {
                    RegisterOrLoginPassenger(dap);
                }
                break;
                case 3:
                    printMenu();
                    break;
                default: {
                    System.out.println("invalid choice!");
                }
                break;
            }
        }
    }

    public static void increaseBalanceMenu(DatabaseAccessPassenger dap, int userId) throws SQLException, ClassNotFoundException {
        int choice = getValidChoice(PURPLE_BRIGHT + "1)increase balance 2)exit \nenter your choice:" + RESET, 2);
        switch (choice) {
            case 1: {
                double amount = getValidDouble(BLUE_BRIGHT + "enter amount: " + RESET);
                dap.increaseBalance(userId, amount);
                increaseBalanceMenu(dap, userId);
            }
            break;
            case 2: {
                printMenu();
            }
            break;
            default: {
                System.out.println(RED + "invalid choice!" + RESET);
            }
            break;
        }
    }

    public static void registerOrLoginDriver(DatabaseAccessDriver databaseAccessDriver, DatabaseAccessCar databaseAccessCar) throws SQLException, ClassNotFoundException {
        int userID = getValidInt("enter user id: ");
        if (databaseAccessDriver.findDriverByID(userID)) {
            databaseAccessDriver.printDriverInfo(userID);
            registerDriverMenu(databaseAccessDriver, userID);
        } else {
            System.out.println(RED + "user not found!" + RESET);
            int choice = getValidChoice(PURPLE_BRIGHT + "1)add a new driver \n2)enter user id again \n3)back to main menu \nenter your choice:" + RESET, 3);
            switch (choice) {
                case 1: {
                    Driver driver = getDriver(databaseAccessCar);
                    int i=databaseAccessDriver.save(driver);
                    if(i>=0)
                        System.out.println(GREEN+"your information successfully saved!"+RESET);
                    printMenu();
                }
                break;
                case 2:
                    registerOrLoginDriver(databaseAccessDriver, databaseAccessCar);
                    break;
                case 3:
                    printMenu();
                    break;
                default: {
                    System.out.println("invalid choice!");
                }
                break;
            }

        }
    }

    public static void registerDriver(DatabaseAccessDriver databaseAccessDriver, int userID) throws SQLException {
        String firstName = getValidName("first name: ");
        String lastName = getValidName("last name: ");
        int nationalCode = getValidInt("national code: ");
        String phoneNumber = getValidPhoneNumber("phone number: ");
        databaseAccessDriver.updateDriver(userID, firstName, lastName, nationalCode, phoneNumber);
    }

    public static void registerDriverMenu(DatabaseAccessDriver databaseAccessDriver, int userID) throws SQLException, ClassNotFoundException {
        int choice = getValidChoice(PURPLE_BRIGHT + "1)register \n2)back to main menu \nenter your choice: " + RESET, 2);
        switch (choice) {
            case 1: {
                registerDriver(databaseAccessDriver, userID);
                printMenu();
            }
            break;
            case 2: {
                printMenu();
            }
            break;
            default: {
                System.out.println("invalid choice!");
            }
        }
    }

    public static void saveTravel(DatabaseAccessTravel accessTravel,DatabaseAccessDriver accessDriver,DatabaseAccessPassenger accessPassenger) throws SQLException {
        int user_id_driver = getValidInt("user_id_driver: ");
        int user_id_passenger = getValidInt("user_id_passenger: ");
        MyDate startDate = getValidDate("startDate: ");
        MyDate endDate = getValidDate("startDate: ");
        Coordinate origin = getValidCoordinate("origin: ");
        Coordinate destination = getValidCoordinate("destination: ");
        double price = getValidDouble("price: ");
        Travel travel=new Travel(user_id_driver,user_id_passenger,startDate,endDate,origin,destination,price,false);
        if(accessPassenger.findPassengerByID(user_id_passenger)){
            if(accessPassenger.checkBalanceIsEnough(user_id_passenger,price)){
                if(accessDriver.findDriverByID(user_id_driver)){
                    accessTravel.save(travel);
                    accessPassenger.paymentTravel(user_id_passenger,price);
                }else {
                    System.out.println(RED+"incorrect driver userID !"+RESET);
                }
            }else {
                System.out.println(RED+"your balance is not enough !"+RESET);
            }
        }else {
            System.out.println(RED+"incorrect passenger userID !"+RESET);
        }
    }

    public static void ongoingTravelMenu(DatabaseAccessTravel accessTravel,DatabaseAccessDriver accessDriver,DatabaseAccessPassenger accessPassenger) throws SQLException, ClassNotFoundException {
        int choice=getValidChoice(PURPLE_BRIGHT+"1)add travel \n2)show ongoing travels \n3)Back to main menu \nenter your choice: "+RESET,3);
        switch (choice){
            case 1:{
                saveTravel(accessTravel,accessDriver,accessPassenger);
                ongoingTravelMenu(accessTravel,accessDriver,accessPassenger);
            }break;
            case 2:{
                accessPassenger.printOngoingTravels();
                ongoingTravelMenu(accessTravel,accessDriver,accessPassenger);
            }break;
            case 3: printMenu(); break;
            default:{
                System.out.println("invalid choice!");
            }break;
        }
    }
}
