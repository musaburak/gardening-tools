package com.target.mbe.gardening.tools.api.response;

import com.target.mbe.gardening.tools.api.model.ToolId;
import java.util.ArrayList;
import java.util.List;

public class ListToolsResponse extends GenericResponse
{
    private List<ToolId> tools = new ArrayList<ToolId>();

    public List<ToolId> getTools()
    {
        return tools;
    }

    public void setFolders(List<ToolId> tools)
    {
        this.tools = tools;
    }

    public void addToolId(String id)
    {
        ToolId ti = new ToolId();
        ti.setId(id);
        tools.add(ti);
    }

}
