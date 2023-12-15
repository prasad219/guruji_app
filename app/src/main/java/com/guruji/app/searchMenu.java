package com.guruji.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.guruji.app.DRVinterface.SubCategoryOnClickInterface;
import com.guruji.app.HelperClassses.HomeAdapter.Category;
import com.guruji.app.HelperClassses.HomeAdapter.CategoryTwo;
import com.guruji.app.HelperClassses.HomeAdapter.CategoryTwoViewHolder;
import com.guruji.app.HelperClassses.HomeAdapter.CategoryViewHolder;

public class searchMenu extends AppCompatActivity {

    String pujasubname, pujaDescription,pujaImportance,pujaPrice,pujaDiscount;


    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;
    ImageView imageView;

    FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;
    FirebaseRecyclerAdapter<CategoryTwo, CategoryTwoViewHolder> adapter2;
    RecyclerView.LayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_menu);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("SearchMenu");

        manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView = findViewById(R.id.recyclerViewSearch);
        recyclerView.setLayoutManager(manager);

        imageView = (ImageView) findViewById(R.id.imageViewBack);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SearchView searchView = findViewById(R.id.searchMenuBox);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);

                return false;
            }
        });

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(reference,Category.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i, @NonNull final Category category) {

                categoryViewHolder.categoryName.setText(category.getCategoryName());
                FirebaseRecyclerOptions<CategoryTwo> options2 = new FirebaseRecyclerOptions.Builder<CategoryTwo>()
                        .setQuery(reference.child(category.getCategoryId()).child("data"),CategoryTwo.class)
                        .build();

                adapter2 = new FirebaseRecyclerAdapter<CategoryTwo, CategoryTwoViewHolder>(options2) {
                    @Override
                    protected void onBindViewHolder(@NonNull CategoryTwoViewHolder categoryTwoViewHolder, int i, @NonNull final CategoryTwo categoryTwo) {
                        categoryTwoViewHolder.dataId.setText(categoryTwo.getDataDesc());
                        categoryTwoViewHolder.dataName.setText(categoryTwo.getDataName());

                        categoryTwoViewHolder.SubCategoryInterfaceClick(new SubCategoryOnClickInterface() {
                            @Override
                            public void onClick(View view, boolean isLongPressed) {
                                Intent intent = new Intent(searchMenu.this, pujainfo.class);
                                intent.putExtra("userId",categoryTwo.getDataId());
                                intent.putExtra("userName",categoryTwo.getDataName());
                                intent.putExtra("userAge",categoryTwo.getDataAge());
                                intent.putExtra("userDesc",categoryTwo.getDataDesc());
                                intent.putExtra("userImp",categoryTwo.getDataImp());
                                intent.putExtra("userPrice",categoryTwo.getDataPrice());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CategoryTwoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v2 = LayoutInflater.from(searchMenu.this)
                                .inflate(R.layout.item_view_two,parent,false);
                        return new CategoryTwoViewHolder(v2);
                    }
                };
                adapter2.startListening();
                adapter2.notifyDataSetChanged();
                categoryViewHolder.category_recyclerView.setAdapter(adapter2);
            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v1 = LayoutInflater.from(searchMenu.this)
                        .inflate(R.layout.item_view_one,parent,false);
                return new CategoryViewHolder(v1);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);



    }

    private void processSearch(String s) {

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(reference,Category.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i, @NonNull final Category category) {

                categoryViewHolder.categoryName.setText(category.getCategoryName());
                FirebaseRecyclerOptions<CategoryTwo> options2 = new FirebaseRecyclerOptions.Builder<CategoryTwo>()
                        .setQuery(reference.child(category.getCategoryId()).child("data").orderByChild("dataName").startAt(s).endAt(s+"\uf8ff"),CategoryTwo.class)
                        .build();

                adapter2 = new FirebaseRecyclerAdapter<CategoryTwo, CategoryTwoViewHolder>(options2) {
                    @Override
                    protected void onBindViewHolder(@NonNull CategoryTwoViewHolder categoryTwoViewHolder, int i, @NonNull final CategoryTwo categoryTwo) {
                        categoryTwoViewHolder.dataId.setText(categoryTwo.getDataDesc());
                        categoryTwoViewHolder.dataName.setText(categoryTwo.getDataName());

                        categoryTwoViewHolder.SubCategoryInterfaceClick(new SubCategoryOnClickInterface() {
                            @Override
                            public void onClick(View view, boolean isLongPressed) {
                                Intent intent = new Intent(searchMenu.this, pujainfo.class);
                                intent.putExtra("userId",categoryTwo.getDataId());
                                intent.putExtra("userName",categoryTwo.getDataName());
                                intent.putExtra("userAge",categoryTwo.getDataAge());
                                intent.putExtra("userDesc",categoryTwo.getDataDesc());
                                intent.putExtra("userImp",categoryTwo.getDataImp());
                                intent.putExtra("userPrice",categoryTwo.getDataPrice());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public CategoryTwoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v2 = LayoutInflater.from(searchMenu.this)
                                .inflate(R.layout.item_view_two,parent,false);
                        return new CategoryTwoViewHolder(v2);
                    }
                };
                adapter2.startListening();
                adapter2.notifyDataSetChanged();
                categoryViewHolder.category_recyclerView.setAdapter(adapter2);
            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v1 = LayoutInflater.from(searchMenu.this)
                        .inflate(R.layout.item_view_one,parent,false);
                return new CategoryViewHolder(v1);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}