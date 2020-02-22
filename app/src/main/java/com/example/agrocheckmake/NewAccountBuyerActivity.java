package com.example.agrocheckmake;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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

public class NewAccountBuyerActivity extends AppCompatActivity implements View.OnClickListener
{

    private EditText editTextFullName;
    private EditText editTextEmail;
    private EditText editTextMobile;
    private EditText editTextAddresss;
    private EditText editTextPassword;
    private Button buttonSubmit;
    private Button buttonBack;

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
        buttonBack = findViewById(R.id.idButtonBackSignUpBuyer);

        buttonSubmit.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);

    }

    @Override
    public void onClick(View view)
    {
        if(view == buttonSubmit)
        {
            createUserBuyer();
        }

        if(view == buttonBack)
        {
            Intent intent = new Intent(this, LoginBuyerActivity.class);
            startActivity(intent);
        }
    }

    private void createUserBuyer()
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
        if(email.length() > 0 && !isValidEmail(email))
        {
            editTextEmail.setError("ENTER VALID EMAIL ID");
            return;
        }

        if(mobile.length() == 0)
        {
            editTextMobile.setError("ENTER MOBILE NUMBER");
            return;
        }
        if((mobile.length() > 0 && mobile.length() < 10) || mobile.length() > 10)
        {
            editTextMobile.setError("ENTER VALID MOBILE NUMBER");
            return;
        }

        if(address.length() == 0)
        {
            editTextAddresss.setError("ENTER ADDRESS");
        }

        if(password.length() == 0)
        {
            editTextPassword.setError("ENTER PASSWORD");
            return;

        }
        if(password.length() > 0 && password.length() < 8)
        {
            editTextPassword.setError("ENTER ATLEAST 8 CHARACTERS");
            return;
        }



        progressDialog.setMessage("Registering Buyer...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_REGISTER_BUYER,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        progressDialog.dismiss();

                        try
                        {
//                            JSONObject obj = new JSONObject(response);

                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject obj = jsonArray.getJSONObject(0);

                            SharedPrefManagerBuyer.getInstance(getApplicationContext())
                                    .userLogin(
                                            obj.getInt("id"),
                                            obj.getString("email"),
                                            obj.getString("fullname"),
                                            obj.getString("mobile"),
                                            obj.getString("address")
                                    );



                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        startActivity(new Intent(getApplicationContext(),HomePageBuyerActivity.class));
                        finish();
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
                params.put("fullname", fullName);
                params.put("email", email);
                params.put("mobile",mobile);
                params.put("address",address);
                params.put("password",password);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    public static boolean isValidEmail(CharSequence target)
    {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
