package com.example.otagchatapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;

public class MyLocationService extends BroadcastReceiver {
    public static final String ACTION_PROCESS_UPDATE="com.example.otagchatapp.UPDATE_LOCATION";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent !=null){
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATE.equals(action)){
                LocationResult result =LocationResult.extractResult(intent);
                if (result !=null){
                    Location location= result.getLastLocation();
//                    String Location_String =new StringBuilder(""+location.getLatitude())
//                            .append(location.getSpeed())
//                            .toString();
                    double Location_lat = location.getLatitude();
                    double Location_lng = location.getLongitude();
                    double altitude = location.getAltitude();
                    double bearing = location.getBearing();
                    int speed = Math.round(location.getSpeed());
                    double satellite = location.getAccuracy();

                    String Locaion_Time = new StringBuilder(""+location.getTime())
                            .toString();
                    try {
                        HomeActivity.getInstance().UpdateTextViewSpeed(Location_lat,Location_lng,altitude,speed,bearing,satellite);
//                        HomeActivity.getInstance().UpdateTextViewTime(Locaion_Time);
                    }catch (Exception ex){
                        Toast.makeText(context, (int) Location_lat,Toast.LENGTH_LONG).show();

                    }
                }
            }
        }

    }
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
}
