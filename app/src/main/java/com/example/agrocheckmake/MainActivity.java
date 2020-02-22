package com.example.agrocheckmake;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button buttonNext;
    private ImageView imageViewLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(!isConnected())
        {

            setContentView(R.layout.no_internet);
            getSupportActionBar().hide();
            alertMessage();
            return;

        }

            Toast.makeText(MainActivity.this,"Welcome", Toast.LENGTH_SHORT).show();

            if(SharedPrefManagerFarmer.getInstance(this).isLoggedIn() && SharedPrefManagerFarmer.getInstance(this).isFarmer().equals("Farmer"))
            {
//                 Toast.makeText(getApplicationContext(),"main 1 "+SharedPrefManagerFarmer.getInstance(this).isFarmer(),Toast.LENGTH_LONG).show();
                 startActivity(new Intent(this, HomePageFarmerActivity.class));
                 finish();
                 return;
            }

            if(SharedPrefManagerBuyer.getInstance(this).isLoggedIn()&& SharedPrefManagerBuyer.getInstance(this).isBuyer().equals("Buyer"))
            {
//                Toast.makeText(getApplicationContext(),"main 2 "+SharedPrefManagerFarmer.getInstance(this).isFarmer(),Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, HomePageBuyerActivity.class));
                finish();
                return;
            }
            setContentView(R.layout.activity_main);


//        setContentView(R.layout.activity_main);



        getSupportActionBar().hide();

        buttonNext = findViewById(R.id.idButtonNextMainActivity);
        imageViewLogo = findViewById(R.id.idImageViewLogoMainActivity);

//        Picasso.get().load("http://agrocheckmake.000webhostapp.com/IU/aaaa/IMG_Gautam.png").into(imageViewLogo);

        buttonNext.setOnClickListener(this);



    }
//    @Override
//    public void onBackPressed()
//    {
//        moveTaskToBack(true);
//    }
    @Override
    public void onClick(View view)
    {
        if(view == buttonNext)
        {
            Intent intent = new Intent(this,CategoryActivity.class);
            startActivity(intent);
        }
    }


    public boolean isConnected()
    {
        boolean connected = false;

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            //we are connected to a network
            connected = true;
        }
        else
        {
            connected = false;
        }

        return connected;
    }


    public void alertMessage()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MainActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Please connect with to working internet connection");

        // Set Alert Title
        builder.setTitle("Network Error!");

        // Set Cancelable false// Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.

        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Ok",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                                finish();
                                startActivity(getIntent());
                            }
                        });


        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }


    @Override
    public void onBackPressed()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MainActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to exit ?");

        // Set Alert Title
        builder.setTitle("Alert !");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                                moveTaskToBack(true);
//                                finish();

                            }
                        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

}
