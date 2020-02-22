package com.example.agrocheckmake;

public class SellCropFarmerInfo
{
    private String image;
    private String name;
    private String quantity;
    private String rate;
    private String description;
    private String buyername;
    private String buyernumber;

    public SellCropFarmerInfo(
            String image,
            String name,
            String quantity,
            String rate,
            String description,
            String buyername,
            String buyernumber
            )
    {
        this.image = image;
        this.name = name;
        this.quantity = quantity;
        this.rate = rate;
        this.description = description;
        this.buyername = buyername;
        this.buyernumber = buyernumber;
    }

    public String getImage()
    {
        return image;
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

    public String getFarmername()
    {
        return buyername;
    }

    public String getFarmernumber()
    {
        return buyernumber;
    }
}
