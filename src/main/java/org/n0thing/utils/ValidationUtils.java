package org.n0thing.utils;

import org.apache.commons.validator.routines.EmailValidator;

public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    public static boolean isValidPassword(String password) {
        // Mínimo 8 caracteres, al menos una mayúscula, una minúscula y un número
        return password != null &&
                password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*");
    }

    public static String getPasswordStrength(String password) {
        if (password == null || password.length() < 8) return "Débil";

        int strength = 0;
        if (password.matches(".*[A-Z].*")) strength++;
        if (password.matches(".*[a-z].*")) strength++;
        if (password.matches(".*\\d.*")) strength++;
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) strength++;

        if (strength <= 2) return "Débil";
        if (strength == 3) return "Media";
        return "Fuerte";
    }
}
