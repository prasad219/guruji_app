package com.guruji.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseUser;

public class Success extends AppCompatActivity {

    String status, ordernumber, prod;
    TextView orderno, statustext;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        Intent intent = getIntent();

        ordernumber = intent.getStringExtra("OrderNo");
        status = intent.getStringExtra("status");
        prod= intent.getStringExtra("Prodname");

        statustext = findViewById(R.id.status);
        orderno = findViewById(R.id.orderno);

        lottieAnimationView = findViewById(R.id.lottieAnimationView);
        NotificationManager manager = (NotificationManager)
                getApplicationContext().getSystemService(
                        Context.NOTIFICATION_SERVICE
                );
        String channelId = "channel_id";
        CharSequence channelName = "mychannel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);

            manager.createNotificationChannel(notificationChannel);
        }

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                Success.this,channelId);
        builder.setContentTitle("Order Placed!");
        builder.setContentText("Order Placed for: "+prod);
        builder.setSmallIcon(R.drawable.ic_baseline_notifications_24);
        builder.setSound(uri);
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentIntent(null);
        manager.notify(1,builder.build());


        onStart();


    }

    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                statustext.setText("Order" +status+"!");
                orderno.setText("Order Number:" + ordernumber);
                lottieAnimationView.setAnimation(R.raw.confirm);
                Intent intent = new Intent(Success.this,orderstatus.class);
                startActivity(intent);
                finish();
            }},3000);

    }

}
