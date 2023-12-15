package com.guruji.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link astrofragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class astrofragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    View mainGrid;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public astrofragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment astrofragment.
     */
    // TODO: Rename and change types and number of parameters
    public static astrofragment newInstance(String param1, String param2) {
        astrofragment fragment = new astrofragment();
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
        view =  inflater.inflate(R.layout.fragment_astrofragment, container, false);

        mainGrid = view.findViewById(R.id.mainGrid);
        //Set Event

        CardView infobase = view.findViewById(R.id.item1);
        infobase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), weddinglist.class);
                Log.e("Cardview","CLICK");
                startActivity(intent);
            }
        });
        CardView infobase1 = view.findViewById(R.id.item2);
        infobase1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), weddinglist.class);
                Log.e("Cardview","CLICK");
                startActivity(intent);
            }
        });
        CardView infobase2 = view.findViewById(R.id.item3);
        infobase2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), weddinglist.class);
                Log.e("Cardview","CLICK");
                startActivity(intent);
            }
        });
        CardView infobase3 = view.findViewById(R.id.item4);
        infobase3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), weddinglist.class);
                Log.e("Cardview","CLICK");
                startActivity(intent);
            }
        });
        ImageView icon1 = view.findViewById(R.id.icon1);
        icon1.setImageResource(R.drawable.constellations);

        return view;
    }
}