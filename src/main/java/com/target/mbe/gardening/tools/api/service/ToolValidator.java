package com.target.mbe.gardening.tools.api.service;

import com.target.mbe.gardening.tools.api.error.AppConstants;
import com.target.mbe.gardening.tools.api.error.AppException;
import com.target.mbe.gardening.tools.api.model.Tool;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.Response.Status;

public class ToolValidator
{
    static final List<String> lstValidColors = Arrays.asList("red", "blue", "brown");

    public static void validate(Tool t) throws AppException
    {
        String toolId = t.getToolId();
        if (toolId == null || toolId.isEmpty())
            throw new AppException(
                Status.BAD_REQUEST.getStatusCode(),
                AppConstants.MANDATORY_PARAMETER_MISSING,
                MessageFormat.format("Mandatory parameter ''{0}'' is missing.", "toolId"),
                MessageFormat.format("Mandatory parameter ''{0}'' is missing.", "toolId")
            );

        String color = t.getColor();
        if (color == null || color.isEmpty())
            throw new AppException(
                Status.BAD_REQUEST.getStatusCode(),
                AppConstants.MANDATORY_PARAMETER_MISSING,
                MessageFormat.format("Mandatory parameter ''{0}'' is missing.", "color"),
                MessageFormat.format("Mandatory parameter ''{0}'' is missing.", "color")
            );
        if (!lstValidColors.contains(color))
            throw new AppException(
                Status.BAD_REQUEST.getStatusCode(),
                AppConstants.INVALID_PARAMETER_VALUE,
                MessageFormat.format("Parameter ''{0}'' value ''{1}'' is invalid.", "color", color),
                MessageFormat.format("Parameter ''{0}'' value ''{1}'' is invalid. Color should be one of ''{2}'' .Check color please.", "color", color, lstValidColors.toString())
            );


        Integer amount = t.getAmount();
        if (amount == null)
            throw new AppException(
                Status.BAD_REQUEST.getStatusCode(),
                AppConstants.MANDATORY_PARAMETER_MISSING,
                MessageFormat.format("Mandatory parameter ''{0}'' is missing.", "amount"),
                MessageFormat.format("Mandatory parameter ''{0}'' is missing.", "amount")
            );
        if (amount < 0)
            throw new AppException(
                Status.BAD_REQUEST.getStatusCode(),
                AppConstants.INVALID_PARAMETER_VALUE,
                MessageFormat.format("Parameter ''{0}'' value ''{1}'' is invalid.", "amount", amount),
                MessageFormat.format("Parameter ''{0}'' value ''{1}'' is invalid. Amount must be greater than 0. Check amount please.", "amount", color)
            );
    }

}
