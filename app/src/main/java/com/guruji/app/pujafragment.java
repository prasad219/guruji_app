package com.guruji.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link pujafragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pujafragment extends Fragment{

    String pujasubname, pujaDescription,pujaImportance,pujaPrice,pujaDiscount;

    ShimmerFrameLayout layout;
    LinearLayout linearLayout;

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;
    Handler handler = new Handler();
    FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;
    FirebaseRecyclerAdapter<CategoryTwo, CategoryTwoViewHolder> adapter2;
    RecyclerView.LayoutManager manager;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public pujafragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pujafragment.
     */
    // TODO: Rename and change types and number of parameters
    public static pujafragment newInstance(String param1, String param2) {
        pujafragment fragment = new pujafragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pujafragment, container, false);

        layout = view.findViewById(R.id.shrimmer);
        linearLayout = view.findViewById(R.id.mainlayout);


        RelativeLayout searchTap = view.findViewById(R.id.SearchTap);
        searchTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), searchMenu.class);
                startActivity(intent);
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.stopShimmer();
                layout.hideShimmer();
                layout.setVisibility(View.GONE);

                linearLayout.setVisibility(View.VISIBLE);
            }
        }, 1500);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Category");

        manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(manager);

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
                                Intent intent = new Intent(getContext(),pujainfo.class);
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
                        View v2 = LayoutInflater.from(getContext())
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
                View v1 = LayoutInflater.from(getContext())
                        .inflate(R.layout.item_view_one,parent,false);
                return new CategoryViewHolder(v1);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


         /*

        recyclerViewPuja = view.findViewById(R.id.pujaRecycler);


        recyclerViewPuja.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        pujaCard = new ArrayList<>();
        pujaCard.add(new pujaCardHelperClass(R.drawable.puja, "Garbhadhan Sanskar", "puja desc small","Garbhadhan Sanskar is performed before or immediately after when conceived. As per science, this Sanskar is meant fora better child and better parental genes. Future parents have a responsibility to give birth to new human life which lets their dynasty grow. To gain special blessings from Goddess for this responsibility Garbhadhan Sanskar is performed.","Blessings of Goddess results in reducing the stress of parents and which helps in better development of the child. This adds to the sense of responsibility and better care. Duration: 1 Hour 30 Minutes",
                "5000","0"));
        pujaCard.add(new pujaCardHelperClass(R.drawable.puja, "Jawal", "puja desc small","This sanskar is performed to gain power, the bliss that remains long term. It is also known as the first hair cut of the child. This sanskar is performed when the child has grown to one year of his age. Only from few hairs are kept and rest are cut off during this sanskar.","This is one way to win the blessings of God (Only a small part of the head is kept hairy and rest is clean shaved in this sanskar) which results in gaining pure food, long life & purification from sins that are done during Garbha. It also symbolizes that the person is in the worship of Lord Ganpati, Punyavachan, Goddess Matruka & Nandishashan.Duration: 1 Hour 30 Minutes"
                ,"5000","0"));
        pujaCard.add(new pujaCardHelperClass(R.drawable.puja, "Name Sanskar", "puja desc small","Naming Samskar / Barse / Naming is one of the important religious rituals of the Hindu religion. Naming or Rituals / Puja is the first celebration of new birth and is usually done on the 12th day after the birth of a child.","The name of the newborn is preserved while preserving the holy mantle. Every goddess and goddess pray to them and make them happy, health, prosperity, and fame. Duration: 1 Hour and 30 Minutes"
                ,"5000","0"));
        pujaCard.add(new pujaCardHelperClass(R.drawable.puja, "Rutu Shanti", "puja desc small","Aatungal sanskar is performed in 8 months while the female is pregnant. Aatungal Sanskar is performed by the recitation of certain mantras. The aim of the Mantra is to get divine blessings. This Sanskar Puja creates a sense of responsibility of resulting in new blossoming life.","The significance of this sacrament is more important to pregnant women. Aatungal sanskar is beneficial for the longevity of the child, health, development of the child. In this ritual, the use of Audumbar Tree fruit or Ficus Racemosa and juice of Durva / Scutch grass is considered useful for pregnant women in Ayurveda. Duration: 2 hours"
                ,"5000","0"));
        pujaCard.add(new pujaCardHelperClass(R.drawable.puja, "Upanayan Sanskar", "puja desc small","Upanayan Sanskar is one of the pure sanskaras in Hindu religion.  It is a traditional ritual by which a Guru and Hindu religion welcome a person. In this occasion, a pure thread chanted by mantras is given to the person. Later this thread is tied around his chest. This ceremony consists of Munj Vidhi.","All the skills like Written, Reading, Mathematics, Art & Vedas are formally being after this sanskar. Ideally, after this sanskar, the disciples were shifted to Gurukul(School) for formal education. Hence this vidhi is open to everyone at any age. Duration: 1 Hour 30 Minutes"
                ,"5000","0"));
        pujaCard.add(new pujaCardHelperClass(R.drawable.puja, "Upanayan Sanskar and Grahamakh", "puja desc small","Upanayan Sanskar is one of the pure sanskaras in Hindu religion.  It is a traditional ritual by which a Guru and Hindu religion welcome a person. In this occasion, a pure thread chanted by mantras is given to the person. Later this thread is tied around his chest. This ceremony consists of Graha Mukh, Munj Vidhi.","All the skills like Written, Reading, Mathematics, Art & Vedas are formally being after this sanskar. Ideally, after this sanskar, the disciples were shifted to Gurukul(School) for formal education. Hence this vidhi is open to everyone at any age."
                ,"5000","0"));

       pujaAdapter = new pujaAdapter(pujaCard);
        pujaAdapter.notifyDataSetChanged();
        recyclerViewPuja.setAdapter(pujaAdapter);

        pujaAdapter.setOnItemClickListener(new pujaAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {

                pujasubname = pujaCard.get(position).getName();
                pujaDescription = pujaCard.get(position).getDescription();
                pujaImportance = pujaCard.get(position).getImportance();
                pujaPrice = pujaCard.get(position).getPrice();
                pujaDiscount = pujaCard.get(position).getDiscount();


                Intent intent = new Intent(getActivity(), pujainfo.class);
                intent.putExtra("pujasubname",pujasubname);
                intent.putExtra("pujaDescription",pujaDescription);
                intent.putExtra("pujaImportance",pujaImportance);
                intent.putExtra("pujaPrice",pujaPrice);
                intent.putExtra("pujaDiscount",pujaDiscount);


                startActivity(intent);

            }
        }); */


        return view;
    }

}