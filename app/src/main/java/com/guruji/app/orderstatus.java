package com.guruji.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.guruji.app.Common.Common;
import com.guruji.app.HelperClassses.HomeAdapter.OrderViewHolder;
import com.guruji.app.HelperClassses.HomeAdapter.orderHelperClass;


import org.jetbrains.annotations.NotNull;

public class orderstatus extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference requests, databaseReference;
    public String userID, Uname, UEmail, phoneNo;
    public FirebaseUser user;
    ImageView imageView;


    public RecyclerView recyclerViewOrder;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> firebaseRecyclerAdapter;

    public RecyclerView recyclerView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderstatus);


        imageView = (ImageView) findViewById(R.id.imageViewBack);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");


        recyclerViewOrder= findViewById(R.id.listOrders);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewOrder.setLayoutManager(layoutManager);


        loadOrders(Common.currentuser.getEmail());




    }

    private void loadOrders(String UEmail) {



        FirebaseRecyclerOptions<Request> options = new FirebaseRecyclerOptions.Builder<Request>()
                .setQuery(requests.orderByChild("email").equalTo(UEmail), Request.class)
                .build();

        Log.i("Orderlistcalled","Order list called");

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull OrderViewHolder holder, int position, @NonNull @NotNull Request model) {
                holder.txtOrderId.setText(firebaseRecyclerAdapter.getRef(position).getKey());
                holder.txtOrderStatus.setText(convertCodeToStatus(model.status));
                holder.txtOrderAddress.setText(model.address);
                holder.txtOrderName.setText(model.prodName);

            }

            @NonNull
            @NotNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(orderstatus.this)
                        .inflate(R.layout.orderlayout, parent, false);
                return new OrderViewHolder(view);
            }
        };

        recyclerViewOrder.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private String convertCodeToStatus(String status) {
        if(status.equals("0")){
            return "Order Placed";

        }
        else if(status.equals("1")){
            return "Guruji on their way";

        }else
            return "Order Completed";
    }
}