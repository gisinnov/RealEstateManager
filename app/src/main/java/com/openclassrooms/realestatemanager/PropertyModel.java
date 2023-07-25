package com.openclassrooms.realestatemanager;

public class PropertyModel {
    private String propertyName;
    private double propertyPrice;
    private String availabilityDate;
    private String estateType;
    private String rooms;
    private String bedrooms;
    private String bathrooms;
    private String agent;
    private String description;
    private String status;
    private double propertyArea;
    private String propertyAddress;
    private boolean isSchoolNearby;
    private boolean areShopsNearby;
    private boolean isParkNearby;

    public PropertyModel(String propertyName, double propertyPrice, String availabilityDate,
                         String estateType, String rooms, String bedrooms, String bathrooms,
                         String agent, String description, String status, double propertyArea,
                         String propertyAddress, boolean isSchoolNearby, boolean areShopsNearby,
                         boolean isParkNearby) {
        this.propertyName = propertyName;
        this.propertyPrice = propertyPrice;
        this.availabilityDate = availabilityDate;
        this.estateType = estateType;
        this.rooms = rooms;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.agent = agent;
        this.description = description;
        this.status = status;
        this.propertyArea = propertyArea;
        this.propertyAddress = propertyAddress;
        this.isSchoolNearby = isSchoolNearby;
        this.areShopsNearby = areShopsNearby;
        this.isParkNearby = isParkNearby;
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

    public String getEstateType() {
        return estateType;
    }

    public void setEstateType(String estateType) {
        this.estateType = estateType;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPropertyArea() {
        return propertyArea;
    }

    public void setPropertyArea(double propertyArea) {
        this.propertyArea = propertyArea;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public boolean isSchoolNearby() {
        return isSchoolNearby;
    }

    public void setSchoolNearby(boolean schoolNearby) {
        isSchoolNearby = schoolNearby;
    }

    public boolean isShopsNearby() {
        return areShopsNearby;
    }

    public void setShopsNearby(boolean shopsNearby) {
        areShopsNearby = shopsNearby;
    }

    public boolean isParkNearby() {
        return isParkNearby;
    }

    public void setParkNearby(boolean parkNearby) {
        isParkNearby = parkNearby;
    }
}
