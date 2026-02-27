package de.iban;


import de.iban.service.IBANCalculatorService;
import de.iban.service.IBANValidatorService;

/**
 * IBANLibrary — Public API for German IBAN calculation.
 * <p>
 * Fixed values:
 * Country  : DE (Germany)
 * Bank Code: 76030080
 * <p>
 * Usage:
 * String iban = IBANLibrary.getIBAN("532013000");
 * // returns "DE57 7603 0080 0532 0130 00"
 * // returns  null if account number is invalid
 */
public final class IBANCalculator {

    private static final IBANCalculatorService calculator =
            new IBANCalculatorService(new IBANValidatorService());

    // Prevent instantiation — library is used statically
    private IBANCalculator() {
    }

    /**
     * Generate a German IBAN from an account number.
     *
     * @param accountNumber Account number (max 10 digits, will be zero-padded)
     * @return Formatted IBAN string, or null if input is invalid
     * <p>
     * Examples:
     * getIBAN("532013000")   → "DE57 7603 0080 0532 0130 00"
     * getIBAN("1234567890")  → "DE91 7603 0080 1234 5678 90"
     * getIBAN("ABC")         → null
     * getIBAN(null)          → null
     */
    public static String getIBAN(String bankCode, String accountNumber) {
        return calculator.calculate(bankCode, accountNumber);
    }

    /**
     * Validate any German IBAN string.
     *
     * @param iban IBAN string to validate (spaces allowed)
     * @return true if valid, false otherwise
     * <p>
     * Examples:
     * isValid("DE57 7603 0080 0532 0130 00")  → true
     * isValid("DE00 1234 5678 9012 3456 78")  → false
     */
    public static boolean isValid(String iban) {
        return new IBANValidatorService().validate(iban);
    }
}
