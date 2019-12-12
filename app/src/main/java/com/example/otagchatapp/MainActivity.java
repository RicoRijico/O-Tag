package com.example.otagchatapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gurtam.wiatagkit.Location;
import com.gurtam.wiatagkit.Message;
import com.gurtam.wiatagkit.MessageSender;
import com.gurtam.wiatagkit.MessageSenderListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText userInput;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;
    List<ResponseMessage> responseMessageList;
    private TelephonyManager mTelephonyManager;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    private static final int IMAGE_PCIK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    ImageView imgview ;
    MessageSenderListener messageSenderListener = new MessageSenderListener() {
        @Override
        protected void onSuccess() {
            super.onSuccess();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "Message Sent", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSIONS_REQUEST_READ_PHONE_STATE);
        } else {
            getDeviceImei();
        }


        userInput = findViewById( R.id.userInput);
        recyclerView = findViewById(R.id.conversation);
        responseMessageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(responseMessageList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(messageAdapter);
        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent event) {

                if (i == EditorInfo.IME_ACTION_SEND) {
                    ResponseMessage responseMessage = new ResponseMessage(userInput.getText().toString(), true);

                    responseMessageList.add(responseMessage);
                    MessageSender.initWithHost("193.193.165.165",20963,"863880041398232","0000");

                    Message message = new Message().time(new Date().getTime());
                    message.text(String.valueOf(userInput.getText()));
                    message.location(new Location(11.5355757, 104.9140025, 290, 2F, (short) 15, (byte) 8));

                    MessageSender.sendMessage(message, messageSenderListener);

                    userInput.getText().clear();

//                    ResponseMessage responseMessage2 = new ResponseMessage(userInput.getText().toString(), false);
//                    responseMessageList.add(responseMessage2);
//                    messageAdapter.notifyDataSetChanged();
//                    if (!isLastVisible())
//                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                }
                return false;
            }


        });
    }

    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = recyclerView.getAdapter().getItemCount();
        return (pos >= numItems);
    }
    private String getDeviceImei() {

        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return null;
            }
        }
        String deviceid = mTelephonyManager.getDeviceId();
        Log.d("msg", "DeviceImei " + deviceid);
        return deviceid;
    }
    private void pickImageFromGallery() {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,IMAGE_PCIK_CODE);

        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Message message = new Message().time(new Date().getTime());
        Uri path = data.getData();
        Bitmap src=BitmapFactory.decodeFile("image/*");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.PNG, 100, baos);
        if (resultCode==RESULT_OK && requestCode==IMAGE_PCIK_CODE){
//            message.image(message.image("wiatag-kit",src);
            Log.e("ooo", String.valueOf(data.getData()));
            imgview.setImageURI(data.getData());

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_DENIED){
                    pickImageFromGallery();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
