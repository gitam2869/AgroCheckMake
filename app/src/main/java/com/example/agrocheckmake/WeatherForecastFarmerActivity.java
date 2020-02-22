package com.example.agrocheckmake;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WeatherForecastFarmerActivity extends AppCompatActivity
{

    TextView textViewDate;
    TextView textViewSun;
    TextView textViewHumidity;
    TextView textViewClouds;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast_farmer);

        textViewSun = findViewById(R.id.idTextViewSunFarmerActivity);
        textViewHumidity = findViewById(R.id.idTextViewHumidityFarmerActivity);
        textViewClouds = findViewById(R.id.idTextViewCloudsFarmerActivity);
        textViewDate = findViewById(R.id.idTextViewDateFarmerActivity);

        progressDialog = new ProgressDialog(this);

        updateWeatherForecast();

    }

    private void updateWeatherForecast()
    {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = "http://api.openweathermap.org/data/2.5/weather?q=Manchar,Maharashtra&appid=a36e3ad5623ebc5109a6643be0416b01";

        JsonObjectRequest jor = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        progressDialog.hide();
                        try
                        {
                            JSONObject main_object = response.getJSONObject("main");

                            JSONArray array = response.getJSONArray("weather");

                            JSONObject object = array.getJSONObject(0);

                            Toast.makeText(getApplicationContext(),String.valueOf(main_object.getDouble("humidity")),Toast.LENGTH_LONG).show();

                            String temp = String.valueOf(main_object.getDouble("temp"));
                            String humidity = String.valueOf(main_object.getDouble("humidity"));
                            String description = object.getString("description");

                            double temp_int = Double.parseDouble(temp);
                            double centi = temp_int-273.15;
                            centi = Math.round(centi);
//                            int i = (int)centi;



                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            String formatted_date = sdf.format(calendar.getTime());

                            textViewDate.setText("Date : "+formatted_date);
                            textViewSun.setText(String.valueOf(centi)+" C");
                            textViewHumidity.setText(humidity+" %");
                            textViewClouds.setText(description);
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
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);
    }
}
