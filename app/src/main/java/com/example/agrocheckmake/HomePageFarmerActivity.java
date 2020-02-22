package com.example.agrocheckmake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class HomePageFarmerActivity extends AppCompatActivity implements View.OnClickListener
{

    LinearLayout linearLayoutCropSelection;
    LinearLayout linearLayoutSellCrop;
    LinearLayout linearLayoutSelectBuyer;
    LinearLayout linearLayoutSettings;

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

        setContentView(R.layout.activity_home_page_farmer);

        linearLayoutCropSelection = findViewById(R.id.idLinearLayoutCropSelectionHomePageFarmer);
        linearLayoutSellCrop = findViewById(R.id.idLinearLayoutSellCropHomePageFarmer);
        linearLayoutSelectBuyer = findViewById(R.id.idLinearLayoutSelectBuyerHomePageFarmer);
        linearLayoutSettings = findViewById(R.id.idLinearLayoutSettingsHomePageFarmer);


        linearLayoutCropSelection.setOnClickListener(this);
        linearLayoutSellCrop.setOnClickListener(this);
        linearLayoutSelectBuyer.setOnClickListener(this);
        linearLayoutSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view == linearLayoutCropSelection)
        {
            Intent intent = new Intent(this, CropSelectionFarmerActivity.class);
            startActivity(intent);
        }

        if(view == linearLayoutSellCrop)
        {
            Intent intent = new Intent(this, SellCropFarmerActivity.class);
            startActivity(intent);
        }

        if(view == linearLayoutSelectBuyer)
        {
            Intent intent = new Intent(this, SelectBuyerFarmerActivity.class);
            startActivity(intent);
        }

        if(view == linearLayoutSettings)
        {
            Intent intent = new Intent(this, ExperContactsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_farmer,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.item1:
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/agrocheckmake?r=nametag/")));
                startActivity(new Intent(this,ProfileFarmerActivity.class));

                break;

            case R.id.item2:
                startActivity(new Intent(this,SocialMediaActivity.class));
                break;

            case R.id.item3:
                SharedPrefManagerBuyer.getInstance(this).logout();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed()
//    {
//        moveTaskToBack(true);
//    }

    @Override
    public void onBackPressed()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(HomePageFarmerActivity.this);

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
//                                finish();
                                moveTaskToBack(true);
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
                .Builder(HomePageFarmerActivity.this);

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
}
