package com.smartfinance.validation;

import com.smartfinance.dto.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
    implements ConstraintValidator<PasswordMatches, RegisterRequest> {


    @Override
    public boolean isValid(RegisterRequest request,
                           ConstraintValidatorContext context){

        if(request.getPassword() == null || request.getConfirmPassword() == null){
            return true;
        }

        if(!request.getPassword().equals(request.getConfirmPassword())){

            context.disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate("Passwords do not match")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }
}
