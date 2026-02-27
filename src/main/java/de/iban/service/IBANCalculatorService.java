package de.iban.service;


import de.iban.constants.IBANConstants;
import de.iban.util.NumericConverter;

public class IBANCalculatorService {

    private final IBANValidatorService validatorService;

    public IBANCalculatorService(IBANValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    public String calculate(String accountNumber) {
        try {
            var cleaned = accountNumber == null
                    ? ""
                    : accountNumber.trim().replaceAll("\\s+", "");

            if (cleaned.isBlank()) return null;
            if (!cleaned.matches("\\d+")) return null;
            if (cleaned.length() > IBANConstants.ACCOUNT_LENGTH) return null;

            // Left-pad to 10 digits
            var paddedAccount = "%010d".formatted(Long.parseLong(cleaned));

            // Build BBAN = BankCode(8) + Account(10)
            var bban = IBANConstants.BANK_CODE + paddedAccount;

            // Compute check digits
            var numericString = NumericConverter.convert(bban + IBANConstants.COUNTRY_CODE + "00");
            var checkDigits = "%02d".formatted(98 - NumericConverter.mod97(numericString));

            // Assemble IBAN
            var ibanRaw = IBANConstants.COUNTRY_CODE + checkDigits + bban;

            // Validate before returning
            if (!validatorService.validate(ibanRaw)) return null;

            return ibanRaw;

        } catch (Exception e) {
            return null;
        }
    }
}
