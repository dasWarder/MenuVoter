package com.example.util;

import java.util.Arrays;
import static org.springframework.util.Assert.notNull;

public class ParamValidationUtil {

    private static final String VALIDATE_NOT_NULL_EXCEPTION_MESSAGE = "The %s must be not NULL";

    public static void validateParametersNotNull(Object ... parametersForValidation) {

        Arrays.stream(parametersForValidation)
                                               .forEach(parameter -> {
                                                        checkParameterNotNull(parameter);
                                               });
    }





    private static void checkParameterNotNull(Object parameterForValidation) {
        ValidateObject validationParameter = convertParameterToValidateObject(parameterForValidation);

        notNull(validationParameter.getParameter(),
                generateExceptionMessageString(validationParameter.getParameterName()));
    }



    private static ValidateObject convertParameterToValidateObject(Object parameterForConvertion) {
        return new ValidateObject(parameterForConvertion, parameterForConvertion
                .getClass()
                .getName());
    }


    private static String generateExceptionMessageString(String parameterName) {
        return String.format(VALIDATE_NOT_NULL_EXCEPTION_MESSAGE, parameterName);
    }
}
