package com.example.callmanager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneStateReceiver extends BroadcastReceiver {
    private static final String TAG = "Home";
    private DatabaseReference databaseReference;

    @Override
    public void onReceive(final Context context, Intent intent) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Account");

        try {
            System.out.println("Receiver start");
            Log.d(TAG, "onReceive: receiving");
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            final String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if (state.equals("android.intent.action.NEW_OUTGOING_CALL")) {
                String savedNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                Toast.makeText(context,"Ringing State Number is -"+savedNumber,Toast.LENGTH_SHORT).show();
            }
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                Toast.makeText(context,"Incoming Call State",Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"Ringing State Number is -"+incomingNumber,Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Query query = databaseReference.orderByChild("phone_number").equalTo(incomingNumber);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                                        String mail = (String)snapshot.child("email").getValue();
                                        Intent intent1 = new Intent(context, MainActivity.class);
                                        intent1.putExtra("PHONE_NUMBER",incomingNumber);
                                        intent1.putExtra("EMAIL", mail);
                                        context.startActivity(intent1);
                                    }
                                }else {
                                    Toast toast = Toast.makeText(context,"Sorry We couldn\'t find this user in our database", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                                    View view = toast.getView();
                                    view.getBackground().setColorFilter(Color.parseColor("#EF5350"), PorterDuff.Mode.SRC_IN);
                                    TextView textView = view.findViewById(android.R.id.message);
                                    textView.setTextColor(Color.WHITE);
                                    toast.show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast toast = Toast.makeText(context,"Error due to "+ databaseError.getMessage(), Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                                View view = toast.getView();
                                view.getBackground().setColorFilter(Color.parseColor("#EF5350"), PorterDuff.Mode.SRC_IN);
                                TextView textView = view.findViewById(android.R.id.message);
                                textView.setTextColor(Color.WHITE);
                                toast.show();

                            }
                        });

                    }
                }, 1000);
            }
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
                Toast.makeText(context,"Call Received State",Toast.LENGTH_SHORT).show();
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                Toast.makeText(context,"Call Idle State",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
