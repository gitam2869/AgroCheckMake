package com.example.agrocheckmake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuyCropsBuyerActivity extends AppCompatActivity
{

    private SellCropListFarmerAdapter adapter;

    //a list to store all the prs users name
    List<SellCropFarmerInfo> sellCropList;

    //the recyclerview
    RecyclerView recyclerView;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_crops_buyer);

        //initializing the postcrop list
        sellCropList = new ArrayList<>();

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.idRecycleViewSellCropList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SellCropListFarmerAdapter(sellCropList);

        progressDialog = new ProgressDialog(this);


        getSellCropList();
    }

    private void getSellCropList()
    {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_SELL_CROP_LIST_FARMER,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        progressDialog.dismiss();
                        try
                        {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++)
                            {

                                //getting product object from json array
                                JSONObject jsonObject = array.getJSONObject(i);

                                sellCropList.add(new SellCropFarmerInfo(
                                                jsonObject.getString("imagepath"),
                                                jsonObject.getString("name"),
                                                jsonObject.getString("quantity"),
                                                jsonObject.getString("rate"),
                                                jsonObject.getString("description"),
                                                jsonObject.getString("farmername"),
                                                jsonObject.getString("farmernumber")
                                        )
                                );
                            }

                            //creating adapter object and setting it to recyclerview
//                            PRSUserAdapter adapter = new PRSUserAdapter(prsUsersList);

                            recyclerView.setAdapter(adapter);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.refresh_buy_crops_buyer,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.item1:
                finish();
                startActivity(getIntent());
                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
