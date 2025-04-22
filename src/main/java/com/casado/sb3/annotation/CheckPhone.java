package com.casado.sb3.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Phone number validation annotation.
 * <p>
 * This annotation validates that a string represents a valid phone number.
 * It supports both national and international formats.
 * <p>
 * Configuration options:
 * <ul>
 *     <li>{@code length} - The expected length of the phone number (digits only, excluding country code)</li>
 *     <li>{@code allowInternational} - Whether to allow international format with country code (e.g., +34612345678)</li>
 * </ul>
 * <p>
 * Supported types are:
 * <ul>
 *     <li>{@code String}</li>
 * </ul>
 *
 * @author ACasado666
 * @gitHub https://github.com/acasado666
 * @website https://acasado666.com
 * @created 2024-09-02
 * @updated 2025-04-22
 */

@Documented
@Constraint(validatedBy = CheckPhoneValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPhone {
    /**
     * The expected length of the phone number (digits only, excluding country code).
     * Default is 9 digits which is common in many countries.
     */
    int length() default 9;
    
    /**
     * Whether to allow international format with country code (e.g., +34612345678).
     * When true, validates both national and international formats.
     * When false, only validates national format.
     */
    boolean allowInternational() default true;

    /**
     * Error message template.
     * Available placeholders:
     * {length} - the configured length
     */
    String message() default "Invalid phone number. Must contain exactly {length} digits" +
            " or be in international format if allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class CheckPhoneValidator implements ConstraintValidator<CheckPhone, String> {
    private int length;
    private boolean allowInternational;
    private String message;

    @Override
    public void initialize(CheckPhone constraintAnnotation) {
        this.length = constraintAnnotation.length();
        this.allowInternational = constraintAnnotation.allowInternational();
        this.message = constraintAnnotation.message().replace("{length}", String.valueOf(length));
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }

        // Check for international format if allowed
        if (allowInternational && phone.startsWith("+")) {
            // Remove the plus sign and validate the remaining digits
            String digitsOnly = phone.substring(1).replaceAll("\\D", "");
            // International numbers can have country code (1-3 digits typically) + local number
            return digitsOnly.length() >= length && digitsOnly.length() <= length + 3 && digitsOnly.matches("\\d+");
        } else {
            // For national format, just validate the digits
            String digitsOnly = phone.replaceAll("\\D", "");
            boolean valid = digitsOnly.length() == length && digitsOnly.matches("\\d+");
            
            if (!valid) {
                // Customize error message
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message)
                       .addConstraintViolation();
            }
            
            return valid;
        }
    }
}