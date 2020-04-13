package com.example.agrocheckmake;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyCropsBuyerActivity extends AppCompatActivity
{

    private SellCropListFarmerAdapter adapter;

    //a list to store all the prs users name
    List<SellCropFarmerInfo> sellCropList;

    //the recyclerview
    RecyclerView recyclerView;

    private ProgressDialog progressDialog;

    //alert dialog box
    private Button buttonNo;
    private Button buttonYes;
    private TextView textViewText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_crops_buyer);

        final AlertDialog.Builder alert = new AlertDialog.Builder(BuyCropsBuyerActivity.this);
        View view = getLayoutInflater().inflate(R.layout.custom_alert_dialouge_box_farmer,null);

        buttonNo = view.findViewById(R.id.idButtonNoCustomDialougeFarmer);
        buttonYes = view.findViewById(R.id.idButtonYesCustomDialougeFarmer);
        textViewText = view.findViewById(R.id.idTextViewCustomDialougeFarmer);

        alert.setView(view);

        final AlertDialog alertDialog = alert.create();

        alertDialog.setCanceledOnTouchOutside(false);



        //initializing the postcrop list
        sellCropList = new ArrayList<>();

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.idRecycleViewSellCropList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SellCropListFarmerAdapter(sellCropList);

        adapter.setOnItemClickListener(new SellCropListFarmerAdapter.OnItemClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(int position)
            {
                buttonYes.setText("Remove");
                buttonYes.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.colorOfRemoveButton));
                textViewText.setText("Are you sure to remove the selected crop ? it will not shown again to you.");


                int i = SharedPrefManagerBuyer.getInstance(getApplicationContext()).getUserId();
                final String b_id = String.valueOf(i);

                SellCropFarmerInfo sellCropFarmerInfo = sellCropList.get(position);

                final String pos = String.valueOf(position);

                final String crop_id = sellCropFarmerInfo.getId();
                final String name = sellCropFarmerInfo.getName();
                final String quantity = sellCropFarmerInfo.getQuantity();
                final String rate = sellCropFarmerInfo.getRate();
                final String description = sellCropFarmerInfo.getDescription();
                final String imageurl = sellCropFarmerInfo.getImage();
                final String farmername = sellCropFarmerInfo.getFarmername();
                final String farmernumber = sellCropFarmerInfo.getFarmernumber();
                final String cropstatus = "cancel";

                final InsertAysnc insertAysnc = new InsertAysnc();

                buttonNo.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog.dismiss();
                    }
                });

                buttonYes.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        insertAysnc.execute(
                                pos,
                                crop_id,
                                b_id,
                                name,
                                quantity,
                                rate,
                                description,
                                imageurl,
                                farmername,
                                farmernumber,
                                cropstatus
                        );
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick1(int position)
            {
                buttonYes.setText("Add");
                buttonYes.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.colorOfAddButton));
                textViewText.setText("Are you sure to add the selected crop requirement ?");

                int i = SharedPrefManagerBuyer.getInstance(getApplicationContext()).getUserId();
                final String b_id = String.valueOf(i);

                SellCropFarmerInfo sellCropFarmerInfo = sellCropList.get(position);

                final String pos = String.valueOf(position);
                final String crop_id = sellCropFarmerInfo.getId();
                final String name = sellCropFarmerInfo.getName();
                final String quantity = sellCropFarmerInfo.getQuantity();
                final String rate = sellCropFarmerInfo.getRate();
                final String description = sellCropFarmerInfo.getDescription();
                final String imageurl = sellCropFarmerInfo.getImage();
                final String farmername = sellCropFarmerInfo.getFarmername();
                final String farmernumber = sellCropFarmerInfo.getFarmernumber();
                final String cropstatus = "add";

                final InsertAysnc insertAysnc = new InsertAysnc();


                buttonNo.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog.dismiss();
                    }
                });

                buttonYes.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        insertAysnc.execute(
                                pos,
                                crop_id,
                                b_id,
                                name,
                                quantity,
                                rate,
                                description,
                                imageurl,
                                farmername,
                                farmernumber,
                                cropstatus
                        );
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

        progressDialog = new ProgressDialog(this);

        MyAsync myAsync = new MyAsync();
        myAsync.execute();

    }

    /************/

    private class MyAsync extends AsyncTask<Void, Integer, String[][]>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }

        @Override
        protected String[][] doInBackground(Void... voids)
        {
            final boolean[] b = {true};

            Log.d("dd", "doInBackground: "+"1");

            final String[][] arrayStringId = new String[1][1];
            Log.d("dd", "doInBackground: "+"2");

            int i = SharedPrefManagerBuyer.getInstance(getApplicationContext()).getUserId();
            final String b_id = String.valueOf(i);
            Log.d("dd", "doInBackground: "+"3");

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.URL_READ_CROP_STATUS_SELL_CROP_HISTORY,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                            Log.d("dd", "doInBackground: "+"4");

                            try
                            {
                                Log.d("dd", "doInBackground: "+"5");

                                //converting the string to json array object
                                JSONArray array = new JSONArray(response);
                                int count = 1;
                                Log.d("dd", "doInBackground: "+"6");


                                arrayStringId[0] = new String[array.length()];
                                Log.d("dd", "doInBackground: "+"7");

                                for (int i = 0; i < array.length(); i++) {
                                    //getting product object from json array
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    Log.d("ddd", "onResponse: " + jsonObject.getInt("crop_id"));
                                    arrayStringId[0][i] = jsonObject.getString("crop_id");
                                }
                                Log.d("dd", "doInBackground: "+"8");


                                for (int j = 0; j < arrayStringId[0].length; j++)
                                {
                                    Log.d("Read Status", "onResponse0: " + arrayStringId[0][j]);
                                }
                                Log.d("dd", "doInBackground: "+"9");

                                b[0] = false;
                            }
                            catch (JSONException e)
                            {
                                Log.d("dd", "doInBackground: "+"10");

                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Log.d("dd", "doInBackground: "+"11");
                            b[0] = false;

                            progressDialog.dismiss();
                            Toast.makeText(
                                    getApplicationContext(),
                                    error.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            )
            {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Log.d("dd", "doInBackground: "+"12");

                    Map<String, String> params = new HashMap<>();
                    params.put("b_id", b_id);
                    return params;

                }
            };

            int a = 0;
            if(b[0]) {
                while (b[0]) {
                    Log.d("dd", "doInBackground: " + "13");

                    if (a == 0) {
                        Log.d("dd", "doInBackground: " + "14");

                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                        a++;
                    }
                    Log.d("dd", "doInBackground: " + "15");


                }
            }
//            else
//            {
//                Log.d("dd", "doInBackground******: "+"16*******");
//
//                return arrayStringId;
//            }

            Log.d("dd", "doInBackground: "+"16");

            return arrayStringId;

        }

        @Override
        protected void onPostExecute(final String[][] strings)
        {
            super.onPostExecute(strings);

            for(int i = 0; i < strings[0].length; i++)
            {
                Log.d("Read Status", "onResponse0fffffffff: "+ strings[0][i]);
            }

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.URL_SELL_CROP_LIST_FARMER,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {

                            try
                            {

                                //converting the string to json array object
                                JSONArray array = new JSONArray(response);
                                boolean isfound = false;

                                if(array.length() == 0)
                                {
                                    Log.d("ddddd", "onResponse: ");
                                    String a = "farmer";
                                    setNothing(a);
                                }

                                for (int i = 0; i < array.length(); i++)
                                {

                                    //getting product object from json array
                                    JSONObject jsonObject = array.getJSONObject(i);

                                    isfound = false;
//                                    Toast.makeText(getApplicationContext(),"onPost",Toast.LENGTH_LONG).show();

                                    for(String j : strings[0])
                                    {

                                        if(jsonObject.getString("id").equals(j))
                                        {
                                            Log.d("jff", "onResponse:ddd "+j);
                                            isfound = true;
                                        }

                                    }

                                    if(!isfound)
                                    {
                                        Log.d("jff", "onResponse:ddd/88 "+jsonObject.getString("id"));

                                        sellCropList.add(new SellCropFarmerInfo(
                                                        jsonObject.getString("id"),
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
                                }

                                recyclerView.setAdapter(adapter);
                                progressDialog.dismiss();

                                if(sellCropList.isEmpty() && array.length() != 0)
                                {
                                    String a = "list";
                                    setNothing(a);
                                }

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

            RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }
    }

    private class InsertAysnc extends  AsyncTask<String,Void,Void>
    {
        @Override
        protected Void doInBackground(final String... strings)
        {
            int iii = Integer.parseInt(strings[0]);
            sellCropList.remove(iii);
            adapter.notifyItemRemoved(iii);

            final StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.URL_SELL_CROP_HISTORY,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
//                            progressDialog.dismiss();
//                            finish();
//                            startActivity(getIntent());
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
                                    Toast.LENGTH_LONG).show();
                        }
                    }
            )
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Map<String, String> params = new HashMap<>();
                    params.put("crop_id",strings[1]);
                    params.put("b_id",strings[2]);
                    params.put("name", strings[3]);
                    params.put("quantity", strings[4]);
                    params.put("rate",strings[5]);
                    params.put("description",strings[6]);
                    params.put("imageurl",strings[7]);
                    params.put("farmername",strings[8]);
                    params.put("farmernumber",strings[9]);
                    params.put("cropstatus",strings[10]);

                    return params;
                }
            };

                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_collections,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.item1:
//                finish();
                startActivity(new Intent(this,SelectBuyCropListBuyerActivity.class));
                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNothing(String a)
    {
        setContentView(R.layout.no_internet);

        TextView t = findViewById(R.id.idTextViewNoInternet);
        Button b = findViewById(R.id.idButtonNoInternet);

        b.setVisibility(View.GONE);

        if(a.equals("farmer"))
        {
            Log.d("dddddif", "onResponse: ");

            t.setText("Farmers Sell Nothing !!!");
        }
        else
        {
            t.setText("You Have See All Crops !!!");
        }


    }

    /************/


//    private void getSellCropList()
//    {
//        progressDialog.setMessage("Please wait...");
//        progressDialog.show();
//
//
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                Constants.URL_SELL_CROP_LIST_FARMER,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response)
//                    {
////                        Toast.makeText(getApplicationContext(),"fffffffffff",Toast.LENGTH_LONG).show();
//                        try
//                        {
//                            //converting the string to json array object
//                            JSONArray array = new JSONArray(response);
//                            boolean isfound = false;
//
//                            if(array.length() == 0)
//                            {
//                                setNothing();
//                            }
//
//                            for (int i = 0; i < array.length(); i++)
//                            {
//
//                                //getting product object from json array
//                                JSONObject jsonObject = array.getJSONObject(i);
//
//
//
//                                isfound = false;
//
////                                for(String j : arrayStringId)
////                                {
////                                    if(jsonObject.getString("id").equals(j))
////                                    {
////                                        isfound = true;
////                                    }
////
////                                }
////
////                                if(!isfound)
////                                {
//                                    sellCropList.add(new SellCropFarmerInfo(
//                                                    jsonObject.getString("id"),
//                                                    jsonObject.getString("imagepath"),
//                                                    jsonObject.getString("name"),
//                                                    jsonObject.getString("quantity"),
//                                                    jsonObject.getString("rate"),
//                                                    jsonObject.getString("description"),
//                                                    jsonObject.getString("farmername"),
//                                                    jsonObject.getString("farmernumber")
//                                            )
//                                    );
////                                }
//                            }
//
//                            recyclerView.setAdapter(adapter);
//                            progressDialog.dismiss();
//
//                        }
//                        catch (JSONException e)
//                        {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error)
//                    {
//                        progressDialog.dismiss();
//                        Toast.makeText(
//                                getApplicationContext(),
//                                error.getMessage(),
//                                Toast.LENGTH_LONG
//                        ).show();
//                    }
//                }
//        );
//
//        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
//
//    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private void insertSellCropHistory(
//            final int position,
//            final String crop_id,
//            final String name,
//            final String quantity,
//            final String rate,
//            final String description,
//            final String imageurl,
//            final String farmername,
//            final String farmernumber,
//            final String cropstatus
//    )
//    {
//
//
//        sellCropList.remove(position);
//        adapter.notifyItemRemoved(position);
//
//        int i = SharedPrefManagerBuyer.getInstance(this).getUserId();
//        final String b_id = String.valueOf(i);
//
//        final AlertDialog.Builder alert = new AlertDialog.Builder(BuyCropsBuyerActivity.this);
//        View view = getLayoutInflater().inflate(R.layout.custom_alert_dialouge_box_farmer,null);
//
//        Button buttonNo = view.findViewById(R.id.idButtonNoCustomDialougeFarmer);
//        Button buttonYES = view.findViewById(R.id.idButtonYesCustomDialougeFarmer);
//        TextView textView = view.findViewById(R.id.idTextViewCustomDialougeFarmer);
//
//        if(cropstatus.equals("cancel"))
//        {
//            buttonYES.setText("Remove");
//            buttonYES.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.colorOfRemoveButton));
//            textView.setText("Are you sure to remove the selected crop ? it will not shown again to you.");
//
//        }
//
//        if(cropstatus.equals("add"))
//        {
//            buttonYES.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.colorOfAddButton));
//            textView.setText("Are you sure to add the selected crop requirement ?");
//
//        }
//
//        alert.setView(view);
//
//        final AlertDialog alertDialog = alert.create();
//
//        alertDialog.setCanceledOnTouchOutside(false);
//
//        if(cropstatus.equals("add"))
//        {
//            progressDialog.setMessage("Adding...");
//        }
//
//        if(cropstatus.equals("cancel"))
//        {
//            progressDialog.setMessage("Removing...");
//        }
//
//
//
//        final StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                Constants.URL_SELL_CROP_HISTORY,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response)
//                    {
//                        progressDialog.dismiss();
//                        finish();
//                        startActivity(getIntent());
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error)
//                    {
//                        progressDialog.dismiss();
//
//                        Toast.makeText(
//                                getApplicationContext(),
//                                error.getMessage(),
//                                Toast.LENGTH_LONG).show();
//                    }
//                }
//        )
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError
//            {
//                Map<String, String> params = new HashMap<>();
//                params.put("crop_id",crop_id);
//                params.put("b_id",b_id);
//                params.put("name", name);
//                params.put("quantity", quantity);
//                params.put("rate",rate);
//                params.put("description",description);
//                params.put("imageurl",imageurl);
//                params.put("farmername",farmername);
//                params.put("farmernumber",farmernumber);
//                params.put("cropstatus",cropstatus);
//
//                return params;
//            }
//        };
//
//        buttonNo.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                progressDialog.dismiss();
//                alertDialog.dismiss();
//            }
//        });
//
//        buttonYES.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                progressDialog.show();
//
//                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
//                alertDialog.dismiss();
//            }
//        });
//
//        alertDialog.show();
//    }

//    private void readButtonState()
//    {
//        int i = SharedPrefManagerBuyer.getInstance(this).getUserId();
//        final String b_id = String.valueOf(i);
//
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                Constants.URL_READ_CROP_STATUS_SELL_CROP_HISTORY,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response)
//                    {
//                        try
//                        {
//
//                            //converting the string to json array object
//                            JSONArray array = new JSONArray(response);
//                            int count = 1;
//
//
//                            arrayStringId = new String[array.length()];
//
//                            for (int i = 0; i < array.length(); i++)
//                            {
//                                //getting product object from json array
//                                JSONObject jsonObject = array.getJSONObject(i);
//
//                                arrayStringId [i] = jsonObject.getString("crop_id");
//                            }
//
//
//                            for(int i = 0; i < arrayStringId.length; i++)
//                            {
//                                Log.d("Read Status", "onResponse0: "+arrayStringId[i]);
//                            }
//
//
//                        }
//                        catch (JSONException e)
//                        {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error)
//                    {
//                        progressDialog.dismiss();
//                        Toast.makeText(
//                                getApplicationContext(),
//                                error.getMessage(),
//                                Toast.LENGTH_LONG
//                        ).show();
//                    }
//                }
//        )
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError
//            {
//                Map<String, String> params = new HashMap<>();
//                params.put("b_id",b_id);
//                return params;
//            }
//        };
//
//
//        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
//
//    }

}
