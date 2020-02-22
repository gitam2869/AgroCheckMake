package com.example.agrocheckmake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileBuyerActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewMobile;
    private TextView textViewAddress;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_buyer);

        textViewName = findViewById(R.id.idTextViewNameProfileBuyer);
        textViewEmail = findViewById(R.id.idTextViewEmailProfileBuyer);
        textViewMobile = findViewById(R.id.idTextViewMobileProfileBuyer);
        textViewAddress = findViewById(R.id.idTextViewAddressProfileBuyer);

//        buttonLogout = findViewById(R.id.idButtonLogoutProfileBuyer);

        textViewName.setText("Name : "+ SharedPrefManagerBuyer.getInstance(this).getUserName());
        textViewEmail.setText("Email : "+SharedPrefManagerBuyer.getInstance(this).getUserEmail());
        textViewMobile.setText("Mobile No. : "+SharedPrefManagerBuyer.getInstance(this).getUserMobile());
        textViewAddress.setText("Address : "+SharedPrefManagerBuyer.getInstance(this).getUserAddress());

//        buttonLogout.setOnClickListener(this);
    }

        @Override
        public void onClick(View view)
        {
            if(view == buttonLogout)
            {
                SharedPrefManagerBuyer.getInstance(this).logout();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }

}

