package com.example.agrocheckmake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ExperContactsActivity extends AppCompatActivity
{
    private ExpertContactAdapter adapter;

    //a list to store all the prs users name
    List<ExpertContactInfo> sellCropList;

    //the recyclerview
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exper_contacts);

        //initializing the postcrop list
        sellCropList = new ArrayList<>();

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.idRecycleViewExpertContactsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ExpertContactAdapter(sellCropList);

        sellCropList.add(new ExpertContactInfo(
                "Samarth Agro Clinic, Kothrud.",
                "+919922117262",
                "+919823104380"));
        sellCropList.add(new ExpertContactInfo(
                "Rayat Agro, Pune Satara Road.",
                "+917620385539",
                "+919325077709"));
        sellCropList.add(new ExpertContactInfo(
                "Abhinav Farmers Club, Hinjawadi.",
                "+919422005569",
                "+9196505577"));
        sellCropList.add(new ExpertContactInfo(
                "Krushi Vikas Kendra, Bhosari.",
                "+919011391260",
                "-"));
        sellCropList.add(new ExpertContactInfo(
                "Krishi Vigyan Kendra, Junnar.",
                "+917028779777",
                "-"));
        sellCropList.add(new ExpertContactInfo(
                "Maharashtra Sandal Growar Farmers Producer Company.",
                "+917038453333",
                "+919922364333"));

        recyclerView.setAdapter(adapter);
    }
}
