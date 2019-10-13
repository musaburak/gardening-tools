package com.target.mbe.gardening.tools.api.provider;

import com.target.mbe.gardening.tools.api.error.ErrorMessage;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException>
{

    @Override
    public Response toResponse(NotFoundException ex)
    {
        return Response.status(ex.getResponse().getStatus())
            .entity(new ErrorMessage(ex))
            .type(MediaType.APPLICATION_JSON)
            .build();
    }

}
