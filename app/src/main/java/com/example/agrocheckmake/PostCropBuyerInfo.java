package com.example.agrocheckmake;

public class PostCropBuyerInfo
{
    private String name;
    private String quantity;
    private String rate;
    private String description;
    private String time;
    private String buyername;
    private String buyernumber;

    public PostCropBuyerInfo(
            String name,
            String quantity,
            String rate,
            String description,
            String time,
            String buyername,
            String buyernumber
            )
    {
        this.name = name;
        this.quantity = quantity;
        this.rate = rate;
        this.description = description;
        this.time = time;
        this.buyername = buyername;
        this.buyernumber = buyernumber;
    }


    public String getName()
    {
        return name;
    }
    public String getQuantity()
    {
        return quantity;
    }

    public String getRate()
    {
        return rate;
    }
    public String getDescription()
    {
        return description;
    }

    public String getTime()
    {
        return time;
    }
    public String getBuyername()
    {
        return buyername;
    }

    public String getBuyernumber()
    {
        return buyernumber;
    }
}
