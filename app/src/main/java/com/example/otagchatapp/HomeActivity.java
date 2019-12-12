package com.example.otagchatapp;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.gurtam.wiatagkit.Message;
import com.gurtam.wiatagkit.MessageSender;
import com.gurtam.wiatagkit.MessageSenderListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    RelativeLayout relativeLayout, rlSetting;
    FloatingActionButton txt_message,txt_sos,txt_photo;


    MessageSenderListener messageSenderListener = new MessageSenderListener() {
        @Override
        protected void onSuccess() {
            super.onSuccess();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(HomeActivity.this, "Message Sent", Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        protected void onFailure(final byte errorCode) {
            super.onFailure(errorCode);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String errorMessage;
                    switch (errorCode) {
                        case MessageSenderListener.FAILED_TO_CONNECT:
                            errorMessage = "Could not connect to server. Check connection and connection settings (host, port)";
                            break;
                        case MessageSenderListener.FAILED_TO_SEND:
                            errorMessage = "Packet parsing error";
                            break;
                        case MessageSenderListener.INVALID_UNIQUE_ID:
                            errorMessage = "Unit does not exist on server";
                            break;
                        case MessageSenderListener.INCORRECT_PASSWORD:
                            errorMessage = "Wrong password";
                            break;
                        default:
                            errorMessage = "Failure";
                            break;
                    }
                    Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        }
    };




    static HomeActivity instance;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;

    public static HomeActivity getInstance() {
        return instance;
    }

    TextView txt_speed,txtTime,txt_bearing,txt_satellites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txt_message = findViewById(R.id.fabChat);
        txt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
            }
        });
        txt_sos = findViewById(R.id.fabSos);
        txt_sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageSender.initWithHost("193.193.165.165",20963,"863880041398232","0000");
//
                Message message = new Message().time(new Date().getTime());
                message.Sos();
                MessageSender.sendMessage(message, messageSenderListener);

            }
        });

        txt_photo = findViewById(R.id.fabImage);
        txt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageSender.initWithHost("193.193.165.165",20963,"863880041398232","0000");
//
                Message message = new Message().time(new Date().getTime());
                message.image("wiatag-kit", getBytesFromDrawableBitmap(R.drawable.battery));
                MessageSender.sendMessage(message, messageSenderListener);

            }
        });

        instance = this;
        txt_speed = findViewById(R.id.txt_speed);
        txtTime = findViewById(R.id.txt_time);
        txt_bearing= findViewById(R.id.txt_bearing);
        txt_satellites= findViewById(R.id.txt_satellites);
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            updateLocation();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();




        relativeLayout= findViewById(R.id.menu_connection);
        rlSetting = findViewById(R.id.rl_setting);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ConnectionActivity.class));
            }
        });
        rlSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,SettingActivity.class));

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateLocation() {
        buildLocationRequest();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent());

    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, MyLocationService.class);
        intent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);

        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);

    }

    public void UpdateTextViewSpeed(final double lat , final double lng, final double altitude,final float speed ,final double bearing,final double satellites) {
        HomeActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                MessageSender.initWithHost("193.193.165.165",20963,"863880041398232","0000");
//
                Message message = new Message().time(new Date().getTime());

                txt_speed.setText(""+speed);
                txtTime.setText(""+altitude);
                txt_bearing.setText(""+bearing);
                txt_satellites.setText(""+satellites);

                Log.e("oooo", String.valueOf(lat));
                message.location(new com.gurtam.wiatagkit.Location(lat, lng, altitude,(short) speed, (short) bearing, (byte) satellites));
                MessageSender.sendMessage(message, messageSenderListener);

            }
        });


    }
    /** helper method for getting byte array of Drawable resource*/
    public byte[] getBytesFromDrawableBitmap(@DrawableRes int  drawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),drawableId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        return stream.toByteArray();
    }

    public void UpdateTextViewTime(final String str) {
        HomeActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//               txtTime.setText(str);
            }
        });
    }
    public void UpdateTextView(final String str){
        HomeActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_speed.setText(str);
            }
        });

    }
}
