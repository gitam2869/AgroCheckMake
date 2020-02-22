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

public class SellCropListFarmerAdapter extends RecyclerView.Adapter<SellCropListFarmerAdapter.PRSListViewHolder>
{
    private List<SellCropFarmerInfo> sellCroList;
    private Context mCtx;

    public SellCropListFarmerAdapter(List<SellCropFarmerInfo> sellCroList)
    {
//        this.mCtx = mCtx;
        this.sellCroList = sellCroList;
    }


    @NonNull
    @Override
    public PRSListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sell_crop_list_farmer,parent,false);
        return new PRSListViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PRSListViewHolder holder, int position)
    {
        SellCropFarmerInfo postCropData = sellCroList.get(position);

        String url = postCropData.getImage();
        Picasso.get().load(url).resize(400,400).into(holder.imageViewUploadedImage);

        holder.textViewname.setText("Crop Name : "+postCropData.getName());
        holder.textViewquantity.setText("Quantity : "+postCropData.getQuantity());
        holder.textViewrate.setText("Rate : "+postCropData.getRate());
        holder.textViewdescription.setText("Description : "+postCropData.getDescription());
        holder.textViewbuyername.setText("Farmer Name : "+postCropData.getFarmername());
        holder.textViewbuyernumber.setText("Farmer Number : "+postCropData.getFarmernumber());
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
        ImageView imageViewUploadedImage;
        TextView textViewname;
        TextView textViewquantity;
        TextView textViewrate;
        TextView textViewdescription;
        TextView textViewbuyername;
        TextView textViewbuyernumber;

        public PRSListViewHolder(@NonNull View itemView, final OnItemClickListener listener)
        {
            super(itemView);

            imageViewUploadedImage = itemView.findViewById(R.id.idImageViewUploadedImageSellCropFarmer);
            textViewname = itemView.findViewById(R.id.idTextViewSellCropNameFarmer);
            textViewquantity = itemView.findViewById(R.id.idTextViewSellCropQuantityFarmer);
            textViewrate = itemView.findViewById(R.id.idTextViewSellCropRateFarmer);
            textViewdescription = itemView.findViewById(R.id.idTextViewSellCropDescriptionFarmer);
            textViewbuyername = itemView.findViewById(R.id.idTextViewSellCropFarmerNameFarmer);
            textViewbuyernumber = itemView.findViewById(R.id.idTextViewSellCropFarmeNumberFarmer);


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
