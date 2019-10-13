package com.target.mbe.gardening.tools.api.service;

import com.target.mbe.gardening.tools.api.error.AppConstants;
import com.target.mbe.gardening.tools.api.error.AppException;
import com.target.mbe.gardening.tools.api.error.InternalErrorException;
import com.target.mbe.gardening.tools.api.model.Tool;
import com.target.mbe.gardening.tools.api.service.sqlite.SqlitePersistence;
import java.text.MessageFormat;
import java.util.Dictionary;
import java.util.List;
import javax.ws.rs.core.Response.Status;

public class ToolService
{
    //TODO
    static Persistence persistence = new SqlitePersistence();

    static public List<String> getToolIds() throws InternalErrorException
    {
        return persistence.getIds();
    }

    static public Tool getTool(String toolid) throws AppException, InternalErrorException
    {
        if (!persistence.isExists(toolid))
                throw new AppException(
                    Status.NOT_FOUND.getStatusCode(),
                    AppConstants.TOOL_NOT_FOUND,
                    MessageFormat.format("Tool ''{0}'' is not found.", toolid),
                    MessageFormat.format("Tool ''{0}'' is not found. Please check tool id and try again.", toolid)
                );

        Dictionary dicTool = persistence.getTool(toolid);

        Tool t = new Tool();
        t.setToolId(toolid);
        t.setColor((String) dicTool.get("color"));
        t.setAmount((Integer) dicTool.get("amount"));
        t.setExtra((String) dicTool.get("extra"));

        return t;
    }

    static public void createTool(Tool tool) throws AppException, InternalErrorException
    {
        if (persistence.isExists(tool.getToolId()))
            throw new AppException(
                Status.BAD_REQUEST.getStatusCode(),
                AppConstants.TOOL_ALREADY_EXISTS,
                MessageFormat.format("Tool ''{0}'' is already exists.", tool.getToolId()),
                MessageFormat.format("Tool ''{0}'' is already exists. Please check tool id and try again.", tool.getToolId())
            );

        persistence.createTool(tool.getToolId(), tool.getColor(), tool.getAmount(), tool.getExtra());
    }

    static public void updateTool(String toolid, Tool tool)
        throws AppException, InternalErrorException
    {
        if (!persistence.isExists(toolid))
            throw new AppException(
                Status.NOT_FOUND.getStatusCode(),
                AppConstants.TOOL_NOT_FOUND,
                MessageFormat.format("Tool ''{0}'' is not found.", toolid),
                MessageFormat.format("Tool ''{0}'' is not found. Please check tool id and try again.", toolid)
            );


        if (!toolid.equals(tool.getToolId()) && persistence.isExists(tool.getToolId()))
            throw new AppException(
                Status.BAD_REQUEST.getStatusCode(),
                AppConstants.TOOL_ALREADY_EXISTS,
                MessageFormat.format("Tool ''{0}'' is already exists.", tool.getToolId()),
                MessageFormat.format("Tool ''{0}'' is already exists. Please check tool id and try again.", tool.getToolId())
            );


        persistence.updateTool(toolid, tool.getToolId(), tool.getColor(), tool.getAmount(), tool.getExtra());

    }

    static public void deleteTool(String toolid) throws AppException, InternalErrorException
    {
        if (!persistence.isExists(toolid))
            throw new AppException(
                Status.NOT_FOUND.getStatusCode(),
                AppConstants.TOOL_NOT_FOUND,
                MessageFormat.format("Tool ''{0}'' is not found.", toolid),
                MessageFormat.format("Tool ''{0}'' is not found. Please check tool id and try again.", toolid)
            );

        persistence.deleteTool(toolid);
    }
}
