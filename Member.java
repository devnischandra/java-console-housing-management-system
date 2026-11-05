import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a member of the organization.
 */
public class Member extends Person {
    private final String memberId;
    private final Unit assignedUnit;

    public Member(String name, LocalDate dateOfBirth, String phoneNumber, Unit assignedUnit) {
        super(name, dateOfBirth, phoneNumber);
        this.assignedUnit = Objects.requireNonNull(
                assignedUnit, "Assigned unit cannot be null.");
        this.memberId = UUID.randomUUID().toString();
    }

    public String getMemberId() {
        return memberId;
    }

    public Unit getAssignedUnit() {
        return assignedUnit;
    }

    @Override
    public String toString() {
        return "Member ID: " + memberId
                + ", Name: " + getFullName()
                + ", Date of Birth: " + getDateOfBirth()
                + ", Phone Number: " + getPhoneNumber()
                + ", Assigned Unit: " + assignedUnit;
    }
}
