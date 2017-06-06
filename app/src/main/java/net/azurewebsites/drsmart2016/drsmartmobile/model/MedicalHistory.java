package net.azurewebsites.drsmart2016.drsmartmobile.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class MedicalHistory {

    private List<Record> records;

    public MedicalHistory(Record[] records) {
        this.records = new ArrayList<>(Arrays.asList(records));
    }

}
