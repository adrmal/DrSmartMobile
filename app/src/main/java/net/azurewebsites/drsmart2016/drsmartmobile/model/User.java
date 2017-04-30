package net.azurewebsites.drsmart2016.drsmartmobile.model;

import net.azurewebsites.drsmart2016.drsmartmobile.util.Address;
import net.azurewebsites.drsmart2016.drsmartmobile.util.Gender;

import java.util.Date;

import lombok.Data;

@Data
public class User {

    protected String id;
    protected String firstName;
    protected String secondName;
    protected String lastName;
    protected Date dateOfBirth;
    protected Gender gender;
    protected String pesel;
    protected Address address;
    protected String phone;
    protected String email;

}
