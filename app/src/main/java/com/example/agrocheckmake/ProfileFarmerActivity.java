package com.example.agrocheckmake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileFarmerActivity extends AppCompatActivity implements View.OnClickListener
{

    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewMobile;
    private TextView textViewLand;
    private TextView textViewLandUnit;
    private TextView textViewAddress;

    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_farmer);


        textViewName = findViewById(R.id.idTextViewNameProfileFarmer);
        textViewEmail = findViewById(R.id.idTextViewEmailProfileFarmer);
        textViewMobile = findViewById(R.id.idTextViewMobileProfileFarmer);
        textViewLand = findViewById(R.id.idTextViewLandProfileFarmer);
        textViewLandUnit = findViewById(R.id.idTextViewLandUnitProfileFarmer);
        textViewAddress = findViewById(R.id.idTextViewAddressProfileFarmer);

//        buttonLogout = findViewById(R.id.idButtonLogoutProfileFarmer);

        textViewName.setText("Name : "+ SharedPrefManagerFarmer.getInstance(this).getUserName());
        textViewEmail.setText("Email : "+SharedPrefManagerFarmer.getInstance(this).getUserEmail());
        textViewMobile.setText("Mobile No. : "+SharedPrefManagerFarmer.getInstance(this).getUserMobile());
        textViewLand.setText("Land Area : "+SharedPrefManagerFarmer.getInstance(this).getUserLand());
        textViewLandUnit.setText(SharedPrefManagerFarmer.getInstance(this).getUserLandUnit());
        textViewAddress.setText("Address : "+SharedPrefManagerFarmer.getInstance(this).getUserAddress());

//        buttonLogout.setOnClickListener(this);
    }
    

    @Override
    public void onClick(View view)
    {
        if(view == buttonLogout)
        {
            SharedPrefManagerFarmer.getInstance(this).logout();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
