package com.example.agrocheckmake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class HomePageBuyerActivity extends AppCompatActivity implements View.OnClickListener
{

    LinearLayout linearLayoutBuyCrops;
    LinearLayout linearLayoutPostRequirement;
    LinearLayout linearLayoutMakeContract;
    LinearLayout linearLayoutViewHistory;

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

        setContentView(R.layout.activity_home_page_buyer);

        linearLayoutBuyCrops = findViewById(R.id.idLinearLayoutBuyCropsHomePageBuyer);
        linearLayoutPostRequirement = findViewById(R.id.idLinearLayoutPostRequirementHomePageBuyer);
        linearLayoutMakeContract = findViewById(R.id.idLinearLayoutMakeContractBuyer);
        linearLayoutViewHistory = findViewById(R.id.idLinearLayoutViewHistoryBuyer);

        linearLayoutBuyCrops.setOnClickListener(this);
        linearLayoutPostRequirement.setOnClickListener(this);
        linearLayoutMakeContract.setOnClickListener(this);
        linearLayoutViewHistory.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        if(view == linearLayoutBuyCrops)
        {
            Intent intent = new Intent(this, BuyCropsBuyerActivity.class);
            startActivity(intent);
        }

        if(view == linearLayoutPostRequirement)
        {
            Intent intent = new Intent(this, PostRequirementBuyerActivity.class);
            startActivity(intent);
        }

        if(view == linearLayoutMakeContract)
        {
            Intent intent = new Intent(this, MakeContractBuyerActivity.class);
            startActivity(intent);
        }

        if(view == linearLayoutViewHistory)
        {
            Intent intent = new Intent(this, ViewHistoryBuyerActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_buyer,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.item1:
                startActivity(new Intent(this,ProfileBuyerActivity.class));
                break;

            case R.id.item2:
                startActivity(new Intent(this,SocialMediaBuyerActivity.class));
                break;

            case R.id.item3:
                logoutBuyer();
//                SharedPrefManagerFarmer.getInstance(this).logout();
//                startActivity(new Intent(this, MainActivity.class));
//                finish();
                break;

//            case R.id.item4:
//                startActivity(new Intent(this,HomePageFarmerActivity.class));
//                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }


    private void logoutBuyer()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(HomePageBuyerActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Are you sure ot Logout ?");

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
                                SharedPrefManagerFarmer.getInstance(getApplicationContext()).logout();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
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
                .Builder(HomePageBuyerActivity.this);

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
                .Builder(HomePageBuyerActivity.this);

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
