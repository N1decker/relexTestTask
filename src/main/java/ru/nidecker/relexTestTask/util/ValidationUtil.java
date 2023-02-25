package ru.nidecker.relexTestTask.util;

import org.springframework.stereotype.Service;
import ru.nidecker.relexTestTask.exception.NotValidEmailException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationUtil {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static void validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if (!matcher.find())
            throw new NotValidEmailException("Email '" + email + "' not valid");
    }
}
