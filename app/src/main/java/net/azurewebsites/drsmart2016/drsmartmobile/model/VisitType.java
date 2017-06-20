package net.azurewebsites.drsmart2016.drsmartmobile.model;

public enum VisitType {
    CONSULTATION("Konsultacja"),
    EXAMINATION("Badanie");

    private String textValue;

    VisitType(String textValue) {
        this.textValue = textValue;
    }

    public String getTextValue() {
        return textValue;
    }

}
