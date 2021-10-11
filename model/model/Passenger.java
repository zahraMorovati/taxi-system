package model;

import myDate.MyDate;

public class Passenger extends Person{

    private double balance;
    private boolean status;

    public Passenger(String firstName, String lastName, int nationalCode, String phoneNumber, MyDate birthDate, double balance, boolean status) {
        super(firstName, lastName, nationalCode, phoneNumber, birthDate);
        this.balance = balance;
        this.status = status;
    }

    public Passenger(int userID,String firstName, String lastName, int nationalCode, String phoneNumber, MyDate birthDate, double balance, boolean status) {
        super(userID,firstName,lastName,nationalCode,phoneNumber,birthDate);
        this.balance = balance;
        this.status = status;
    }
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isStatus() {
        return status;
    }

    public int getStatus() {
        if(status)
            return 1;
        else return 0;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString()+", balance=" + balance + ", status=" + status ;
    }
}
