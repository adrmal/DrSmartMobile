package net.azurewebsites.drsmart2016.drsmartmobile.model;

import java.util.Date;

import lombok.Data;

@Data
public class Visit {

    private String id;
    private String patientId;
    private String doctorId;
    private Date date;

}
