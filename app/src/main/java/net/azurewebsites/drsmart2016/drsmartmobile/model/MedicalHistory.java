package net.azurewebsites.drsmart2016.drsmartmobile.model;

import java.util.List;

import lombok.Data;

@Data
public class MedicalHistory {

    private String patientId;
    private List<Record> records;

    public void addRecord(Record record) {
        records.add(record);
    }

}
