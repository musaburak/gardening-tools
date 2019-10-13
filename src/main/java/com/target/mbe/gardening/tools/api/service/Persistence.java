package com.target.mbe.gardening.tools.api.service;

import com.target.mbe.gardening.tools.api.error.InternalErrorException;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

public interface Persistence
{
    public List<String> getIds() throws InternalErrorException;

    public boolean isExists(String toolid) throws InternalErrorException;

    public Dictionary<String, Object> getTool(String toolid)
        throws InternalErrorException;

    public void createTool(String toolid, String color, int amount, String extra)
        throws InternalErrorException;

    public void updateTool(String toolidOld, String toolidNew, String color, int amount, String extra)
        throws InternalErrorException;

    public void deleteTool(String toolid) throws InternalErrorException;

}
