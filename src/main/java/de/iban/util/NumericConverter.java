package de.iban.util;


public final class NumericConverter {

    private NumericConverter() {
    }

    public static String convert(String input) {
        var sb = new StringBuilder();
        for (char c : input.toUpperCase().toCharArray()) {
            sb.append(Character.isLetter(c) ? String.valueOf(c - 'A' + 10) : c);
        }
        return sb.toString();
    }

    public static int mod97(String numericString) {
        var remainder = 0;
        for (char c : numericString.toCharArray()) {
            remainder = (remainder * 10 + (c - '0')) % 97;
        }
        return remainder;
    }
}