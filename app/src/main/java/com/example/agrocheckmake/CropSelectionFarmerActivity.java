package com.example.agrocheckmake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class CropSelectionFarmerActivity extends AppCompatActivity implements View.OnClickListener
{

    private LinearLayout linearLayoutTestSoil;
    private LinearLayout linearLayoutWeatherForecast;
    private LinearLayout linearLayoutMarketTrends;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_selection_farmer);

        linearLayoutTestSoil = findViewById(R.id.idLinearLayoutTestSoilCropSelectionFarmer);
        linearLayoutWeatherForecast = findViewById(R.id.idLinearLayoutWeatherForecastCropSelectionFarmer);
        linearLayoutMarketTrends = findViewById(R.id.idLinearLayoutMarketTrendsCropSelectionFarmer);

        linearLayoutTestSoil.setOnClickListener(this);
        linearLayoutWeatherForecast.setOnClickListener(this);
        linearLayoutMarketTrends.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view == linearLayoutTestSoil)
        {
            Intent intent = new Intent(this, TestSoilFarmerActivity.class);
            startActivity(intent);
        }

        if(view == linearLayoutWeatherForecast)
        {
            Intent intent = new Intent(this, WeatherForecastFarmerActivity.class);
            startActivity(intent);
        }

        if(view == linearLayoutMarketTrends)
        {
            Intent intent = new Intent(this, MarketTrendsFarmerActivity.class);
            startActivity(intent);
        }
    }
}
