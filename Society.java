import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a society with a name, address, units, and members.
 */
public class Society {
    private final String name;
    private final String address;
    private final List<Unit> units = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();

    public Society(String name, String address) {
        this.name = requireNonBlank(name, "Society name");
        this.address = requireNonBlank(address, "Society address");
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<Unit> getUnits() {
        return Collections.unmodifiableList(units);
    }

    public int getTotalUnits() {
        return units.size();
    }

    public void addUnit(Unit unit) {
        Objects.requireNonNull(unit, "Unit cannot be null.");

        boolean duplicate = units.stream().anyMatch(existing ->
                existing.getFloorNumber() == unit.getFloorNumber()
                        && existing.getUnitNumber().equalsIgnoreCase(unit.getUnitNumber()));

        if (duplicate) {
            throw new IllegalArgumentException(
                    "A unit with this floor number and unit number already exists.");
        }

        units.add(unit);
    }

    public boolean removeUnit(Unit unit) {
        Objects.requireNonNull(unit, "Unit cannot be null.");

        if (!units.contains(unit)) {
            return false;
        }

        if (unit.isOccupied()) {
            throw new IllegalStateException("An occupied unit cannot be removed.");
        }

        return units.remove(unit);
    }

    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public void addMember(Member member) {
        Objects.requireNonNull(member, "Member cannot be null.");

        if (members.contains(member)) {
            throw new IllegalArgumentException("This member is already registered.");
        }

        Unit assignedUnit = member.getAssignedUnit();

        if (!units.contains(assignedUnit)) {
            throw new IllegalArgumentException(
                    "The assigned unit does not belong to this society.");
        }

        if (assignedUnit.isOccupied()) {
            throw new IllegalStateException("The assigned unit is already occupied.");
        }

        members.add(member);
        assignedUnit.setOccupied(true);
    }

    public boolean removeMember(Member member) {
        Objects.requireNonNull(member, "Member cannot be null.");

        if (!members.remove(member)) {
            return false;
        }

        member.getAssignedUnit().setOccupied(false);
        return true;
    }

    public int getTotalMembers() {
        return members.size();
    }

    private static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank.");
        }
        return value.trim();
    }

    @Override
    public String toString() {
        return "Society Name: " + name
                + ", Address: " + address
                + ", Total Units: " + getTotalUnits()
                + ", Total Members: " + getTotalMembers();
    }
}
