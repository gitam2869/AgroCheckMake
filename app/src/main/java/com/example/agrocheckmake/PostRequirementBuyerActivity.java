package com.example.agrocheckmake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class PostRequirementBuyerActivity extends AppCompatActivity implements View.OnClickListener
{

    private EditText editTextName;
    private EditText editTextQuantity;
    private EditText editTextRate;
    private EditText editTextDescription;
    private EditText editTextTime;

    private Button buttonPost;

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

        buttonPost = findViewById(R.id.idButtonPostRequirementPostRequirementBuyerActivity);
        buttonPost.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        if(view == buttonPost)
        {
           postCropRequirement();
        }
    }

    private void postCropRequirement()
    {
        final String name = editTextName.getText().toString().trim();
        final String quantity = editTextQuantity.getText().toString().trim();
        final String rate = editTextRate.getText().toString().trim();
        final String description = editTextDescription.getText().toString().trim();
        final String time = editTextTime.getText().toString().trim();

        int i = SharedPrefManagerFarmer.getInstance(this).getUserId();
        final String b_id = String.valueOf(i);

        final String buyername = SharedPrefManagerBuyer.getInstance(this).getUserName();
        final String buyernumber = SharedPrefManagerBuyer.getInstance(this).getUserMobile();

        if(name.length() == 0)
        {
            editTextName.setError("ENTER CROP NAME");
            return;
        }

        if(quantity.length() == 0)
        {
            editTextQuantity.setError("ENTER QUANTITY");
        }

        if(rate.length() == 0)
        {
            editTextRate.setError("ENTER RATE");
        }

        if(description.length() == 0)
        {
            editTextQuantity.setError("ENTER Description");
        }

        if(time.length() == 0)
        {
            editTextQuantity.setError("ENTER TIME");
        }

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_POST_CROP_BUYER,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplicationContext(), "Post Successfully Done", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(getApplicationContext(),HomePageBuyerActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
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
                params.put("b_id",b_id);
                params.put("name", name);
                params.put("quantity", quantity);
                params.put("rate",rate);
                params.put("description",description);
                params.put("time",time);
                params.put("buyername",buyername);
                params.put("buyernumber",buyernumber);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
