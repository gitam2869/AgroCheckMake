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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewAccountBuyerActivity extends AppCompatActivity
{
    private String[] dataArray = new String[5];

    private EditText editTextFullName;
    private EditText editTextEmail;
    private EditText editTextMobile;
    private EditText editTextAddresss;
    private EditText editTextPassword;
    private Button buttonSubmit;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_buyer);

        getSupportActionBar().hide();

        editTextFullName = findViewById(R.id.idEditTextFullNameSignUPBuyer);
        editTextEmail = findViewById(R.id.idEditTextEmailSignUpBuyer);
        editTextMobile = findViewById(R.id.idEditTextMobileSignUPBuyer);
        editTextAddresss = findViewById(R.id.idEditTextAddressSignUPBuyer);
        editTextPassword = findViewById(R.id.idEditTextPasswordSignUPBuyer);
        buttonSubmit = findViewById(R.id.idButtonSubmitBuyer);


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
                        dataArray[4] !=null
                )
                {
                    NewAccountBuyerAsyncTask newAccountBuyerAsyncTask = new NewAccountBuyerAsyncTask();
                    newAccountBuyerAsyncTask.execute(dataArray);
                }
            }
        });


    }

    private class NewAccountBuyerAsyncTask extends AsyncTask<String, Void, String []>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog.setMessage("Registering Buyer...");
            progressDialog.show();
        }

        @Override
        protected String[] doInBackground(final String... strings)
        {
            final boolean[] b = {true};

            final String[] s = new String[1];

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.URL_REGISTER_BUYER,
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
                    params.put("password", strings[4]);

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
//                            JSONObject obj = new JSONObject(response);

                JSONArray jsonArray = new JSONArray(strings[0]);
                JSONObject obj = jsonArray.getJSONObject(0);

                if(!obj.getBoolean("error"))
                {
                    SharedPrefManagerBuyer.getInstance(getApplicationContext())
                            .userLogin(
                                    obj.getInt("id"),
                                    obj.getString("email"),
                                    obj.getString("fullname"),
                                    obj.getString("mobile"),
                                    obj.getString("address")
                            );

                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();

                    startActivity(new Intent(getApplicationContext(),HomePageBuyerActivity.class));
                    finish();
                }
                else
                {
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            obj.getString("message"),
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
        if(mobile.length() < 10 || mobile.length() > 10)
        {
            editTextMobile.setError("ENTER VALID MOBILE NUMBER");
            return;
        }

        if(address.length() == 0)
        {
            editTextAddresss.setError("ENTER ADDRESS");
            return;
        }

        if(password.length() == 0)
        {
            editTextPassword.setError("ENTER PASSWORD");
            return;

        }
        if(password.length() < 8)
        {
            editTextPassword.setError("ENTER ATLEAST 8 CHARACTERS");
            return;
        }

        dataArray[0] = fullName;
        dataArray[1] = email;
        dataArray[2] = mobile;
        dataArray[3] = address;
        dataArray[4] = password;

    }

//    private void createUserBuyer()
//    {
//        final String fullName = editTextFullName.getText().toString().trim();
//        final String email = editTextEmail.getText().toString().trim();
//        final String mobile = editTextMobile.getText().toString().trim();
//        final String address = editTextAddresss.getText().toString().trim();
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
//        }
//
//        if(password.length() == 0)
//        {
//            editTextPassword.setError("ENTER PASSWORD");
//            return;
//
//        }
//        if(password.length() < 7)
//        {
//            editTextPassword.setError("ENTER ATLEAST 8 CHARACTERS");
//            return;
//        }
//
//
//
//        progressDialog.setMessage("Registering Buyer...");
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                Constants.URL_REGISTER_BUYER,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response)
//                    {
//                        progressDialog.dismiss();
//
//                        try
//                        {
////                            JSONObject obj = new JSONObject(response);
//
//                            JSONArray jsonArray = new JSONArray(response);
//                            JSONObject obj = jsonArray.getJSONObject(0);
//
//                            SharedPrefManagerBuyer.getInstance(getApplicationContext())
//                                    .userLogin(
//                                            obj.getInt("id"),
//                                            obj.getString("email"),
//                                            obj.getString("fullname"),
//                                            obj.getString("mobile"),
//                                            obj.getString("address")
//                                    );
//
//
//
//                        }
//                        catch (JSONException e)
//                        {
//                            e.printStackTrace();
//                        }
//
//                        startActivity(new Intent(getApplicationContext(),HomePageBuyerActivity.class));
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
}
