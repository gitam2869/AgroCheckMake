package com.example.agrocheckmake;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewAccountFarmerActivity extends AppCompatActivity
{
    private String[] dataArray = new String[6];

    private EditText editTextFullName;
    private EditText editTextEmail;
    private EditText editTextMobile;
    private EditText editTextAddresss;
    private EditText editTextLandAcres;
    private EditText editTextPassword;
    private Button buttonSubmit;
    private Spinner spinnerLandQuantity;

    private ProgressDialog progressDialog;

    public String stringLandAcresSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_farmer);

        getSupportActionBar().hide();

        editTextFullName = findViewById(R.id.idEditTextFullNameSignUPFarmer);
        editTextEmail = findViewById(R.id.idEditTextEmailSignUpFarmer);
        editTextMobile = findViewById(R.id.idEditTextMobileSignUPFarmer);
        editTextAddresss = findViewById(R.id.idEditTextAddressSignUPFarmer);
        editTextLandAcres = findViewById(R.id.idEditTextLandSignUpFarmer);
        editTextPassword = findViewById(R.id.idEditTextPasswordSignUpFarmer);
        buttonSubmit = findViewById(R.id.idButtonSubmitFarmer);
        spinnerLandQuantity = findViewById(R.id.idSpinnerLandSignUpFarmer);

        setUpSpinnerLandQuantity();

        progressDialog = new ProgressDialog(this);

        buttonSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                verifyData();

                if(
                        dataArray[0] != null &&
                        dataArray[1] !=null &&
                        dataArray[2] !=null &&
                        dataArray[3] != null &&
                        dataArray[4] !=null &&
                        dataArray[5] != null
                )
                {
                    NewAccountFarmerAsyncTask newAccountFarmerAsyncTask = new NewAccountFarmerAsyncTask();
                    newAccountFarmerAsyncTask.execute(dataArray);
                }
            }
        });


    }

    private class NewAccountFarmerAsyncTask extends AsyncTask<String, Void, String[]>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog.setMessage("Registering Farmer...");
            progressDialog.show();
        }

        @Override
        protected String[] doInBackground(final String... strings)
        {

            final boolean[] b = {true};

            final String[] s = new String[1];

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.URL_REGISTER_FARMER,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {

                            s[0] = response;

                            b[0] = false;

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
            )
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Map<String, String> params = new HashMap<>();
                    params.put("fullname", strings[0]);
                    params.put("email", strings[1]);
                    params.put("mobile", strings[2]);
                    params.put("address", strings[3]);
                    params.put("landacres", strings[4]);
                    params.put("password", strings[5]);

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

            return s;

        }

        @Override
        protected void onPostExecute(String[] strings)
        {
            super.onPostExecute(strings);

            progressDialog.dismiss();

            try
            {

                JSONArray jsonArray = new JSONArray(strings[0]);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                if(!jsonObject.getBoolean("error"))
                {
                    SharedPrefManagerFarmer.getInstance(getApplicationContext())
                            .userLogin(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("email"),
                                    jsonObject.getString("fullname"),
                                    jsonObject.getString("mobile"),
                                    jsonObject.getString("landacres"),
                                    jsonObject.getString("address")
                            );

                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            jsonObject.getString("message"),
                            Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.show();

                    startActivity(new Intent(getApplicationContext(),HomePageFarmerActivity.class));
                    finish();
                }
                else
                {
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            jsonObject.getString("message"),
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }


            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
    }

    private void verifyData()
    {
        final String fullName = editTextFullName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String mobile = editTextMobile.getText().toString().trim();
        final String address = editTextAddresss.getText().toString().trim();

        final String landAcres = editTextLandAcres.getText().toString().trim();

        final String password = editTextPassword.getText().toString().trim();

        if(fullName.length() == 0)
        {
            editTextFullName.setError("ENTER FULL NAME");
            return;
        }
        if(!fullName.matches("[a-zA-Z][a-zA-Z ]*"))
        {
            editTextFullName.setError("ENTER VALID FULL NAME");
            return;
        }

        if(email.length() == 0)
        {
            editTextEmail.setError("ENTER EMAIL ID");
            return;
        }
        if(!isValidEmail(email))
        {
            editTextEmail.setError("ENTER VALID EMAIL ID");
            return;
        }

        if(mobile.length() == 0)
        {
            editTextMobile.setError("ENTER MOBILE NUMBER");
            return;
        }
        if(mobile.length() < 10)
        {
            editTextMobile.setError("ENTER VALID MOBILE NUMBER");
            return;
        }
        if(mobile.length() > 10)
        {
            editTextMobile.setError("ENTER VALID MOBILE NUMBER");
            return;
        }

        if(address.length() == 0)
        {
            editTextAddresss.setError("ENTER ADDRESS");
            return;
        }

        if(landAcres.length() == 0)
        {
            editTextLandAcres.setError("ENTER Land Acres");
            return;
        }

        if(password.length() == 0)
        {
            editTextPassword.setError("ENTER PASSWORD");
            return;

        }
        if( password.length() < 7)
        {
            editTextPassword.setError("ENTER ATLEAST 8 CHARACTERS");
            return;
        }

        dataArray[0] = fullName;
        dataArray[1] = email;
        dataArray[2] = mobile;
        dataArray[3] = address;
        dataArray[4] = landAcres;
        dataArray[5] = password;

    }

//    private void createUserFarmer()
//    {
//        final String fullName = editTextFullName.getText().toString().trim();
//        final String email = editTextEmail.getText().toString().trim();
//        final String mobile = editTextMobile.getText().toString().trim();
//        final String address = editTextAddresss.getText().toString().trim();
//
//        final String landAcres = editTextLandAcres.getText().toString().trim();
//
//        final String password = editTextPassword.getText().toString().trim();
//
//        if(fullName.length() == 0)
//        {
//            editTextFullName.setError("ENTER FULL NAME");
//            return;
//        }
//        if(!fullName.matches("[a-zA-Z][a-zA-Z ]*"))
//        {
//            editTextFullName.setError("ENTER VALID FULL NAME");
//            return;
//        }
//
//        if(email.length() == 0)
//        {
//            editTextEmail.setError("ENTER EMAIL ID");
//            return;
//        }
//        if(email.length() > 0 && !isValidEmail(email))
//        {
//            editTextEmail.setError("ENTER VALID EMAIL ID");
//            return;
//        }
//
//        if(mobile.length() == 0)
//        {
//            editTextMobile.setError("ENTER MOBILE NUMBER");
//            return;
//        }
//        if((mobile.length() > 0 && mobile.length() < 10) || mobile.length() > 10)
//        {
//            editTextMobile.setError("ENTER VALID MOBILE NUMBER");
//            return;
//        }
//
//        if(address.length() == 0)
//        {
//            editTextAddresss.setError("ENTER ADDRESS");
//            return;
//        }
//
//        if(landAcres.length() == 0)
//        {
//            editTextLandAcres.setError("ENTER Land Acres");
//            return;
//        }
//
//        if(password.length() == 0)
//        {
//            editTextPassword.setError("ENTER PASSWORD");
//            return;
//
//        }
//        if( password.length() < 7)
//        {
//            editTextPassword.setError("ENTER ATLEAST 8 CHARACTERS");
//            return;
//        }
//
//        progressDialog.setMessage("Registering Farmer...");
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                Constants.URL_REGISTER_FARMER,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response)
//                    {
//                        progressDialog.dismiss();
//
//                        try
//                        {
//
//                            JSONArray jsonArray = new JSONArray(response);
//                            JSONObject jsonObject = jsonArray.getJSONObject(0);
//
//                            SharedPrefManagerFarmer.getInstance(getApplicationContext())
//                                    .userLogin(
//                                            jsonObject.getInt("id"),
//                                            jsonObject.getString("email"),
//                                            jsonObject.getString("fullname"),
//                                            jsonObject.getString("mobile"),
//                                            jsonObject.getString("landacres"),
//                                            jsonObject.getString("address")
//                                    );
//
//                        }
//                        catch (JSONException e)
//                        {
//                            e.printStackTrace();
//                        }
//
//                        startActivity(new Intent(getApplicationContext(),HomePageFarmerActivity.class));
//                        finish();
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
//                params.put("fullname", fullName);
//                params.put("email", email);
//                params.put("mobile",mobile);
//                params.put("address",address);
//                params.put("landacres",landAcres);
//                params.put("password",password);
//
//                return params;
//            }
//        };
//
//        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
//
//    }

    public static boolean isValidEmail(CharSequence target)
    {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void setUpSpinnerLandQuantity()
    {
        ArrayList<String> units = new ArrayList<>();

//        units.add(0,"Unit");
        units.add("Square Kilometer");
        units.add("Square Foot");
        units.add("Hectre");
        units.add("Square Meter");
        units.add("Gunta");
        units.add("Acre");
        //style the spinner

        ArrayAdapter arrayAdapterCropQuantity = new ArrayAdapter(this,R.layout.spinner_item,units);


        //Dropdown layouto style
        arrayAdapterCropQuantity.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        //attaching data adapter to spinner
        spinnerLandQuantity.setAdapter(arrayAdapterCropQuantity);

        spinnerLandQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                stringLandAcresSpinner = parent.getItemAtPosition(position).toString();

                SharedPrefManagerFarmer.getInstance(getApplicationContext())
                        .userFarmUnit(stringLandAcresSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


    }
}
