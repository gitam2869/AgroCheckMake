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
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class LoginFarmerActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private ImageView imageViewGoogle;
    private Button buttonCreateNewAccount;
    private Button buttonBack;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_farmer);

        getSupportActionBar().hide();

        editTextEmail = findViewById(R.id.idEditTextEmailLoginActivityFarmer);
        editTextPassword = findViewById(R.id.idEditTextPasswordLoginActivityFarmer);
        buttonLogin = findViewById(R.id.idButtonLoginLoginActivityFarmer);
//        imageViewGoogle = findViewById(R.id.idImageViewGoogleSignUpLoginActivityFarmer);
        buttonCreateNewAccount = findViewById(R.id.idButtonCreateNewAccoutnLoginActivityFarmer);
        buttonBack = findViewById(R.id.idButtonBackLoginActivityFarmer);

        buttonLogin.setOnClickListener(this);
        buttonCreateNewAccount.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

    }

    @Override
    public void onClick(View view)
    {
        if(view == buttonLogin)
        {
            farmerLogin();
        }

        if(view == buttonCreateNewAccount)
        {
            Intent intent = new Intent(this,NewAccountFarmerActivity.class);
            startActivity(intent);
        }

        if(view == buttonBack)
        {
            Intent intent = new Intent(this,CategoryActivity.class);
            startActivity(intent);
        }
    }

    private void farmerLogin()
    {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

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

        if(password.length() == 0)
        {
            editTextPassword.setError("ENTER PASSWORD");
            return;

        }

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN_FARMER,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        progressDialog.dismiss();

                        Toast.makeText(
                                getApplicationContext(),
                                "Invalid Username or Password",
                                Toast.LENGTH_LONG
                        ).show();

                        try
                        {
//                            JSONObject obj = new JSONObject(response);

                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject obj = jsonArray.getJSONObject(0);

                            SharedPrefManagerFarmer.getInstance(getApplicationContext())
                                    .userLogin(
                                            obj.getInt("id"),
                                            obj.getString("email"),
                                            obj.getString("fullname"),
                                            obj.getString("mobile"),
                                            obj.getString("landacres"),
                                            obj.getString("address")
                                    );
//                            Toast.makeText(getApplicationContext(), obj.getString("email"), Toast.LENGTH_LONG).show();
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Login Successfully",
                                    Toast.LENGTH_LONG
                            ).show();

                            startActivity(new Intent(getApplicationContext(),HomePageFarmerActivity.class));
                            finish();

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
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();

                params.put("email", email);
                params.put("password", password);
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
