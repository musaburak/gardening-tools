package com.target.mbe.gardening.tools.api.response;

import com.target.mbe.gardening.tools.api.model.Tool;

public class GetToolResponse extends GenericResponse
{
    private Tool tool;

    public Tool getTool()
    {
        return tool;
    }

    public void setTool(Tool tool)
    {
        this.tool = tool;
    }
}
