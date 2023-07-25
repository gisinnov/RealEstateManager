package com.openclassrooms.realestatemanager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

public class PermissionUtils {
    public static boolean checkLocationPermission(Context context) {
        String fineLocationPermission = Manifest.permission.ACCESS_FINE_LOCATION;
        String coarseLocationPermission = Manifest.permission.ACCESS_COARSE_LOCATION;

        int fineLocationPermissionResult = ContextCompat.checkSelfPermission(context, fineLocationPermission);
        int coarseLocationPermissionResult = ContextCompat.checkSelfPermission(context, coarseLocationPermission);

        return fineLocationPermissionResult == PackageManager.PERMISSION_GRANTED && coarseLocationPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean didUserGrantPermissions(int[] grantResults) {
        if (grantResults.length > 0) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
