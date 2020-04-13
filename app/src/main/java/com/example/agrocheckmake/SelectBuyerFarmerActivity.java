package com.example.agrocheckmake;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectBuyerFarmerActivity extends AppCompatActivity
{
    private PostCropListBuyerAdapter adapter;

    //a list to store all the prs users name
    List<PostCropBuyerInfo> postCropList;

    //the recyclerview
    RecyclerView recyclerView;

    private ProgressDialog progressDialog;

    TextView t;

    //alert dialog box
    private Button buttonNo;
    private Button buttonYes;
    private TextView textViewText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_buyer_farmer);

        final AlertDialog.Builder alert = new AlertDialog.Builder(SelectBuyerFarmerActivity.this);
        View view = getLayoutInflater().inflate(R.layout.custom_alert_dialouge_box_farmer,null);

        buttonNo = view.findViewById(R.id.idButtonNoCustomDialougeFarmer);
        buttonYes = view.findViewById(R.id.idButtonYesCustomDialougeFarmer);
        textViewText = view.findViewById(R.id.idTextViewCustomDialougeFarmer);

        alert.setView(view);

        final AlertDialog alertDialog = alert.create();

        alertDialog.setCanceledOnTouchOutside(false);


        //initializing the postcrop list
        postCropList = new ArrayList<>();

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.idRecycleViewPrsHandlerList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PostCropListBuyerAdapter(postCropList);


        adapter.setOnItemClickListener(new PostCropListBuyerAdapter.OnItemClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(int position)
            {

                buttonYes.setText("Remove");
                buttonYes.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.colorOfRemoveButton));
                textViewText.setText("Are you sure to remove the selected crop ? it will not shown again to you.");

                int i = SharedPrefManagerFarmer.getInstance(getApplicationContext()).getUserId();
                final String f_id = String.valueOf(i);

                PostCropBuyerInfo postCropData = postCropList.get(position);

                final String pos = String.valueOf(position);

                final String crop_id = postCropData.getId();
                final String b_id = postCropData.getB_Id();
                final String name = postCropData.getName();
                final String quantity = postCropData.getQuantity();
                final String rate = postCropData.getRate();
                final String description = postCropData.getDescription();
                final String time = postCropData.getTime();
                final String buyername = postCropData.getBuyername();
                final String buyernumber = postCropData.getBuyernumber();
                final String cropstatus = "cancel";

                final InsertAsyncTask insertAsyncTask = new InsertAsyncTask();

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
                        insertAsyncTask.execute(
                                pos,
                                crop_id,
                                f_id,
                                name,
                                quantity,
                                rate,
                                description,
                                time,
                                buyername,
                                buyernumber,
                                cropstatus
                        );
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();



//                insertPostCropHistory(
//                        position,
//                        crop_id,
//                        b_id,
//                        name,
//                        quantity,
//                        rate,
//                        description,
//                        time,
//                        buyername,
//                        buyernumber,
//                        cropstatus
//                );

//                Toast.makeText(getApplicationContext(),"cancel",Toast.LENGTH_LONG).show();
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick1(int position)
            {
                buttonYes.setText("Add");
                buttonYes.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.colorOfAddButton));
                textViewText.setText("Are you sure to add the selected crop requirement ?");

                int i = SharedPrefManagerFarmer.getInstance(getApplicationContext()).getUserId();
                final String f_id = String.valueOf(i);

                PostCropBuyerInfo postCropData = postCropList.get(position);

                final String pos = String.valueOf(position);

                final String crop_id = postCropData.getId();
                String b_id = postCropData.getB_Id();
                final String name = postCropData.getName();
                final String quantity = postCropData.getQuantity();
                final String rate = postCropData.getRate();
                final String description = postCropData.getDescription();
                final String time = postCropData.getTime();
                final String buyername = postCropData.getBuyername();
                final String buyernumber = postCropData.getBuyernumber();
                final String cropstatus = "add";

                final InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
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
                        insertAsyncTask.execute(
                                pos,
                                crop_id,
                                f_id,
                                name,
                                quantity,
                                rate,
                                description,
                                time,
                                buyername,
                                buyernumber,
                                cropstatus
                        );
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();


//                insertPostCropHistory(
//                        position,
//                        crop_id,
//                        b_id,
//                        name,
//                        quantity,
//                        rate,
//                        description,
//                        time,
//                        buyername,
//                        buyernumber,
//                        cropstatus
//                );

//                Toast.makeText(getApplicationContext(),"save",Toast.LENGTH_LONG).show();
            }
        });

        progressDialog = new ProgressDialog(this);

        SelectBuyerAsyncTask selectBuyerAsyncTask = new SelectBuyerAsyncTask();
        selectBuyerAsyncTask.execute();

//        readButtonState();

//        getPostCropList();

    }

    private class SelectBuyerAsyncTask extends AsyncTask<Void, Void, String[][]>
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
            int i = SharedPrefManagerFarmer.getInstance(getApplicationContext()).getUserId();
            final String f_id = String.valueOf(i);

            final boolean[] b = {true};

            final String[][] arrayStringId = new String[1][1];

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.URL_READ_CROP_STATUS_POST_CROP,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {

                            try
                            {
                                //converting the string to json array object
                                JSONArray array = new JSONArray(response);
                                int count = 1;

                                arrayStringId[0] = new String[array.length()];

                                for (int i = 0; i < array.length(); i++)
                                {
                                    //getting product object from json array
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    arrayStringId[0][i] = jsonObject.getString("crop_id");
                                }


//                                for (int j = 0; j < arrayStringId[0].length; j++)
//                                {
//                                    Log.d("Read Status", "onResponse0: " + arrayStringId[0][j]);
//                                }

                                b[0] = false;
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
                    Map<String, String> params = new HashMap<>();
                    params.put("f_id",f_id);
                    return params;
                }
            };

            int a = 0;

            if(b[0])
            {
                while (b[0])
                {
                    if (a == 0)
                    {
                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                        a++;
                    }
                }
            }

            return arrayStringId;

        }

        @Override
        protected void onPostExecute(final String[][] strings)
        {
            super.onPostExecute(strings);

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.URL_POST_CROP_LIST_BUYER,
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
                                    String a = "buyer";
                                    setNothing(a);
                                }

                                for (int i = 0; i < array.length(); i++)
                                {

                                    //getting product object from json array
                                    JSONObject jsonObject = array.getJSONObject(i);

                                    isfound = false;

                                for(String j : strings[0])
                                {
                                    if(jsonObject.getString("id").equals(j))
                                    {
                                        isfound = true;
                                    }

                                }

                                if(!isfound)
                                {
                                    postCropList.add(new PostCropBuyerInfo(
                                                    jsonObject.getString("id"),
                                                    jsonObject.getString("b_id"),
                                                    jsonObject.getString("name"),
                                                    jsonObject.getString("quantity"),
                                                    jsonObject.getString("rate"),
                                                    jsonObject.getString("description"),
                                                    jsonObject.getString("time"),
                                                    jsonObject.getString("buyername"),
                                                    jsonObject.getString("buyernumber")
                                            )
                                    );
                                }

                                }

                                recyclerView.setAdapter(adapter);
                                progressDialog.dismiss();
                                if(postCropList.isEmpty() && array.length() != 0)
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

    private class InsertAsyncTask extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(final String... strings)
        {
            int iii = Integer.parseInt(strings[0]);
            postCropList.remove(iii);
            adapter.notifyItemRemoved(iii);

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.URL_POST_CROP_HISTORY,
                    new Response.Listener<String>()
                    {

                        @Override
                        public void onResponse(String response)
                        {
//                            progressDialog.dismiss();
//
////                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
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
                    params.put("f_id",strings[2]);
                    params.put("name", strings[3]);
                    params.put("quantity", strings[4]);
                    params.put("rate",strings[5]);
                    params.put("description",strings[6]);
                    params.put("time",strings[7]);
                    params.put("buyername",strings[8]);
                    params.put("buyernumber",strings[9]);
                    params.put("cropstatus",strings[10]);

                    return params;
                }
            };

            RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

            return null;
        }
    }


//    private void getPostCropList()
//    {
////        Toast.makeText(getApplicationContext(),"getPostCropList",Toast.LENGTH_LONG).show();
//
//        progressDialog.setMessage("Please Wait...");
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                Constants.URL_POST_CROP_LIST_BUYER,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response)
//                    {
//
//
//
//                        try
//                        {
//                            //converting the string to json array object
//                            JSONArray array = new JSONArray(response);
//                            boolean isfound = false;
//                            if(array.length() == 0)
//                            {
//                                setNothing();
//                            }
//                            for (int i = 0; i < array.length(); i++)
//                            {
//
//                                //getting product object from json array
//                                JSONObject jsonObject = array.getJSONObject(i);
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
//                                    postCropList.add(new PostCropBuyerInfo(
//                                                    jsonObject.getString("id"),
//                                                    jsonObject.getString("b_id"),
//                                                    jsonObject.getString("name"),
//                                                    jsonObject.getString("quantity"),
//                                                    jsonObject.getString("rate"),
//                                                    jsonObject.getString("description"),
//                                                    jsonObject.getString("time"),
//                                                    jsonObject.getString("buyername"),
//                                                    jsonObject.getString("buyernumber")
//                                            )
//                                    );
////                                }
//
//                            }
//
//                            recyclerView.setAdapter(adapter);
//                            progressDialog.dismiss();
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
//        );
//
//        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
//
//    }


//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private void insertPostCropHistory(
//            final int position,
//            final String crop_id,
//            final String b_id,
//            final String name,
//            final String quantity,
//            final String rate,
//            final String description,
//            final String time,
//            final String buyername,
//            final String buyernumber,
//            final String cropstatus)
//    {
//        int i = SharedPrefManagerFarmer.getInstance(this).getUserId();
//        final String f_id = String.valueOf(i);
//
//
//
//        final AlertDialog.Builder alert = new AlertDialog.Builder(SelectBuyerFarmerActivity.this);
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
//            textView.setText("Are you sure to remove the selected crop requirement ? it will not shown again to you.");
//        }
//
//        if(cropstatus.equals("add"))
//        {
//            buttonYES.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.colorOfAddButton));
//            textView.setText("Are you sure to add the selected crop requirement ?");
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
//                Constants.URL_POST_CROP_HISTORY,
//                new Response.Listener<String>()
//                {
//
//                    @Override
//                    public void onResponse(String response)
//                    {
//                        progressDialog.dismiss();
//
////                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
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
//
//                    }
//                }
//        )
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError
//            {
//                Map<String, String> params = new HashMap<>();
//                params.put("crop_id",crop_id);
//                params.put("f_id",f_id);
//                params.put("name", name);
//                params.put("quantity", quantity);
//                params.put("rate",rate);
//                params.put("description",description);
//                params.put("time",time);
//                params.put("buyername",buyername);
//                params.put("buyernumber",buyernumber);
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
//
//
//    }
//
//    private void readButtonState()
//    {
//        int i = SharedPrefManagerFarmer.getInstance(this).getUserId();
//        final String f_id = String.valueOf(i);
//
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                Constants.URL_READ_CROP_STATUS_POST_CROP,
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
//
//                                //getting product object from json array
//                                JSONObject jsonObject = array.getJSONObject(i);
//
//                                arrayStringId [i] = jsonObject.getString("crop_id");
//
//
//
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
//                params.put("f_id",f_id);
//                return params;
//            }
//        };
//
//
//        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
//
//    }

//    @Override
//    public void onBackPressed()
//    {
//        super.onBackPressed();
//        startActivity(new Intent(this,HomePageFarmerActivity.class));
//    }

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
                startActivity(new Intent(this,SelectPostCropListFarmerActivity.class));
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

        if(a.equals("buyer"))
        {
            Log.d("dddddif", "onResponse: ");

            t.setText("Buyers Post Nothing !!!");
        }
        else
        {
            t.setText("You Have See All Requirements !!!");
        }


    }

}
