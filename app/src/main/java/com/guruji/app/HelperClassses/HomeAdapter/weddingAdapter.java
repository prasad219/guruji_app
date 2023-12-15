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

public class weddingAdapter extends RecyclerView.Adapter<weddingAdapter.weddingCardViewHolder>{

        ArrayList<weddingHelperClass> weddingCardList;
        private weddingAdapter.onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(weddingAdapter.onItemClickListener mListener){
        this.mListener = mListener;
    }

public weddingAdapter(ArrayList<weddingHelperClass> weddingCardList) {
        this.weddingCardList = weddingCardList;
        }

@NonNull
@Override
public weddingCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weddingcard, parent,false);
        weddingCardViewHolder wedcardViewHolder = new weddingCardViewHolder(view, mListener);
        return wedcardViewHolder;
        }

@Override
public void onBindViewHolder(@NonNull weddingAdapter.weddingCardViewHolder holder, int position) {

        weddingHelperClass weddingHelperClass = weddingCardList.get(position);

        holder.image.setImageResource(weddingHelperClass.getImage());
        holder.title.setText(weddingHelperClass.getTitle());
        holder.description.setText(weddingHelperClass.getDescription());


        }

@Override
public int getItemCount() {
        return weddingCardList.size();
        }

public  static class weddingCardViewHolder extends RecyclerView.ViewHolder{

    ImageView image;
    TextView title, description;

    public weddingCardViewHolder(@NonNull View itemView, final weddingAdapter.onItemClickListener mListener) {
        super(itemView);

        //hooks
        image = itemView.findViewById(R.id.weddingImageView);
        title = itemView.findViewById(R.id.weddingText);
        description = itemView.findViewById(R.id.weddingdesc);

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
