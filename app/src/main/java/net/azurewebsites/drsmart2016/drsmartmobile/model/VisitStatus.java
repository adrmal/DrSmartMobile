package net.azurewebsites.drsmart2016.drsmartmobile.model;

public enum VisitStatus {
    ACTIVE("Aktywna"),
    INACTIVE("Nieaktywna"),
    CANCELED("Anulowana");

    private String textValue;

    VisitStatus(String textValue) {
        this.textValue = textValue;
    }

    public String getTextValue() {
        return textValue;
    }

}
