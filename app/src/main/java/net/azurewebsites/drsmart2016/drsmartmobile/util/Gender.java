package net.azurewebsites.drsmart2016.drsmartmobile.util;

public enum Gender {
    MALE,
    FEMALE;

    @Override
    public String toString() {
        if(this == MALE) {
            return "mężczyzna";
        }
        return "kobieta";
    }
}
