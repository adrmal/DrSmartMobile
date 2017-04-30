package net.azurewebsites.drsmart2016.drsmartmobile.model;

import lombok.Data;

@Data
public class Record {

    private String id;
    private String doctorId;
    private String date;
    private String diagnosis;
    private String comment;
    private String examination;
    private String information;
    private String recommendation;

}
