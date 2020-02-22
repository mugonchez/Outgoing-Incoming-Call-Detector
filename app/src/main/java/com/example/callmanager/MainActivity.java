package com.example.callmanager;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    private static final int MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS = 2;
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    private Button dialBtn;
    private TextView emailView, phoneView;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailView = findViewById(R.id.email);
        phoneView = findViewById(R.id.phone_number);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Account");

        if (android.os.Build.VERSION.SDK_INT >= 23){
            if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission has not been granted, therefore prompt the user to grant permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }


            if (getApplicationContext().checkSelfPermission(Manifest.permission.PROCESS_OUTGOING_CALLS)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission has not been granted, therefore prompt the user to grant permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS},
                        MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS);
            }
        }

        dialBtn = findViewById(R.id.button);
        dialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL_BUTTON, null);
                startActivity(intent);
//                DatabaseReference newRef = databaseReference.push();
////                        newRef.child("email").setValue("moses@gmail.com");
////                        newRef.child("phone_number").setValue("0765417193");
//                HashMap map = new HashMap();
//                map.put("email", "moses@gmail.com");
//                map.put("phone_number", "0765417193");
//                newRef.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if(task.isSuccessful()){
//                            Intent intent = new Intent(Intent.ACTION_CALL_BUTTON, null);
//                            startActivity(intent);
//                        }else{
//                            Toast.makeText(getApplicationContext(), "Not successful", Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//                });
            }
        });

        Intent intent = getIntent();
        String phone_number = intent.getStringExtra("PHONE_NUMBER");
        String email = intent.getStringExtra("EMAIL");
        phoneView.setText(phone_number);
        emailView.setText(email);





    }


//    public void checkPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.canDrawOverlays(this)) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
//            }
//        }
//    }
}
