package de.iban.util;

public final class IBANFormatter {

    private IBANFormatter() {
    }

    public static String format(String iban) {
        var raw = iban.replaceAll("\\s+", "");
        var sb = new StringBuilder();
        for (int i = 0; i < raw.length(); i += 4) {
            if (!sb.isEmpty()) sb.append(" ");
            sb.append(raw, i, Math.min(i + 4, raw.length()));
        }
        return sb.toString();
    }

    public static String strip(String iban) {
        return iban == null ? "" : iban.replaceAll("\\s+", "").toUpperCase();
    }
}