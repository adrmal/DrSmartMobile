package net.azurewebsites.drsmart2016.drsmartmobile.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class Visit implements Serializable {

    @SerializedName("VisitId")
    private String id;
    @SerializedName("PatientId")
    private String patientId;
    @SerializedName("DoctorId")
    private String doctorId;
    @SerializedName("DoctorName")
    private String doctorFullName;
    @SerializedName("SpecialityId")
    private String doctorSpecialtyId;
    @SerializedName("SpecialityName")
    private String doctorSpecialtyName;
    @SerializedName("Data")
    private Date date;
    @SerializedName("Status")
    private String status;
    @SerializedName("Type")
    private String type;

    public Visit() {
        this.id = UUID.randomUUID().toString();
    }

}
