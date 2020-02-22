package com.example.agrocheckmake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SocialMediaActivity extends AppCompatActivity implements View.OnClickListener
{

    private ImageView imageViewInstagram;
    private ImageView imageViewFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);

        imageViewInstagram = findViewById(R.id.idImageViewInstagramSocialMedia);
        imageViewFacebook = findViewById(R.id.idImageViewFacebookSocialMedia);

        imageViewInstagram.setOnClickListener(this);
        imageViewFacebook.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view == imageViewInstagram)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/agrocheckmake/?r=nametag")));
        }

        if(view == imageViewFacebook)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Agro-Chechmake-100551861527187/")));
        }
    }
}
