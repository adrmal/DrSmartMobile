package net.azurewebsites.drsmart2016.drsmartmobile.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Record implements Serializable {

    @SerializedName("MedicalHistoryId")
    private String id;
    @SerializedName("PatientId")
    private String patientId;
    @SerializedName("DoctorId")
    private String doctorId;
    @SerializedName("Data")
    private Date date;
    @SerializedName("Diagnosis")
    private String diagnosis;
    @SerializedName("Comments")
    private String comments;
    @SerializedName("Examinations")
    private String examinations;
    @SerializedName("Information")
    private String information;
    @SerializedName("Recommendations")
    private String recommendations;

}
