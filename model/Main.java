import dataAccess.DatabaseAccessCar;
import dataAccess.DatabaseAccessDriver;
import dataAccess.DatabaseAccessPassenger;
import dataAccess.DatabaseAccessTravel;
import model.Driver;
import model.Passenger;

import java.sql.SQLException;

import static validData.ConsoleColors.*;
import static validData.GetValidData.*;

public class Main {

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
                    ongoingTravelMenu(databaseAccessTravel);
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
            System.out.println(RED+" RuntimeException! "+RESET);
            printMenu();
        }
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

    public static void ongoingTravelMenu(DatabaseAccessTravel accessTravel) throws SQLException, ClassNotFoundException {
        int choice=getValidChoice(PURPLE_BRIGHT+"1))show ongoing travels \n2)Back to main menu \nenter your choice: "+RESET,2);
        switch (choice){
            case 1:{
                accessTravel.printOngoingTravels();
                ongoingTravelMenu(accessTravel);
            }break;
            case 2: printMenu(); break;
            default:{
                System.out.println("invalid choice!");
            }break;
        }
    }


}
