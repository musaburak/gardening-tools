package com.target.mbe.gardening.tools.api.error;

public class InternalErrorException extends Exception
{
    public InternalErrorException(Exception e)
    {
        super(e);
    }
}
