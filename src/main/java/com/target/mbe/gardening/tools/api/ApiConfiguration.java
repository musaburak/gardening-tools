package com.target.mbe.gardening.tools.api;

import com.target.mbe.gardening.tools.api.controller.Tools;
import com.target.mbe.gardening.tools.api.provider.AppExceptionMapper;
import com.target.mbe.gardening.tools.api.provider.GenericExceptionMapper;
import com.target.mbe.gardening.tools.api.provider.NotFoundExceptionMapper;
import com.target.mbe.gardening.tools.api.provider.ObjectMapperProvider;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class ApiConfiguration
{
    static public ResourceConfig getConfiguration()
    {
        ResourceConfig rc = new ResourceConfig();

        rc.register(AppExceptionMapper.class);
        rc.register(NotFoundExceptionMapper.class);
        rc.register(GenericExceptionMapper.class);
        rc.register(ObjectMapperProvider.class);
        rc.register(JacksonFeature.class);

        rc.register(Tools.class);

        return rc;
    }
}
