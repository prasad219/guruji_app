package com.guruji.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.guruji.app.Common.Common;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    static final float END_SCALE = 0.7f;
    private static final String TAG = Home.class.getSimpleName();

    private FirebaseUser user;
    private DatabaseReference reference;
    private Button logout;
    private String userID;
    ImageView imageView;
    LinearLayout contentView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView LocationHome;
    RelativeLayout LocationViewadd;
    String loc, homeaddress = "Select Location",email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        Map<String, Object> remoteConfigDefaults = new HashMap();
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_REQUIRED, false);
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_CURRENT_VERSION, "1.0.4");
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_URL,
                "https://play.google.com/store/apps/details?id=com.guruji.app");
        
        firebaseRemoteConfig.setDefaultsAsync(remoteConfigDefaults);
        firebaseRemoteConfig.fetch(10) // fetch every minutes
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            firebaseRemoteConfig.activate();

                        }else{

                        }
                    }
                });

        ForceUpdateChecker.with(this).onUpdateNeeded(this::onUpdateNeeded).check();


        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        imageView = findViewById(R.id.userlogo);
        contentView = findViewById(R.id.content);
        LocationHome = findViewById(R.id.LocationHome);



        navigationDrawer();


        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Intent intent = getIntent();

        homeaddress = intent.getStringExtra("Address");

        getSupportFragmentManager().beginTransaction().add(R.id.homecontainer, homefragment.newInstance(homeaddress,"location"),"homefragment").commit();


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView userName = (TextView) findViewById(R.id.textViewUserName);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                Common.currentuser = userProfile;
                if(userProfile != null){
                    String name = userProfile.name;

                    userName.setText("Hello " + name);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Home.this, "Something went wrong!", Toast.LENGTH_LONG).show();

            }
        });




    }
    private void redirectStore(String updateUrl) {
        final Intent intentabc = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intentabc.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentabc);
    }
    public void onUpdateNeeded(final String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New version available")
                .setMessage("Since the app is getting better and better everyday we request you to please, update app to new version for a better experience.")
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redirectStore(updateUrl);
                            }
                        }).setNegativeButton("No, thanks",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create();
        dialog.show();
    }

    private void navigationDrawer() {

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.homemenu);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else{
                    drawerLayout.openDrawer(GravityCompat.START);

                }
            }
        });

        animateNavigationDrawer();


    }

    private void animateNavigationDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.lightyellow));

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                        Fragment fragmentselect = null;
                    switch (item.getItemId()){
                        case R.id.home_nav:
                            fragmentselect = new homefragment();

                            break;
                        case R.id.puja_nav:
                            fragmentselect = new pujafragment();
                            break;
                        case R.id.wedding_nav:
                            fragmentselect = new weddingfragment();
                            break;
                        case R.id.astro_nav:
                            fragmentselect = new astrofragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.homecontainer, fragmentselect).commit();


                    return true;
                }
            };

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

        int id = item.getItemId();
        if(id==R.id.Logoutmenu){
            FirebaseAuth.getInstance().signOut();
            Intent intentLogout = new Intent(Home.this, Login.class);

            startActivity(intentLogout);
            finish();

        }
        if(id==R.id.ProfileMenu){

            Intent intentLogout = new Intent(Home.this, Profile.class);
            startActivity(intentLogout);


        }

        if(id==R.id.recentOrder){

            Intent intent = new Intent(Home.this,orderstatus.class);
            startActivity(intent);


        }





        return true;
    }
}