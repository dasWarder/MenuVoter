package com.example.util;

import com.example.customer.Customer;
import com.example.exception.CustomerNotFoundException;
import com.example.exception.EntityNotFoundException;
import com.example.exception.MenuNotFoundException;
import com.example.exception.RestaurantNotFoundException;
import com.example.menu.Menu;
import com.example.restaurant.Restaurant;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class OptionalValidation {

    public static final String MENU_NOT_FOUND_EXCEPTION_MESSAGE = "The menu hasn't been found";
    public static final String RESTAURANT_NOT_FOUND_EXCEPTION_MESSAGE = "The restaurant hasn't been found";
    public static final String CUSTOMER_NOT_FOUND_EXCEPTION_MESSAGE = "The customer hasn't been found";
    public static final String OBJECT_NOT_FOUND_EXCEPTION_MESSAGE = "The object of class %s hasn't been found";

    public static <T> T checkOptionalAndReturnOrThrowException(Optional<T> validationObject, Class validateObjectClass)
                                                                                             throws EntityNotFoundException {
        if (validationObject.isPresent()) {

            log.info("Receiving an object from the optional");
            T validObject = validationObject.get();

            return validObject;
        }

        log.info("The object not found exception occurred for the class = {}",
                                                                              validateObjectClass.getName());
        EntityNotFoundException exceptionFromObject = exceptionQualifier(validateObjectClass.getName());

        throw exceptionFromObject;
    }





    private static EntityNotFoundException exceptionQualifier(String validateObjectClass) {

        if(validateObjectClass.equals(Menu.class.getName())) {

            return new MenuNotFoundException(MENU_NOT_FOUND_EXCEPTION_MESSAGE);

        } else if (validateObjectClass.equals(Restaurant.class.getName())) {

            return new RestaurantNotFoundException(RESTAURANT_NOT_FOUND_EXCEPTION_MESSAGE);

        } else if (validateObjectClass.equals(Customer.class.getName())) {

            return new CustomerNotFoundException(CUSTOMER_NOT_FOUND_EXCEPTION_MESSAGE);

        } else {

            throw new RuntimeException(String.format(OBJECT_NOT_FOUND_EXCEPTION_MESSAGE, validateObjectClass));

        }
    }
}
