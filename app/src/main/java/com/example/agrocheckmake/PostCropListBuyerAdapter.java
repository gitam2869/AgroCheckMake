package com.example.agrocheckmake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
//        super.onBindViewHolder(holder,position);

        PostCropBuyerInfo postCropData = postCropList.get(position);

//        holder.textViewId.setText("id : "+postCropData.getId());
        holder.textViewname.setText(postCropData.getName());
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

        void onItemClick1(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }



    public static class PRSListViewHolder extends RecyclerView.ViewHolder
    {
//        TextView textViewId;
        TextView textViewname;
        TextView textViewquantity;
        TextView textViewrate;
        TextView textViewdescription;
        TextView textViewtime;
        TextView textViewbuyername;
        TextView textViewbuyernumber;
        ImageButton buttonCancel;
        Button buttonAddToList;


        public PRSListViewHolder(@NonNull View itemView, final OnItemClickListener listener)
        {
            super(itemView);

//            textViewId = itemView.findViewById(R.id.idTextViewPostCropIdBuyer);
            textViewname = itemView.findViewById(R.id.idTextViewPostCropNameBuyer);
            textViewquantity = itemView.findViewById(R.id.idTextViewPostCropQuantityBuyer);
            textViewrate = itemView.findViewById(R.id.idTextViewPostCropRateBuyer);
            textViewdescription = itemView.findViewById(R.id.idTextViewPostCropDesriptionBuyer);
            textViewtime = itemView.findViewById(R.id.idTextViewPostCropTimeBuyer);
            textViewbuyername = itemView.findViewById(R.id.idTextViewPostBuyerNameBuyer);
            textViewbuyernumber = itemView.findViewById(R.id.idTextViewPostBuyerNumberBuyer);
            buttonCancel = itemView.findViewById(R.id.idButtonPostCancelButtonBuyer);
            buttonAddToList = itemView.findViewById(R.id.idButtonPostAddToListButtonBuyer);



            buttonCancel.setOnClickListener(new View.OnClickListener()
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

            buttonAddToList.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick1(position);

                        }
                    }
                }
            });

        }
    }
}
