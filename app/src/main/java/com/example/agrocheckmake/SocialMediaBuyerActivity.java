package com.example.agrocheckmake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SocialMediaBuyerActivity extends AppCompatActivity implements View.OnClickListener
{
    private LinearLayout linearLayoutInstagram;
    private LinearLayout linearLayoutFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media_buyer);

        linearLayoutInstagram = findViewById(R.id.idLinearLayoutInstagramSocialMediaBuyer);
        linearLayoutFacebook = findViewById(R.id.idLinearLayoutFacebookSocialMediaBuyer);

        linearLayoutInstagram.setOnClickListener(this);
        linearLayoutFacebook.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view == linearLayoutInstagram)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/agrocheckmake/?r=nametag")));
        }

        if(view == linearLayoutFacebook)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Agro-Chechmake-100551861527187/")));
        }
    }
}
