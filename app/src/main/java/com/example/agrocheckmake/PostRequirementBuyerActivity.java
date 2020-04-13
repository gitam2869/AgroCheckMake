package com.example.agrocheckmake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostRequirementBuyerActivity extends AppCompatActivity
{
    private String[] dataArray = new String[8];

    private EditText editTextName;
    private EditText editTextQuantity;
    private EditText editTextRate;
    private EditText editTextDescription;
    private EditText editTextTime;

    private Spinner spinnerCropQuantity;
    private Spinner spinnerCropRate;

    private Button buttonPost;

    private ProgressDialog progressDialog;

    public String stringCropQuanitySpinner;
    public String stringCropRateSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_requirement_buyer);


        editTextName = findViewById(R.id.idEditTextCropNamePostRequirementBuyerActivity);
        editTextQuantity = findViewById(R.id.idEditTextQuantityPostRequirementBuyerActivity);
        editTextRate = findViewById(R.id.idEditTextRatePostRequirementBuyerActivity);
        editTextDescription = findViewById(R.id.idEditTextCropDescriptionPostRequirementBuyerActivity);
        editTextTime = findViewById(R.id.idEditTextTimePeriodPostRequirementBuyerActivity);
        spinnerCropQuantity = findViewById(R.id.idSpinnerCropQuantityPostRequirementBuyerActivity);
        spinnerCropRate = findViewById(R.id.idSpinnerCropRatePostRequirementBuyerActivity);


//        setUpCropQuantitySpinner();
//        setUpCropRateSpinner();


        //spinner quanity

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.arrayOfUnitsQuantity,
                R.layout.spinner_item
//                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spinnerCropQuantity.setAdapter(adapter);

        spinnerCropQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                stringCropQuanitySpinner = parent.getItemAtPosition(position).toString();
//                Toast.makeText(getApplicationContext(),
//                        stringCropQuanitySpinner,
//                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        //spinner rate

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this,
                R.array.arrayOfUnitsRate,
                R.layout.spinner_item

//                android.R.layout.simple_spinner_item
        );

        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spinnerCropRate.setAdapter(adapter1);

        spinnerCropRate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                stringCropRateSpinner = parent.getItemAtPosition(position).toString();
//                Toast.makeText(getApplicationContext(),
//                        stringCropRateSpinner,
//                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        buttonPost = findViewById(R.id.idButtonPostRequirementPostRequirementBuyerActivity);
        progressDialog = new ProgressDialog(this);

        buttonPost.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                verifyData();

                if(
                        dataArray[1] != null &&
                        dataArray[2] !=null &&
                        dataArray[3] != null &&
                        dataArray[4] !=null &&
                        dataArray[5] != null
                )
                {
                    PostRequirementAsyncTask postRequirementAsyncTask = new PostRequirementAsyncTask();
                    postRequirementAsyncTask.execute(dataArray);
                }
            }
        });

    }


    private class PostRequirementAsyncTask extends AsyncTask<String, Void, boolean[]>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog.setMessage("Please wait...");
            progressDialog.show();

        }

        @Override
        protected boolean[] doInBackground(final String... strings)
        {
            final boolean[] b = {true};

            final boolean[] state = new boolean[1];

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.URL_POST_CROP_BUYER,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            try
                            {
                                JSONObject jsonObject = new JSONObject(response);

                                state[0] = jsonObject.getBoolean("error");
                                b[0] = false;

                            }

                            catch (JSONException e)
                            {
                                progressDialog.dismiss();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            b[0] = false;
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
                    params.put("b_id",strings[0]);
                    params.put("name", strings[1]);
                    params.put("quantity", strings[2]);
                    params.put("rate",strings[3]);
                    params.put("description",strings[4]);
                    params.put("time",strings[5]);
                    params.put("buyername",strings[6]);
                    params.put("buyernumber",strings[7]);

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

            return state;
        }

        @Override
        protected void onPostExecute(boolean[] booleans)
        {
            super.onPostExecute(booleans);
            progressDialog.dismiss();
            if(!booleans[0])
            {
                if(!PostRequirementBuyerActivity.this.isFinishing())
                {
                    Toast.makeText(getApplicationContext(), "Post Upload Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), HomePageBuyerActivity.class));
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Post Not Upload Successfully", Toast.LENGTH_LONG).show();

            }
        }
    }

    private void verifyData()
    {
        int i = SharedPrefManagerFarmer.getInstance(this).getUserId();
        final String b_id = String.valueOf(i);


        final String name = editTextName.getText().toString().trim();

        String tempQuantity = editTextQuantity.getText().toString().trim();
        String quantity1 = tempQuantity +" "+stringCropQuanitySpinner;
        final String quantity = quantity1;

        String tempRate = editTextRate.getText().toString().trim();
        String rate1 = tempRate +" "+ stringCropRateSpinner;
        final String rate = rate1;

        final String description = editTextDescription.getText().toString().trim();
        final String time = editTextTime.getText().toString().trim();



        final String buyername = SharedPrefManagerBuyer.getInstance(this).getUserName();
        final String buyernumber = SharedPrefManagerBuyer.getInstance(this).getUserMobile();

        if(name.length() == 0)
        {
            editTextName.setError("ENTER CROP NAME");
            return ;
        }

        if(tempQuantity.length() == 0)
        {
            editTextQuantity.setError("ENTER QUANTITY");
            return ;
        }

        if(tempRate.length() == 0)
        {
            editTextRate.setError("ENTER RATE");
            return ;
        }

        if(description.length() == 0)
        {
            editTextDescription.setError("ENTER Description");
            return ;
        }

        if(time.length() == 0)
        {
            editTextTime.setError("ENTER Months");
            return ;
        }


        dataArray[0] = b_id;
        dataArray[1] = name;
        dataArray[2] = quantity;
        dataArray[3] = rate;
        dataArray[4] = description;
        dataArray[5] = time;
        dataArray[6] = buyername;
        dataArray[7] = buyernumber;

    }


    //    private void setUpCropQuantitySpinner()
//    {
//        List<String> units = new ArrayList<>();
//
////        units.add(0,"Unit");
//        units.add("Kg");
//        units.add("Quintle");
//        units.add("Tonne");
//
//        //style the spinner
//
//        ArrayAdapter<String> arrayAdapterCropQuantity = new ArrayAdapter(this,R.layout.spinner_item,units);
//
//
//        //Dropdown layouto style
//        arrayAdapterCropQuantity.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
//
//        //attaching data adapter to spinner
//        spinnerCropQuantity.setAdapter(arrayAdapterCropQuantity);
//
//        spinnerCropQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//        {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//            {
//                stringCropQuanitySpinner = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent)
//            {
//
//            }
//        });
//
//
//    }
//
//    private void setUpCropRateSpinner()
//    {
//        List<String> units = new ArrayList<>();
//
////        units.add(0,"Unit");
//        units.add("Rs/Kg");
//        units.add("Rs/Quintle");
//        units.add("Rs/Tonne");
//        units.add("Rs/Dozen");
//
//
//        //style the spinner
//
//        ArrayAdapter<String> arrayAdapterCropQuantity = new ArrayAdapter(this,R.layout.spinner_item,units);
//
//
//        //Dropdown layouto style
//        arrayAdapterCropQuantity.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
//
//        //attaching data adapter to spinner
//        spinnerCropRate.setAdapter(arrayAdapterCropQuantity);
//
//        spinnerCropRate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//        {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//            {
//                stringCropRateSpinner = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent)
//            {
//
//            }
//        });
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
                startActivity(new Intent(this,PostedCropRequirementListBuyerActivity.class));
                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }


//    @Override
//    public void onClick(View view)
//    {
//        if(view == buttonPost)
//        {
//           postCropRequirement();
//        }
//    }

//    private void postCropRequirement()
//    {
//        final String name = editTextName.getText().toString().trim();
//
////        final String quantity = editTextQuantity.getText().toString().trim();
////        final String rate = editTextRate.getText().toString().trim();
//
//        String tempQuantity = editTextQuantity.getText().toString().trim();
//        String quantity1 = tempQuantity +" "+stringCropQuanitySpinner;
//        final String quantity = quantity1;
//
//        String tempRate = editTextRate.getText().toString().trim();
//        String rate1 = tempRate +" "+ stringCropRateSpinner;
//        final String rate = rate1;
//
//        final String description = editTextDescription.getText().toString().trim();
//        final String time = editTextTime.getText().toString().trim();
//
//        int i = SharedPrefManagerFarmer.getInstance(this).getUserId();
//        final String b_id = String.valueOf(i);
//
//        final String buyername = SharedPrefManagerBuyer.getInstance(this).getUserName();
//        final String buyernumber = SharedPrefManagerBuyer.getInstance(this).getUserMobile();
//
//        if(name.length() == 0)
//        {
//            editTextName.setError("ENTER CROP NAME");
//            return;
//        }
//
//        if(tempQuantity.length() == 0)
//        {
//            editTextQuantity.setError("ENTER QUANTITY");
//            return;
//        }
//
//        if(tempRate.length() == 0)
//        {
//            editTextRate.setError("ENTER RATE");
//            return;
//        }
//
//        if(description.length() == 0)
//        {
//            editTextDescription.setError("ENTER Description");
//            return;
//        }
//
//        if(time.length() == 0)
//        {
//            editTextTime.setError("ENTER Months");
//            return;
//        }
//
//        progressDialog.setMessage("Please wait...");
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                Constants.URL_POST_CROP_BUYER,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response)
//                    {
//                        try
//                        {
//                            JSONObject jsonObject = new JSONObject(response);
//
//
//                            if(!jsonObject.getBoolean("error"))
//                            {
//                                startActivity(new Intent(getApplicationContext(), HomePageBuyerActivity.class));
//                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                            }
//                            progressDialog.dismiss();
//
//                        }
//
//                        catch (JSONException e)
//                        {
//                            progressDialog.dismiss();
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
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
//                params.put("b_id",b_id);
//                params.put("name", name);
//                params.put("quantity", quantity);
//                params.put("rate",rate);
//                params.put("description",description);
//                params.put("time",time);
//                params.put("buyername",buyername);
//                params.put("buyernumber",buyernumber);
//
//                return params;
//            }
//        };
//
//        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
//    }
}
