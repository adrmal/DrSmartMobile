package net.azurewebsites.drsmart2016.drsmartmobile.model;

import com.google.gson.annotations.SerializedName;

import net.azurewebsites.drsmart2016.drsmartmobile.util.Gender;

import java.util.Date;

import lombok.Data;

@Data
public class User {

    @SerializedName("UserInfoId")
    protected String id;
    @SerializedName("FirstName")
    protected String firstName;
    @SerializedName("SecondName")
    protected String secondName;
    @SerializedName("LastName")
    protected String lastName;
    @SerializedName("Pesel")
    protected String pesel;
    @SerializedName("DateOfBirth")
    protected Date dateOfBirth;
    @SerializedName("Gender")
    protected Gender gender;
    @SerializedName("City")
    protected String city;
    @SerializedName("ZipCode")
    protected String zipCode;
    @SerializedName("Province")
    protected String province;
    @SerializedName("Address")
    protected String address;
    @SerializedName("Locked")
    protected boolean isLocked;

}
