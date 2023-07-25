package com.openclassrooms.realestatemanager;

import android.provider.BaseColumns;

public class PropertyContract {
    private PropertyContract() {
        // Private constructor to prevent instantiation
    }

    // Inner class that defines the table contents
    public static class PropertyEntry implements BaseColumns {
        // Table name
        public static final String TABLE_NAME = "Property";

        // Column names
        public static final String COLUMN_PROPERTY_NAME = "property_name";
        public static final String COLUMN_PROPERTY_PRICE = "property_price";
        public static final String COLUMN_AVAILABILITY_DATE = "availability_date";

        // Add the new column names for the property details
        public static final String COLUMN_ESTATE_TYPE = "estateType";
        public static final String COLUMN_ROOMS = "rooms";
        public static final String COLUMN_BEDROOMS = "bedrooms";
        public static final String COLUMN_BATHROOMS = "bathrooms";
        public static final String COLUMN_AGENT = "agent";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_STATUS = "status";

        // Add the new columns for the new property details
        public static final String COLUMN_PROPERTY_AREA = "property_area";
        public static final String COLUMN_PROPERTY_ADDRESS = "property_address";
        public static final String COLUMN_IS_SCHOOL_NEARBY = "is_school_nearby";
        public static final String COLUMN_ARE_SHOPS_NEARBY = "are_shops_nearby";
        public static final String COLUMN_IS_PARK_NEARBY = "is_park_nearby";
    }
}
