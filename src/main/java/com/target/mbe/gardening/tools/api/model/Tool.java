package com.target.mbe.gardening.tools.api.model;

public class Tool
{
    String toolId;
    String color;
    Integer amount;
    String extra;

    public String getToolId()
    {
        return toolId;
    }

    public void setToolId(String toolId)
    {
        this.toolId = toolId;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public Integer getAmount()
    {
        return amount;
    }

    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }

    public String getExtra()
    {
        return extra;
    }

    public void setExtra(String extra)
    {
        this.extra = extra;
    }
}
