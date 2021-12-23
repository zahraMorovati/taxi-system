package model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;
    private String firstName;
    private String lastName;
    private int nationalCode;
    private String phoneNumber;
    private Date birthDate;

    public String getFullName() {
        return firstName+" "+lastName;
    }

    @Override
    public String toString() {
        return "userID= "+getUserID()+ " , name= "+getFullName()+
                " , phone number= "+getPhoneNumber();

    }

}
