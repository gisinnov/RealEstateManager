
package com.openclassrooms.realestatemanager;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "properties")
public class Property implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String propertyName;
    private double propertyPrice;
    private String propertyType;
    private String propertyDescription;
    private double propertySurface;
    private String numberOfRooms;
    private String numberOfBedrooms;
    private String numberOfBathrooms;
    private String propertyAddress;
    private String propertyTown;
    private String postalCode;
    private String pointsOfInterest;
    private String saleDate;
    private String saleStatus;
    private String agentName;

    public Property() {
        // Default constructor required by Room
    }

    public Property(String propertyName, double propertyPrice, String availabilityDate, String estateType,
                    String numberOfRooms, String numberOfBedrooms, String numberOfBathrooms, String agentName,
                    String propertyDescription, String saleStatus, double propertySurface, String propertyAddress,
                    boolean isSchoolNearby, boolean areShopsNearby, boolean isParkNearby, String propertyTown, String postalCode) {
        this.propertyName = propertyName;
        this.propertyPrice = propertyPrice;
        this.propertyType = estateType;
        this.propertyDescription = propertyDescription;
        this.propertySurface = propertySurface;
        this.numberOfRooms = numberOfRooms;
        this.numberOfBedrooms = numberOfBedrooms;
        this.numberOfBathrooms = numberOfBathrooms;
        this.propertyAddress = propertyAddress;
        this.propertyTown = propertyTown;
        this.postalCode = postalCode;
        this.pointsOfInterest = pointsOfInterest;
        this.saleDate = saleDate;
        this.saleStatus = saleStatus;
        this.agentName = agentName;
    }

    // Getters and setters for each attribute...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPropertyName() { return propertyName; }
    public void setPropertyName(String propertyName) { this.propertyName = propertyName; }

    public double getPropertyPrice() { return propertyPrice; }
    public void setPropertyPrice(double propertyPrice) { this.propertyPrice = propertyPrice; }

    public String getPropertyType() { return propertyType; }
    public void setPropertyType(String propertyType) { this.propertyType = propertyType; }

    public String getPropertyDescription() { return propertyDescription; }
    public void setPropertyDescription(String propertyDescription) { this.propertyDescription = propertyDescription; }

    public double getPropertySurface() { return propertySurface; }
    public void setPropertySurface(double propertySurface) { this.propertySurface = propertySurface; }

    public String getNumberOfRooms() { return numberOfRooms; }
    public void setNumberOfRooms(String numberOfRooms) { this.numberOfRooms = numberOfRooms; }

    public String getNumberOfBedrooms() { return numberOfBedrooms; }
    public void setNumberOfBedrooms(String numberOfBedrooms) { this.numberOfBedrooms = numberOfBedrooms; }

    public String getNumberOfBathrooms() { return numberOfBathrooms; }
    public void setNumberOfBathrooms(String numberOfBathrooms) { this.numberOfBathrooms = numberOfBathrooms; }

    public String getPropertyAddress() { return propertyAddress; }
    public void setPropertyAddress(String propertyAddress) { this.propertyAddress = propertyAddress; }

    public String getPropertyTown() { return propertyTown; }
    public void setPropertyTown(String propertyTown) { this.propertyTown = propertyTown; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getPointsOfInterest() { return pointsOfInterest; }
    public void setPointsOfInterest(String pointsOfInterest) { this.pointsOfInterest = pointsOfInterest; }

    public String getSaleDate() { return saleDate; }
    public void setSaleDate(String saleDate) { this.saleDate = saleDate; }

    public String getSaleStatus() { return saleStatus; }
    public void setSaleStatus(String saleStatus) { this.saleStatus = saleStatus; }

    public String getAgentName() { return agentName; }
    public void setAgentName(String agentName) { this.agentName = agentName; }

}
