package com.guruji.app.HelperClassses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.guruji.app.R;

import java.util.ArrayList;

public class astroAdapter extends RecyclerView.Adapter<astroAdapter.astroCardViewHolder>{

    ArrayList<astroCardHelperClass> astroCard;
    private astroAdapter.onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(astroAdapter.onItemClickListener mListener){
        this.mListener = mListener;
    }


    public astroAdapter(ArrayList<astroCardHelperClass> astroCard) {
        this.astroCard = astroCard;
    }

    @NonNull
    @Override
    public astroCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.astrologycard, parent,false);
        astroCardViewHolder astroViewHolder = new astroCardViewHolder(view, mListener);
        return astroViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull astroAdapter.astroCardViewHolder holder, int position) {

        astroCardHelperClass astroCardHelperClass = astroCard.get(position);

        holder.image.setImageResource(astroCardHelperClass.getImage());
        holder.title.setText(astroCardHelperClass.getTitle());
        holder.description.setText(astroCardHelperClass.getDescription());


    }

    @Override
    public int getItemCount() {
        return astroCard.size();
    }

    public  static class astroCardViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title, description;

        public astroCardViewHolder(@NonNull View itemView, final astroAdapter.onItemClickListener mListener) {
            super(itemView);

            //hooks
            image = itemView.findViewById(R.id.astrologyImageView);
            title = itemView.findViewById(R.id.astrologyTitle);
            description = itemView.findViewById(R.id.astrologydesc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }



}
