package net.azurewebsites.drsmart2016.drsmartmobile.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Specialty {

    @SerializedName("SpecialtyId")
    private String id;
    @SerializedName("Name")
    private String name;

}
