package ru.nidecker.relexTestTask.util;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationUtil {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALID_CREDIT_CARD_REGEX = Pattern.compile("^[1-9][0-9]{3}(\\-| )?[0-9]{4}(\\-| )?[0-9]{4}(\\-| )?[0-9]{4}$");
    private static final Pattern VALID_WALLET_ADDRESS_REGEX = Pattern.compile("^[A-Za-z0-9]+");

    public static boolean isValidEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean isValidCreditCard(String creditCardNumber) {
        Matcher matcher = VALID_CREDIT_CARD_REGEX.matcher(creditCardNumber);
        return matcher.find();
    }

    public static boolean isValidWalletAddress(String walletAddress) {
        Matcher matcher = VALID_WALLET_ADDRESS_REGEX.matcher(walletAddress);
        return matcher.find();
    }
}