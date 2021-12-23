import dataAccess.*;
import model.Driver;
import model.Passenger;
import java.sql.SQLException;
import static validData.ConsoleColors.*;
import static validData.GetValidData.*;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Dao databaseAccess = new Dao();
        MySessionFactory mySessionFactory=new MySessionFactory();
        printMenu();

    }

    public static void printMenu() throws SQLException, ClassNotFoundException {

        try {
            DaoCar accessCar = new DaoCar();
            DaoDriver accessDriver = new DaoDriver();
            DaoPassenger accessPassenger = new DaoPassenger();
            DaoTravel accessTravel = new DaoTravel();

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
                    for (int i = 1; i < n + 1; i++) {
                        System.out.println(WHITE_BOLD + "enter passenger number " + i + " info:" + RESET);
                        Passenger passenger = getPassengerInfo();
                        accessPassenger.save(passenger);
                    }
                    printMenu();
                }
                break;
                case 2: {
                    int n = getValidInt("enter number of drivers:");
                    for (int i = 1; i < n + 1; i++) {
                        System.out.println(WHITE_BOLD + "enter driver number " + i + " info:" + RESET);
                        Driver driver = getDriver(accessCar);
                        accessDriver.save(driver);
                    }
                    printMenu();
                }
                break;
                case 3: {
                    registerOrLoginPassenger(accessTravel, accessDriver, accessPassenger);
                    printMenu();
                }
                break;
                case 4: {
                    registerOrLoginDriver(accessDriver, accessTravel, accessPassenger, accessCar);
                    printMenu();
                }
                break;
                case 5: {
                    ongoingTravelMenu(accessTravel);
                }
                break;
                case 6: {
                    accessPassenger.printAllPassenger();
                    printMenu();
                }
                break;
                case 7: {
                    accessDriver.printAllDrivers();
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
        } catch (RuntimeException e) {
            System.out.println(YELLOW_BACKGROUND + e.getLocalizedMessage() + RESET);
            System.out.println(RED + " RuntimeException! " + RESET);
            printMenu();
        }
    }

    public static void registerOrLoginPassenger(DaoTravel accessTravel, DaoDriver accessDriver,
                                                DaoPassenger accessPassenger) throws SQLException, ClassNotFoundException {

        int userId = getValidInt("enter user id: ");
        if (accessPassenger.findPassengerByID(userId)) {
            accessPassenger.printPassengerInfo(userId);

            registerPassengerMenu(accessTravel, accessDriver, accessPassenger);
        } else {
            System.out.println("user not found!");
            int choice = getValidChoice(PURPLE_BRIGHT + "1)add a new passenger" +
                    " \n2)enter user id again \n3)back to main menu \nenter your choice" + RESET, 3);
            switch (choice) {
                case 1: {
                    Passenger passenger = getPassengerInfo();
                    accessPassenger.save(passenger);
                    printMenu();
                }
                break;
                case 2: {
                    registerOrLoginPassenger(accessTravel, accessDriver, accessPassenger);
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

    public static void registerPassengerMenu(DaoTravel accessTravel,
                                             DaoDriver accessDriver,
                                             DaoPassenger accessPassenger)
            throws SQLException, ClassNotFoundException {

        int choice = getValidChoice("1)save travel by cash" +
                "\n2)save travel by balance" +
                "\n3)increase balance" +
                "\n4)back to main menu" +
                "\nenter your choice: ", 4);

        switch (choice) {
            case 1: {
                saveTravelByCash(accessTravel, accessDriver, accessPassenger);
                registerPassengerMenu(accessTravel, accessDriver, accessPassenger);
            }
            break;
            case 2: {
                saveTravelByBalance(accessTravel, accessDriver, accessPassenger);
                registerPassengerMenu(accessTravel, accessDriver, accessPassenger);
            }
            break;
            case 3: {
                int userIdPassenger = getValidInt("passenger userID: ");
                increaseBalanceMenu(accessPassenger, userIdPassenger);
                registerPassengerMenu(accessTravel, accessDriver, accessPassenger);
            }
            break;
            case 4: {
                printMenu();
            }
        }
    }

    public static void increaseBalanceMenu(DaoPassenger accessPassenger, int userId)
            throws SQLException, ClassNotFoundException {

        int choice = getValidChoice(PURPLE_BRIGHT + "1)increase balance 2)exit \nenter your choice:" + RESET, 2);
        switch (choice) {
            case 1: {
                double amount = getValidDouble(BLUE_BRIGHT + "enter amount: " + RESET);
                accessPassenger.increaseBalance(userId, amount);
                increaseBalanceMenu(accessPassenger, userId);
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

    public static void registerOrLoginDriver(DaoDriver accessDriver,
                                             DaoTravel accessTravel,
                                             DaoPassenger accessPassenger,
                                             DaoCar accessCar) throws SQLException, ClassNotFoundException {

        int userID = getValidInt("enter user id: ");
        if (accessDriver.findDriverByID(userID)) {
            accessDriver.printDriverInfo(userID);
            if (!accessDriver.getStatusDriver(userID)) {
                System.out.println(YELLOW + "you have no ongoing travel!" + RESET);
                printMenu();
            } else {
                registerDriverMenu(accessDriver, accessTravel, accessPassenger, userID);
            }

        } else {
            System.out.println(RED + "user not found!" + RESET);
            int choice = getValidChoice(PURPLE_BRIGHT + "1)add a new driver " +
                    "\n2)enter user id again \n3)back to main menu \nenter your choice:" + RESET, 3);

            switch (choice) {
                case 1: {
                    Driver driver = getDriver(accessCar);
                    accessDriver.save(driver);
                    printMenu();
                }
                break;
                case 2:
                    registerOrLoginDriver(accessDriver, accessTravel, accessPassenger, accessCar);
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

    public static void registerDriver(DaoDriver accessDriver, int userID) throws SQLException {
        String firstName = getValidName("first name: ");
        String lastName = getValidName("last name: ");
        int nationalCode = getValidInt("national code: ");
        String phoneNumber = getValidPhoneNumber("phone number: ");
        accessDriver.updateDriver(userID, firstName, lastName, nationalCode, phoneNumber);
    }

    public static void registerDriverMenu(DaoDriver accessDriver,
                                          DaoTravel accessTravel,
                                          DaoPassenger accessPassenger,
                                          int userID) throws SQLException, ClassNotFoundException {

        int choice = getValidChoice(PURPLE_BRIGHT + "1)register \n2)confirm travel payment" +
                "\n3)finishing travel \n4)back to main menu \nenter your choice: " + RESET, 4);
        switch (choice) {
            case 1: {
                registerDriver(accessDriver, userID);
                registerDriverMenu(accessDriver, accessTravel, accessPassenger, userID);
            }
            break;
            case 2: {
                if (accessTravel.printWaitingForPayment(userID)) {
                    int idTravel = getValidInt("id travel : ");
                    accessTravel.confirmPaymentByCash(idTravel);
                    registerDriverMenu(accessDriver, accessTravel, accessPassenger, userID);
                } else {
                    System.out.println(YELLOW + "there is no payment to confirm!" + RESET);
                    registerDriverMenu(accessDriver, accessTravel, accessPassenger, userID);
                }

            }
            break;
            case 3: {
                finishingTravel(accessPassenger, accessDriver, accessTravel);
                registerDriverMenu(accessDriver, accessTravel, accessPassenger, userID);
            }
            break;
            case 4: {
                printMenu();
            }
            break;
            default: {
                System.out.println("invalid choice!");
            }
        }
    }

    public static void ongoingTravelMenu(DaoTravel accessTravel) throws SQLException, ClassNotFoundException {
        int choice = getValidChoice(PURPLE_BRIGHT + "1))show ongoing travels" +
                "\n2)travel list \n3)Back to main menu \nenter your choice: " + RESET, 3);
        switch (choice) {
            case 1: {
                accessTravel.printOngoingTravels();
                ongoingTravelMenu(accessTravel);
            }
            break;
            case 2: {
                accessTravel.printAllTravel();
                ongoingTravelMenu(accessTravel);
            }
            break;
            case 3: {
                printMenu();
            }
            break;
            default: {
                System.out.println("invalid choice!");
            }
            break;
        }
    }


}
