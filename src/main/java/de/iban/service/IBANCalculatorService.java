package de.iban.service;


import de.iban.constants.IBANConstants;
import de.iban.util.NumericConverter;

public class IBANCalculatorService {

    private final IBANValidatorService validatorService;

    public IBANCalculatorService(IBANValidatorService validatorService) {
        this.validatorService = validatorService;
    }

   /* public String calculate(String bankCode, String accountNumber) {
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
            var bban = bankCode + paddedAccount;

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
    }*/
   public String calculate(String bankCode, String accountNumber) {
       if (bankCode == null) throw new IllegalArgumentException("Bank code must not be null");
       if (accountNumber == null) throw new IllegalArgumentException("Account number must not be null");
       if (!bankCode.matches("\\d{8}")) throw new IllegalArgumentException("Bank code must be exactly 8 digits");
       if (accountNumber.trim().replaceAll("\\s+", "").length() > IBANConstants.ACCOUNT_LENGTH)
           throw new IllegalArgumentException("Account number must not exceed 10 digits");
       try {
           var cleaned = accountNumber.trim().replaceAll("\\s+", ""); // null check no longer needed here

           if (cleaned.isBlank()) return null;
           if (!cleaned.matches("\\d+")) return null;
           if (cleaned.length() > IBANConstants.ACCOUNT_LENGTH) return null;

           var paddedAccount = "%010d".formatted(Long.parseLong(cleaned));
           var bban = bankCode + paddedAccount;
           var numericString = NumericConverter.convert(bban + IBANConstants.COUNTRY_CODE + "00");
           var checkDigits = "%02d".formatted(98 - NumericConverter.mod97(numericString));
           var ibanRaw = IBANConstants.COUNTRY_CODE + checkDigits + bban;

           if (!validatorService.validate(ibanRaw)) return null;

           return ibanRaw;

       } catch (Exception e) {
           return null;
       }
   }
}
