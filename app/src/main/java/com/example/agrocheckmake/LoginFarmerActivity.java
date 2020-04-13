package com.example.agrocheckmake;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.Gravity;
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

    private TextView textViewAppName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private ImageView imageViewGoogle;
    private Button buttonCreateNewAccount;
    private TextView textViewForgotPassword;


    private ProgressDialog progressDialog;

    public String stringEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_farmer);

        getSupportActionBar().hide();


        textViewAppName = findViewById(R.id.idTextViewAppNameLoginActivityFarmer);
        editTextEmail = findViewById(R.id.idEditTextEmailLoginActivityFarmer);
        editTextPassword = findViewById(R.id.idEditTextPasswordLoginActivityFarmer);
        buttonLogin = findViewById(R.id.idButtonLoginLoginActivityFarmer);
//        imageViewGoogle = findViewById(R.id.idImageViewGoogleSignUpLoginActivityFarmer);
        buttonCreateNewAccount = findViewById(R.id.idButtonCreateNewAccoutnLoginActivityFarmer);
        textViewForgotPassword = findViewById(R.id.idTextViewForgotPasswordLoginActivityFarmer);

        buttonLogin.setOnClickListener(this);
        buttonCreateNewAccount.setOnClickListener(this);
        textViewForgotPassword.setOnClickListener(this);

        String appName = "Agro CheckMake";
        SpannableString spannableString = new SpannableString(appName);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorOfAppName));
        ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(getResources().getColor(R.color.colorOfAppNameOtherCharacters));
        ForegroundColorSpan foregroundColorSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.colorOfAppNameOtherCharacters));

        spannableString.setSpan(foregroundColorSpan,10,11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(foregroundColorSpan1,0,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(foregroundColorSpan2,11,14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textViewAppName.setText(spannableString);

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

        if(view == textViewForgotPassword)
        {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            intent.putExtra("user", "Farmer");
            intent.putExtra("email", stringEmail);
            startActivity(intent);
            finish();
        }

    }

    private void farmerLogin()
    {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        stringEmail = email;
        if(email.length() == 0)
        {
            editTextEmail.setError("ENTER EMAIL ID");
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
                                response,
                                Toast.LENGTH_LONG
                        ).show();

                        try
                        {
//                            JSONObject obj = new JSONObject(response);

                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject obj = jsonArray.getJSONObject(0);

                            if(!obj.getBoolean("error"))
                            {
                                SharedPrefManagerFarmer.getInstance(getApplicationContext())
                                        .userLogin(
                                                obj.getInt("id"),
                                                obj.getString("email"),
                                                obj.getString("fullname"),
                                                obj.getString("mobile"),
                                                obj.getString("landacres"),
                                                obj.getString("address")
                                        );
                                Toast toast = Toast.makeText(
                                        getApplicationContext(),
                                        "Login Successfully",
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
