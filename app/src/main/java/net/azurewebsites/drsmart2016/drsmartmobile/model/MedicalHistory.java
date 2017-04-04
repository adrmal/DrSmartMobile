package net.azurewebsites.drsmart2016.drsmartmobile.model;

import java.util.List;

import lombok.Data;

@Data
public class MedicalHistory {

    private List<Disease> diseases;

    public void addDisease(Disease disease) {
        diseases.add(disease);
    }

}
