package model;

import myDate.MyDate;

public class Person {
    private int userID;
    private String firstName;
    private String lastName;
    private int nationalCode;
    private String phoneNumber;
    private MyDate birthDate;


    public Person(String firstName, String lastName, int nationalCode, String phoneNumber, MyDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public Person(int userID, String firstName, String lastName, int nationalCode, String phoneNumber, MyDate birthDate) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public MyDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(MyDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(int nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAge() {
        int currentYear=MyDate.getCurrentDate().getYear();
        return (currentYear-(birthDate.getYear()));
    }

    public String getFullName() {
        return firstName+" "+lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public String toString() {
        return "userID= "+getUserID()+ " , name= "+getFullName()+
                " , age= "+getAge()+
                " , phone number= "+getPhoneNumber();

    }
}
