package com.example.agrocheckmake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener
{
    private ImageView imageViewFarmer;
    private ImageView imageViewBuyer;

    public String categoryFarmer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        getSupportActionBar().hide();

        imageViewFarmer = findViewById(R.id.idImageViewFarmerCategoryActivity);
        imageViewBuyer = findViewById(R.id.idImageVeiwBuyerCategoryActivity);

        imageViewFarmer.setOnClickListener(this);
        imageViewBuyer.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        if(view == imageViewFarmer)
        {
            categoryFarmer = "Farmer";
            SharedPrefManagerFarmer.getInstance(getApplicationContext())
                    .farmerCategory("Farmer");
            Intent intent = new Intent(this,LoginFarmerActivity.class);
            startActivity(intent);
        }

        if(view == imageViewBuyer)
        {
            categoryFarmer = "Buyer";
            SharedPrefManagerBuyer.getInstance(getApplicationContext())
                    .farmerCategory("Buyer");
            Intent intent = new Intent(this,LoginBuyerActivity.class);
            startActivity(intent);
        }
    }
}
