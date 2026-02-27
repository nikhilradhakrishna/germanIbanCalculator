package de.iban.service;


import de.iban.constants.IBANConstants;
import de.iban.util.IBANFormatter;
import de.iban.util.NumericConverter;

public class IBANValidatorService {

    public boolean validate(String iban) {
        if (iban == null || iban.isBlank()) return false;

        var raw = IBANFormatter.strip(iban);

        if (!raw.startsWith(IBANConstants.COUNTRY_CODE)) return false;
        if (raw.length() != IBANConstants.IBAN_LENGTH) return false;
        if (!raw.matches("[A-Z0-9]+")) return false;

        var rearranged = raw.substring(4) + raw.substring(0, 4);
        return NumericConverter.mod97(NumericConverter.convert(rearranged)) == 1;
    }
}