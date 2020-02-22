package com.example.agrocheckmake;

public class ExpertContactInfo
{
    private String name;
    private String m1;
    private String m2;

    public ExpertContactInfo(
            String name,
            String m1,
            String m2
            )
    {
        this.name = name;
        this.m1 = m1;
        this.m2 = m2;
    }


    public String getName()
    {
        return name;
    }

    public String getM1()
    {
        return m1;
    }

    public String getM2()
    {
        return m2;
    }

}
