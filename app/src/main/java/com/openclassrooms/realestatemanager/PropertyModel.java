package com.openclassrooms.realestatemanager;


public class PropertyModel {
    private String propertyName;
    private double propertyPrice;
    private String availabilityDate;

    public PropertyModel(String propertyName, double propertyPrice, String availabilityDate) {
        this.propertyName = propertyName;
        this.propertyPrice = propertyPrice;
        this.availabilityDate = availabilityDate;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public double getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(double propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(String availabilityDate) {
        this.availabilityDate = availabilityDate;
    }
}