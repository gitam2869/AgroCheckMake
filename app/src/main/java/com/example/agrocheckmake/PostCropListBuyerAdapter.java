package com.example.agrocheckmake;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostCropListBuyerAdapter extends RecyclerView.Adapter<PostCropListBuyerAdapter.PRSListViewHolder>
{
    private List<PostCropBuyerInfo> postCropList;
//    private Context mCtx;

    public PostCropListBuyerAdapter(List<PostCropBuyerInfo> postCropList)
    {
//        this.mCtx = mCtx;
        this.postCropList = postCropList;
    }


    @NonNull
    @Override
    public PRSListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.post_crop_list_buyer,parent,false);
        return new PRSListViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PRSListViewHolder holder, int position)
    {
        PostCropBuyerInfo postCropData = postCropList.get(position);

        holder.textViewname.setText("Crop Name : "+postCropData.getName());
        holder.textViewquantity.setText("Quantity : "+postCropData.getQuantity());
        holder.textViewrate.setText("Rate : "+postCropData.getRate());
        holder.textViewdescription.setText("Description : "+postCropData.getDescription());
        holder.textViewtime.setText("Time : "+postCropData.getTime()+" months");
        holder.textViewbuyername.setText("Buyer Name : "+postCropData.getBuyername());
        holder.textViewbuyernumber.setText("Buyer Number : "+postCropData.getBuyernumber());
    }

    @Override
    public int getItemCount()
    {
        return postCropList.size();
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
        TextView textViewquantity;
        TextView textViewrate;
        TextView textViewdescription;
        TextView textViewtime;
        TextView textViewbuyername;
        TextView textViewbuyernumber;

        public PRSListViewHolder(@NonNull View itemView, final OnItemClickListener listener)
        {
            super(itemView);

            textViewname = itemView.findViewById(R.id.idTextViewPostCropNameBuyer);
            textViewquantity = itemView.findViewById(R.id.idTextViewPostCropQuantityBuyer);
            textViewrate = itemView.findViewById(R.id.idTextViewPostCropRateBuyer);
            textViewdescription = itemView.findViewById(R.id.idTextViewPostCropDesriptionBuyer);
            textViewtime = itemView.findViewById(R.id.idTextViewPostCropTimeBuyer);
            textViewbuyername = itemView.findViewById(R.id.idTextViewPostBuyerNameBuyer);
            textViewbuyernumber = itemView.findViewById(R.id.idTextViewPostBuyerNumberBuyer);


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
