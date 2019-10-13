package com.target.mbe.gardening.tools.api.provider;

import com.target.mbe.gardening.tools.api.error.AppException;
import com.target.mbe.gardening.tools.api.error.ErrorMessage;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AppExceptionMapper implements ExceptionMapper<AppException>
{
    @Override
    public Response toResponse(AppException ex) {
        return Response.status(ex.getStatus())
            .entity(new ErrorMessage(ex))
            .type(MediaType.APPLICATION_JSON).
                build();
    }
}
