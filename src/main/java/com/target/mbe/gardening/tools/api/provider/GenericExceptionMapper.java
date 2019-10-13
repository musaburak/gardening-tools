package com.target.mbe.gardening.tools.api.provider;

import com.target.mbe.gardening.tools.api.error.AppConstants;
import com.target.mbe.gardening.tools.api.error.ErrorMessage;
import com.target.mbe.gardening.tools.api.error.InternalErrorException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable>
{

    @Override
    public Response toResponse(Throwable ex)
    {
        ErrorMessage errorMessage = new ErrorMessage();
        setHttpStatusAndCode(ex, errorMessage);
        errorMessage.setMessage(ex.getMessage());
        StringWriter errorStackTrace = new StringWriter();
        ex.printStackTrace(new PrintWriter(errorStackTrace));
        errorMessage.setDeveloperMessage(errorStackTrace.toString());

        return Response.status(errorMessage.getStatus())
            .entity(errorMessage)
            .type(MediaType.APPLICATION_JSON)
            .build();
    }

    private void setHttpStatusAndCode(Throwable ex, ErrorMessage errorMessage)
    {
        if (ex instanceof WebApplicationException)
        {
            errorMessage.setStatus(((WebApplicationException)ex).getResponse().getStatus());
            errorMessage.setCode(AppConstants.GENERIC_APP_ERROR_CODE);
        }
        else if (ex instanceof InternalErrorException)
        {
            errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()); //defaults to internal server error 500
            errorMessage.setCode(AppConstants.GENERIC_APP_INTERNAL_ERROR);
        }
        else
        {
            errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()); //defaults to internal server error 500
            errorMessage.setCode(AppConstants.GENERIC_APP_ERROR_CODE);
        }
    }
}

