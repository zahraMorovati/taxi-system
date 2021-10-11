package validData;

import model.CarType;
import model.VehicleColor;
import myDate.MyDate;

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


}
