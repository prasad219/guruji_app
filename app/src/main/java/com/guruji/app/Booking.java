package com.guruji.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Booking extends AppCompatActivity {

    String BookName, BookPrice, BookDiscount, BookDetails, Time, date, Uname, UEmail, sharedloc, status;
    TextView bookingName, bookingPrice, bookingDetails, dateAvail, eventtime, Finaltime, fdate, ftime;
    String address, phoneNo;
    CalendarView calendarView;
    TimePicker timePicker;
    Button button;
    private String userID;
    private FirebaseUser user;
    FirebaseDatabase firebaseDb;
    DatabaseReference databaseReference, requests;
    ImageView imageView;
    public static String FILE_NAME = "Location.txt";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        bookingName = (TextView) findViewById(R.id.BookingName);
        bookingPrice = (TextView) findViewById(R.id.BookingPrice);
        bookingDetails = (TextView) findViewById(R.id.BookingDetails);
        timePicker = (TimePicker) findViewById(R.id.eventimepicker);
        button = (Button) findViewById(R.id.bookingconfirm);
        eventtime= (TextView) findViewById(R.id.EventTime);
        Finaltime= (TextView) findViewById(R.id.Finaldatetime);
        dateAvail = (TextView) findViewById(R.id.dateAvailability);
        ftime = (TextView) findViewById(R.id.Finaltime);
        fdate = (TextView) findViewById(R.id.Finaldate);

        firebaseDb = FirebaseDatabase.getInstance();
        databaseReference = firebaseDb.getReference("Users");
        requests= firebaseDb.getReference("Requests");

        user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        final TextView userName = (TextView) findViewById(R.id.textViewUserName);

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    Uname = userProfile.name;
                    UEmail = userProfile.email;
                    phoneNo = userProfile.phoneNo;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Booking.this, "Something went wrong!", Toast.LENGTH_LONG).show();

            }
        });

        Intent intent = getIntent();


        BookName = intent.getStringExtra("Bookname");
        BookPrice = intent.getStringExtra("BookPricefinal");
        BookDiscount = intent.getStringExtra("BookDiscount");
        BookDetails = intent.getStringExtra("BookDetails");
        Log.i("Booking Price","Price:"+BookPrice);
        Log.i("Booking Details","Details"+BookDetails);
        bookingName.setText(BookName);
        bookingPrice.setText("\u20B9"+BookPrice);
        bookingDetails.setText(BookDetails);
        imageView = (ImageView) findViewById(R.id.imageViewBack);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        calendarView = (CalendarView) findViewById(R.id.CalenderView);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                Time = i + ":" + i1;
                ftime.setText(Time);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = i2 +"/"+ (i1+1)  +"/"+i;
                fdate.setText(date);
                if(i2==7 && (i1+1) == 8){
                    dateAvail.setText("No Mhurat Available");
                    dateAvail.setTextColor(Color.RED);
                    eventtime.setVisibility(View.GONE);
                    timePicker.setVisibility(View.GONE);
                    ftime.setVisibility(View.GONE);
                    fdate.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    Finaltime.setText("Please choose another date!");
                }else if(i2==8 && (i1+1) == 8){
                    dateAvail.setText("No Mhurat Available");
                    dateAvail.setTextColor(Color.RED);
                    eventtime.setVisibility(View.GONE);
                    timePicker.setVisibility(View.GONE);
                    ftime.setVisibility(View.GONE);
                    fdate.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    Finaltime.setText("Please choose another date!");
                }else if(i2==6 && (i1+1) == 9){
                    dateAvail.setText("No Mhurat Available");
                    dateAvail.setTextColor(Color.RED);
                    eventtime.setVisibility(View.GONE);
                    timePicker.setVisibility(View.GONE);
                    ftime.setVisibility(View.GONE);
                    fdate.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    Finaltime.setText("Please choose another date!");
                }else if(i2==5 && (i1+1) == 10){
                    dateAvail.setText("No Mhurat Available");
                    dateAvail.setTextColor(Color.RED);
                    eventtime.setVisibility(View.GONE);
                    ftime.setVisibility(View.GONE);
                    fdate.setVisibility(View.GONE);
                    timePicker.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    Finaltime.setText("Please choose another date!");
                }else if(i2==6 && (i1+1) == 10){
                    dateAvail.setText("No Mhurat Available");
                    dateAvail.setTextColor(Color.RED);
                    eventtime.setVisibility(View.GONE);
                    timePicker.setVisibility(View.GONE);
                    ftime.setVisibility(View.GONE);
                    fdate.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    Finaltime.setText("Please choose another date!");
                }else if(i2==19 && (i1+1) == 10){
                    dateAvail.setText("No Mhurat Available");
                    dateAvail.setTextColor(Color.RED);
                    eventtime.setVisibility(View.GONE);
                    ftime.setVisibility(View.GONE);
                    fdate.setVisibility(View.GONE);
                    timePicker.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    Finaltime.setText("Please choose another date!");
                }else if(i2==4 && (i1+1) == 11){
                    dateAvail.setText("No Mhurat Available");
                    dateAvail.setTextColor(Color.RED);
                    eventtime.setVisibility(View.GONE);
                    ftime.setVisibility(View.GONE);
                    fdate.setVisibility(View.GONE);
                    timePicker.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    Finaltime.setText("Please choose another date!");
                }else if(i2==3 && (i1+1) == 11){
                    dateAvail.setText("No Mhurat Available");
                    dateAvail.setTextColor(Color.RED);
                    eventtime.setVisibility(View.GONE);
                    timePicker.setVisibility(View.GONE);
                    ftime.setVisibility(View.GONE);
                    fdate.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    Finaltime.setText("Please choose another date!");
                }else if(i2==3 && (i1+1) == 12){
                    dateAvail.setText("No Mhurat Available");
                    dateAvail.setTextColor(Color.RED);
                    ftime.setVisibility(View.GONE);
                    fdate.setVisibility(View.GONE);
                    eventtime.setVisibility(View.GONE);
                    timePicker.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    Finaltime.setText("Please choose another date!");

                }else if(i2==4 && (i1+1) == 12){
                    dateAvail.setText("No Mhurat Available");
                    dateAvail.setTextColor(Color.RED);
                    eventtime.setVisibility(View.GONE);
                    ftime.setVisibility(View.GONE);
                    fdate.setVisibility(View.GONE);
                    timePicker.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    Finaltime.setText("Please choose another date!");
                }
                else {
                    dateAvail.setText("Mhurat Available: All day");
                    dateAvail.setTextColor(Color.rgb(75,131,120));
                    eventtime.setVisibility(View.VISIBLE);
                    timePicker.setVisibility(View.VISIBLE);
                    ftime.setVisibility(View.VISIBLE);
                    fdate.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    Finaltime.setText("Date & Time: ");

                }

            }

        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadData();

                if(Time == null && date == null){
                    Toast.makeText(Booking.this, "Please select data and time before proceeding!", Toast.LENGTH_LONG).show();

                    timePicker.requestFocus();
                    calendarView.requestFocus();
                }

                else if (address==null) {
                    Intent intent = new Intent(Booking.this, UserLocation.class);
                    startActivity(intent);

                }
                else{
                    Request request = new Request(UEmail, Uname, BookPrice, BookName, date, Time, address, phoneNo, status);
                    requests.child(String.valueOf(System.currentTimeMillis()))
                            .setValue(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Intent intent = new Intent(Booking.this, Success.class);
                                intent.putExtra("OrderNo",System.currentTimeMillis());
                                intent.putExtra("status","Confirm");
                                intent.putExtra("Prodname",BookName);
                                startActivity(intent);
                                finish();


                            } else {
                                Intent intent = new Intent(Booking.this, fail.class);
                                intent.putExtra("OrderNo",System.currentTimeMillis());
                                intent.putExtra("status","Failed");
                                startActivity(intent);

                            }
                        }
                    });
                }
            }
        });







    }

    public void loadData(){
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br =new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((sharedloc = br.readLine()) != null){
                sb.append(sharedloc).append("\n");
            }
            String sharedFetch = sb.toString();
            if(sharedFetch!=null) {
                address = sharedFetch;
            }else{
                Toast.makeText(getApplicationContext(), "Choose a Location!", Toast.LENGTH_SHORT).show();
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}