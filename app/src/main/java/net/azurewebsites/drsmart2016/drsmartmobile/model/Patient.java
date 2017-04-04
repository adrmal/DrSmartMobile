package net.azurewebsites.drsmart2016.drsmartmobile.model;

import lombok.Data;

@Data
public class Patient extends User {

    private MedicalHistory medicalHistory;

}
