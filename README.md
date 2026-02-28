# 🏦 German IBAN Calculator

A Java utility for calculating and generating valid German IBANs (International Bank Account Numbers) from a bank routing number (Bankleitzahl / BLZ) and account number (Kontonummer).

---

## 📋 Table of Contents

- [Overview](#overview)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Running Tests](#running-tests)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

This project implements the German IBAN calculation algorithm in Java. Given a German bank routing number (BLZ) and an account number, it produces the correctly formatted IBAN following the ISO 13616 standard.

German IBANs always:
- Start with the country code `DE`
- Are exactly **22 characters** long
- Follow the format: `DExx BBBB BBBB CCCC CCCC CC`
  - `xx` = 2-digit check digits
  - `B` = 8-digit BLZ (Bankleitzahl)
  - `C` = 10-digit account number (zero-padded on the left)

---

## Project Structure

```
germanIbanCalculator/
├── src/
│   └── main/
│       └── java/
│           └── de/
│               └── iban/
│                   └── *.java          # Core IBAN calculation logic
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── build.gradle                        # Gradle build configuration
├── settings.gradle
├── gradlew                             # Unix Gradle wrapper
├── gradlew.bat                         # Windows Gradle wrapper
└── .gitignore
```

---

## Prerequisites

- **Java 11+** (or the version specified in `build.gradle`)
- **Gradle** (wrapper included — no separate installation needed)

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/nikhilradhakrishna/germanIbanCalculator.git
cd germanIbanCalculator
```

### 2. Build the project

**On Linux/macOS:**
```bash
./gradlew build
```

**On Windows:**
```cmd
gradlew.bat build
```

---

## Usage

Run the application with a BLZ and account number:

```bash
./gradlew run
```

Or compile and run the main class directly:

```bash
javac -cp src/main/java src/main/java/de/iban/*.java
java -cp src/main/java de.iban.Main
```

### Example

| Input | Value |
|-------|-------|
| BLZ (Bankleitzahl) | `37040044` |
| Account Number | `0532013000` |
| **Resulting IBAN** | **`DE89370400440532013000`** |

---

## Running Tests

```bash
./gradlew test
```

Test results will be available at:
```
build/reports/tests/test/index.html
```

---

## Contributing

Contributions are welcome! To get started:

1. Fork the repository.
2. Create a new feature branch:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add your feature description"
   ```
4. Push to your branch:
   ```bash
   git push origin feature/your-feature-name
   ```
5. Open a Pull Request.

---

## License

This project is open source. Feel free to use and modify it in accordance with the repository's license terms.

---

## References

- [ISO 13616 – IBAN Standard](https://www.iso.org/standard/81090.html)
- [Deutsche Bundesbank – BLZ Directory](https://www.bundesbank.de/en/tasks/payment-systems/services/bank-sort-codes/download-bank-sort-codes)
- [IBAN Validation Rules (SWIFT)](https://www.swift.com/standards/data-standards/iban)
