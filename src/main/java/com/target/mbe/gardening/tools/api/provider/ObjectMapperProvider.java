package com.target.mbe.gardening.tools.api.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper>
{
    final ObjectMapper defaultObjectMapper;

    public ObjectMapperProvider()
    {
        defaultObjectMapper = createDefaultMapper();
    }

    @Override
    public ObjectMapper getContext(Class<?> type)
    {
        return defaultObjectMapper;
    }

    private static ObjectMapper createDefaultMapper()
    {
        final ObjectMapper result = new ObjectMapper();
        result.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

        return result;
    }
}