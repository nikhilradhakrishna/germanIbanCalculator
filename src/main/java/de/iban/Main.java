package de.iban;


public class Main {

    public static void main(String[] args) {
        String iban = IBANCalculator.getIBAN("76030080", "240706250");
        boolean valid = IBANCalculator.isValid(iban);
        if (valid) {
            System.out.println("Iban : " + iban);
        } else {
            System.out.println(iban);
        }
    }
}