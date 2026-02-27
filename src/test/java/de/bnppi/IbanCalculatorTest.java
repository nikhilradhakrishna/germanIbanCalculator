package de.bnppi;

import de.iban.service.IBANCalculatorService;
import de.iban.service.IBANValidatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class IbanCalculatorTest {

    private static final IBANCalculatorService ibanCalculator =
            new IBANCalculatorService(new IBANValidatorService());

    // --- Happy path: known valid German IBANs ---

    @Test
    void shouldCalculateCorrectIban_knownExample() {
        // Deutsche Bank Frankfurt: BLZ 37040044, account 532013000
        String iban = ibanCalculator.calculate("37040044", "532013000");
        assertEquals("DE89370400440532013000", iban);
    }

    @Test
    void shouldPadAccountNumberWithLeadingZeros() {
        // Account numbers shorter than 10 digits must be left-padded
        String iban = ibanCalculator.calculate("20041144", "1234567890");
        assertNotNull(iban);
        assertEquals(22, iban.length());
    }

    @ParameterizedTest
    @CsvSource({
            "37040044, 532013000,  DE89370400440532013000",
            "76030080, 610653146, DE42760300800610653146",
            "12030000, 5566778899, DE98120300005566778899",
            "30020900, 0102030405, DE05300209000102030405",
    })
    void shouldCalculateCorrectIban_multipleExamples(String blz, String account, String expected) {
        assertEquals(expected, ibanCalculator.calculate(blz, account));
    }

    // --- Format checks ---

    @Test
    void ibanShouldAlwaysBe22CharactersLong() {
        String iban = ibanCalculator.calculate("37040044", "532013000");
        assertEquals(22, iban.length());
    }

    @Test
    void ibanShouldStartWithDE() {
        String iban = ibanCalculator.calculate("37040044", "532013000");
        assertTrue(iban.startsWith("DE"));
    }

    @Test
    void ibanShouldContainBlzAtCorrectPosition() {
        String iban = ibanCalculator.calculate("37040044", "532013000");
        // BLZ occupies characters 4–11 (0-indexed)
        assertEquals("37040044", iban.substring(4, 12));
    }

    @Test
    void ibanShouldContainPaddedAccountAtCorrectPosition() {
        String iban = ibanCalculator.calculate("37040044", "532013000");
        // Account (10 digits) occupies characters 12–21
        assertEquals("0532013000", iban.substring(12, 22));
    }

    // --- Check digit verification ---

    @Test
    void checkDigitsShouldBeNumeric() {
        String iban = ibanCalculator.calculate("37040044", "532013000");
        String checkDigits = iban.substring(2, 4);
        assertDoesNotThrow(() -> Integer.parseInt(checkDigits));
    }

    @Test
    void checkDigitsShouldNeverBeZeroOrOne() {
        // Valid IBAN check digits are always between 02 and 98
        String iban = ibanCalculator.calculate("37040044", "532013000");
        int checkDigits = Integer.parseInt(iban.substring(2, 4));
        assertTrue(checkDigits >= 2 && checkDigits <= 98);
    }

    // --- Edge cases ---

    @Test
    void shouldHandleAccountNumberAlreadyTenDigits() {
        String iban = ibanCalculator.calculate("20041144", "1234567890");
        assertEquals("1234567890", iban.substring(12, 22));
    }

    @Test
    void shouldHandleShortAccountNumber_singleDigit() {
        // Account "1" should be padded to "0000000001"
        String iban = ibanCalculator.calculate("37040044", "1");
        assertEquals("0000000001", iban.substring(12, 22));
    }

    // --- Invalid input (if your code throws exceptions) ---

    @Test
    void shouldThrowException_whenBlzIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                ibanCalculator.calculate(null, "532013000")
        );
    }

    @Test
    void shouldThrowException_whenAccountIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                ibanCalculator.calculate("37040044", null)
        );
    }

    @Test
    void shouldThrowException_whenBlzIsNot8Digits() {
        assertThrows(IllegalArgumentException.class, () ->
                ibanCalculator.calculate("1234", "532013000") // too short
        );
    }

    @Test
    void shouldThrowException_whenAccountExceeds10Digits() {
        assertThrows(IllegalArgumentException.class, () ->
                ibanCalculator.calculate("37040044", "12345678901") // 11 digits
        );
    }
}