package com.guruji.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.guruji.app.DRVinterface.SubCategoryOnClickInterface;
import com.guruji.app.HelperClassses.HomeAdapter.CategoryTwo;
import com.guruji.app.HelperClassses.HomeAdapter.CategoryTwoViewHolder;
import com.guruji.app.HelperClassses.HomeAdapter.astroAdapter;
import com.guruji.app.HelperClassses.HomeAdapter.astroCardHelperClass;
import com.guruji.app.HelperClassses.HomeAdapter.weddingAdapter;
import com.guruji.app.HelperClassses.HomeAdapter.weddingHelperClass;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homefragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    public String mParam1;
    public String mParam2;
    String pujasubname, pujaDescription,pujaImportance,pujaPrice,pujaDiscount, sharedloc;
    RecyclerView recyclerViewPuja, recyclerViewWedding, recyclerViewAstro;
    DatabaseReference pujas, addresses;


    public static String SHARED_PREFS = "sharedPrefs";
    weddingAdapter weddingAdapter;
    astroAdapter astroAdapter;
    Fragment fragmentselect = null;
    TextView locationhome ;
    public static String FILE_NAME = "Location.txt";
    FirebaseDatabase database;
    public RecyclerView recyclerViewPujas;

    FirebaseRecyclerAdapter<CategoryTwo, CategoryTwoViewHolder> firebaseRecyclerAdapter;
    RecyclerView.LayoutManager manager;


    ArrayList<weddingHelperClass> weddingCardList;

    ArrayList<astroCardHelperClass> astroCard;


    public homefragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homefragment newInstance(String param1, String param2) {
        homefragment fragment = new homefragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    public void loadData(){
        FileInputStream fis = null;
        try {
            fis = getActivity().openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br =new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((sharedloc = br.readLine()) != null){
                sb.append(sharedloc).append("\n");
            }
            String sharedFetch = sb.toString();
            String[] separated = sharedFetch.split(",");
            int len = separated.length;
            String userCity = separated[len-3].trim();
            String avcity = "Florida";

            if(userCity.equals(avcity)){
                fragmentselect = new fragmentnonaddress();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homecontainer, fragmentselect).addToBackStack(null).commit();
            }

            if(sharedFetch!=null) {

                locationhome.setText(sharedFetch);
            }else{
                locationhome.setText("Select Address");
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homefragment, container, false);
        recyclerViewPuja = view.findViewById(R.id.pujaRecycler);
        recyclerViewWedding = view.findViewById(R.id.weddingRecyler);
        recyclerViewAstro = view.findViewById(R.id.astroRecycler);
        locationhome = view.findViewById(R.id.LocationHome);

        loadData();

        database = FirebaseDatabase.getInstance();
        pujas = database.getReference("Popularpuja");
        addresses = database.getReference("Address");

        RelativeLayout LocationViewadd = view.findViewById(R.id.LocationBar);
        LocationViewadd .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserLocation.class);
                startActivity(intent);
            }
        });

        RelativeLayout pujaban = view.findViewById(R.id.homePujaIconBar);
        pujaban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentselect = new pujafragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homecontainer, fragmentselect).addToBackStack(null).commit();
            }
        });
        RelativeLayout wedban = view.findViewById(R.id.homeWedIconBar);
        wedban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentselect = new weddingfragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homecontainer, fragmentselect).addToBackStack(null).commit();
            }
        });
        RelativeLayout astroban = view.findViewById(R.id.homeAstroIconBar);
        astroban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentselect = new astrofragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homefragment, fragmentselect).addToBackStack(null).commit();
            }
        });
        RelativeLayout tarotban = view.findViewById(R.id.homeTarotIconBar);
        tarotban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), weddinglist.class);
                Log.e("Cardview","CLICK");
                startActivity(intent);
            }
        });


        manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPujas = view.findViewById(R.id.pujaRecycler);
        recyclerViewPujas.setLayoutManager(manager);

        recyclerViewWedding.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        recyclerViewAstro.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        weddingCardList = new ArrayList<>();
        weddingCardList.add(new weddingHelperClass(R.drawable.puja, "Coming Soon", "This feature will be available Soon!"));
        weddingCardList.add(new weddingHelperClass(R.drawable.puja, "Coming Soon", "This feature will be available Soon!"));
        weddingCardList.add(new weddingHelperClass(R.drawable.puja, "Coming Soon", "This feature will be available Soon!"));
        weddingCardList.add(new weddingHelperClass(R.drawable.puja, "Coming Soon", "This feature will be available Soon!"));

        recyclerViewWedding.setAdapter(new weddingAdapter(weddingCardList));

        astroCard = new ArrayList<>();
        astroCard.add(new astroCardHelperClass(R.drawable.aries, "Aries", "Coming Soon! "));
        astroCard.add(new astroCardHelperClass(R.drawable.taurus, "Taurus", "Coming Soon!"));
        astroCard.add(new astroCardHelperClass(R.drawable.gemini, "Gemini", "Coming Soon!"));
        astroCard.add(new astroCardHelperClass(R.drawable.cancer, "Cancer", "Coming Soon!"));
        astroCard.add(new astroCardHelperClass(R.drawable.leo, "Leo", "Coming Soon!"));
        astroCard.add(new astroCardHelperClass(R.drawable.virgo, "Virgo", "Coming Soon!"));
        astroCard.add(new astroCardHelperClass(R.drawable.libra, "Libra", "Coming Soon!"));
        astroCard.add(new astroCardHelperClass(R.drawable.scorpio, "Scorpio", "Coming Soon!"));
        astroCard.add(new astroCardHelperClass(R.drawable.sagittarius, "Sagittarius", "Coming Soon!"));
        astroCard.add(new astroCardHelperClass(R.drawable.capricorn, "Capricorn", "Coming Soon!"));
        astroCard.add(new astroCardHelperClass(R.drawable.aquarius, "Aquarius", "Coming Soon!"));
        astroCard.add(new astroCardHelperClass(R.drawable.pisces, "Pisces", "Coming Soon!"));

        recyclerViewAstro.setAdapter(new astroAdapter(astroCard));


        weddingAdapter = new weddingAdapter(weddingCardList);
        weddingAdapter.notifyDataSetChanged();
        recyclerViewWedding.setAdapter(weddingAdapter);

        weddingAdapter.setOnItemClickListener(new weddingAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {




                Intent intent = new Intent(getActivity(), weddinglist.class);

                startActivity(intent);

            }
        });

        astroAdapter = new astroAdapter(astroCard);
        astroAdapter.notifyDataSetChanged();
        recyclerViewAstro.setAdapter(astroAdapter);

        astroAdapter.setOnItemClickListener(new astroAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(getActivity(), weddinglist.class);

                startActivity(intent);

            }
        });

        loadpuja();


        return view;

    }

    private void loadpuja() {



        FirebaseRecyclerOptions<CategoryTwo> options = new FirebaseRecyclerOptions.Builder<CategoryTwo>()
                .setQuery(pujas.child("data"), CategoryTwo.class)
                .build();

        Log.i("Orderlistcalled","Order list called");

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CategoryTwo, CategoryTwoViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull CategoryTwoViewHolder holder, int position, @NonNull @NotNull CategoryTwo model) {


                holder.dataId.setText(model.getDataDesc());
                holder.dataName.setText(model.getDataName());

                holder.SubCategoryInterfaceClick(new SubCategoryOnClickInterface() {
                    @Override
                    public void onClick(View view, boolean isLongPressed) {
                        Intent intent = new Intent(getContext(),pujainfo.class);
                        intent.putExtra("userId",model.getDataId());
                        intent.putExtra("userName",model.getDataName());
                        intent.putExtra("userAge",model.getDataAge());
                        intent.putExtra("userDesc",model.getDataDesc());
                        intent.putExtra("userImp",model.getDataImp());
                        intent.putExtra("userPrice",model.getDataPrice());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @NotNull
            @Override
            public CategoryTwoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.item_view_two, parent, false);
                return new CategoryTwoViewHolder(view);
            }
        };

        recyclerViewPujas.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter.notifyDataSetChanged();

    }

}