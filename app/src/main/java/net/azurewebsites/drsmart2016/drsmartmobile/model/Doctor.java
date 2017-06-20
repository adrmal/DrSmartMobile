package net.azurewebsites.drsmart2016.drsmartmobile.model;

import java.util.List;

import lombok.Data;

@Data
public class Doctor extends User {

    private List<Specialty> specialties;

}
