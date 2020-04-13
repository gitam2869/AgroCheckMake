package com.example.agrocheckmake;

public class PostCropBuyerInfo
{
    private String id;
    private String b_id;
    private String name;
    private String quantity;
    private String rate;
    private String description;
    private String time;
    private String buyername;
    private String buyernumber;

    public PostCropBuyerInfo(
            String id,
            String b_id,
            String name,
            String quantity,
            String rate,
            String description,
            String time,
            String buyername,
            String buyernumber
            )
    {
        this.id = id;
        this.b_id = b_id;
        this.name = name;
        this.quantity = quantity;
        this.rate = rate;
        this.description = description;
        this.time = time;
        this.buyername = buyername;
        this.buyernumber = buyernumber;
    }

    public String getId()
    {
        return id;
    }
    public String getB_Id()
    {
        return b_id;
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
