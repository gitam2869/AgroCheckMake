package com.example.agrocheckmake;

public class SellCropFarmerInfo
{
    private String id;
    private String image;
    private String name;
    private String quantity;
    private String rate;
    private String description;
    private String farmername;
    private String farmernumber;

    public SellCropFarmerInfo(
            String id,
            String image,
            String name,
            String quantity,
            String rate,
            String description,
            String farmername,
            String farmernumber
            )
    {
        this.id = id;
        this.image = image;
        this.name = name;
        this.quantity = quantity;
        this.rate = rate;
        this.description = description;
        this.farmername = farmername;
        this.farmernumber = farmernumber;
    }

    public String getId()
    {
        return id;
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
        return farmername;
    }

    public String getFarmernumber()
    {
        return farmernumber;
    }
}
