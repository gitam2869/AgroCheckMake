package com.example.agrocheckmake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ExpertContactAdapter extends RecyclerView.Adapter<ExpertContactAdapter.PRSListViewHolder>
{
    private List<ExpertContactInfo> sellCroList;
//    private Context mCtx;

    public ExpertContactAdapter(List<ExpertContactInfo> sellCroList)
    {
//        this.mCtx = mCtx;
        this.sellCroList = sellCroList;
    }


    @NonNull
    @Override
    public PRSListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.expert_contacts_list_farmer,parent,false);
        return new PRSListViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PRSListViewHolder holder, int position)
    {
        ExpertContactInfo postCropData = sellCroList.get(position);


        holder.textViewname.setText(postCropData.getName());
        holder.textViewm1.setText(postCropData.getM1());
        holder.textViewm2.setText(postCropData.getM2());
    }

    @Override
    public int getItemCount()
    {
        return sellCroList.size();
    }


    //create variable

    private OnItemClickListener mListener;

    //interface

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public static class PRSListViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewname;
        TextView textViewm1;
        TextView textViewm2;
;

        public PRSListViewHolder(@NonNull View itemView, final OnItemClickListener listener)
        {
            super(itemView);

            textViewname = itemView.findViewById(R.id.idTextViewExpertNameFarmer);
            textViewm1 = itemView.findViewById(R.id.idTextViewExpertM1Farmer);
            textViewm2 = itemView.findViewById(R.id.idTextViewExpertM2Farmer);


            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);

                        }
                    }
                }
            });
        }
    }
}
