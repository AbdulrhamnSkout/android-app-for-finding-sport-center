package com.example.b7sport;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FreindsProfileViewHolder extends RecyclerView.ViewHolder {
    TextView fullname,email,phonenumber,address;
    View mView;

    public FreindsProfileViewHolder(@NonNull View itemView){
        super(itemView);

        mView = itemView;
/*
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());

            }
        });*/
        fullname = itemView.findViewById(R.id.Nameid);
        email = itemView.findViewById(R.id.Emailaddress);
        phonenumber = itemView.findViewById(R.id.PhoneNumber);
        address = itemView.findViewById(R.id.Address111);

    }

    private ViewHolder.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view , int position);

    }
    public void setonClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

}
