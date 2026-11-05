import java.time.LocalDate;

/**
 * Represents a person with a full name, date of birth, and phone number.
 */
public abstract class Person {
    private final String fullName;
    private final LocalDate dateOfBirth;
    private String phoneNumber;

    public Person(String fullName, LocalDate dateOfBirth, String phoneNumber) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name cannot be blank.");
        }

        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth is required.");
        }

        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future.");
        }

        this.fullName = fullName.trim();
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = validatePhoneNumber(phoneNumber);
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = validatePhoneNumber(phoneNumber);
    }

    private static String validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be blank.");
        }

        String normalized = phoneNumber.trim().replaceAll("[\\s()-]", "");
        if (!normalized.matches("\\+?[0-9]{7,15}")) {
            throw new IllegalArgumentException(
                    "Phone number must contain 7 to 15 digits, with an optional leading +.");
        }

        return normalized;
    }

    @Override
    public String toString() {
        return "Full Name: " + fullName
                + ", Date of Birth: " + dateOfBirth
                + ", Phone Number: " + phoneNumber;
    }
}
