package com.target.mbe.gardening.tools.api.controller;

import ch.qos.logback.classic.Logger;
import com.target.mbe.gardening.tools.api.error.AppConstants;
import com.target.mbe.gardening.tools.api.error.AppException;
import com.target.mbe.gardening.tools.api.error.InternalErrorException;
import com.target.mbe.gardening.tools.api.model.Tool;
import com.target.mbe.gardening.tools.api.response.DeleteToolResponse;
import com.target.mbe.gardening.tools.api.response.GetToolResponse;
import com.target.mbe.gardening.tools.api.response.ListToolsResponse;
import com.target.mbe.gardening.tools.api.service.ToolService;
import com.target.mbe.gardening.tools.api.service.ToolValidator;
import java.util.List;
import javax.ws.rs.DELETE;;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.LoggerFactory;

@Path("/")
public class Tools
{
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(Tools.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tools")
    public ListToolsResponse getTools() throws InternalErrorException
    {
        LOGGER.debug("getTools");

        List<String> lst =  ToolService.getToolIds();

        ListToolsResponse resp = new ListToolsResponse();
        for (String i: lst)
            resp.addToolId(i);
        resp.setCode(AppConstants.SUCCESS);

        LOGGER.debug("getTools is SUCCESS");
        return resp;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tools/{toolid}")
    public GetToolResponse getTool( @PathParam("toolid") String toolid )
        throws AppException, InternalErrorException
    {
        LOGGER.debug("getTool. toolid:" + toolid);

        GetToolResponse resp = new GetToolResponse();
        resp.setTool(ToolService.getTool(toolid));
        resp.setCode(AppConstants.SUCCESS);

        LOGGER.debug("getTool is SUCCESS. toolid:" + toolid);
        return resp;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tools")
    public GetToolResponse createTool(Tool tool) throws AppException, InternalErrorException
    {
        LOGGER.debug("createTool");

        ToolValidator.validate(tool);
        ToolService.createTool(tool);

        LOGGER.debug("createTool is SUCCESS. toolid:" + tool.getToolId());
        return getTool(tool.getToolId());
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tools/{toolid}")
    public GetToolResponse updateTool( @PathParam("toolid") String toolid, Tool tool)
        throws AppException, InternalErrorException
    {
        LOGGER.debug("updateTool. toolid:" + toolid);

        ToolValidator.validate(tool);
        ToolService.updateTool(toolid, tool);

        String toolidOld = tool.getToolId();
        if (toolidOld.equals(tool.getToolId()))
            LOGGER.debug("updateTool is SUCCESS. toolid:" + tool.getToolId());
        else
            LOGGER.debug("updateTool is SUCCESS. from :" + toolidOld + " to:" + tool.getToolId());

        return getTool(tool.getToolId());
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tools/{toolid}")
    public DeleteToolResponse deleteTool( @PathParam("toolid") String toolid)
        throws AppException, InternalErrorException
    {
        LOGGER.debug("deleteTool. toolid:" + toolid);

        ToolService.deleteTool(toolid);

        DeleteToolResponse resp = new DeleteToolResponse();
        resp.setCode(AppConstants.SUCCESS);

        LOGGER.debug("deleteTool is SUCCESS. toolid:" + toolid);
        return resp;
    }
}
