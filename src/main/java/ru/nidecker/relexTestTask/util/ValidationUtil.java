package ru.nidecker.relexTestTask.util;

import org.springframework.stereotype.Service;
import ru.nidecker.relexTestTask.exception.NotValidFieldException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationUtil {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALID_CREDIT_CARD_REGEX = Pattern.compile("^[1-9][0-9]{3}(\\-| )?[0-9]{4}(\\-| )?[0-9]{4}(\\-| )?[0-9]{4}$");

    public static void validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if (!matcher.find())
            throw new NotValidFieldException("Email '" + email + "' not valid");
    }

    public static void validateCreditCard(String creditCardNumber) {
        Matcher matcher = VALID_CREDIT_CARD_REGEX.matcher(creditCardNumber);
        if (!matcher.find())
            throw new NotValidFieldException("Credit card " + creditCardNumber + " is invalid");
    }

    public static void validateWalletAddress(String walletAddress) {

    }
}
