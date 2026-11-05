import java.util.UUID;

/**
 * Represents a unit in a housing society.
 */
public class Unit {
    private final String unitId;
    private final int floorNumber;
    private final String unitNumber;
    private boolean occupied;

    public Unit(int floorNumber, String unitNumber) {
        if (floorNumber < 0) {
            throw new IllegalArgumentException("Floor number cannot be negative.");
        }

        if (unitNumber == null || unitNumber.isBlank()) {
            throw new IllegalArgumentException("Unit number cannot be blank.");
        }

        this.unitId = UUID.randomUUID().toString();
        this.floorNumber = floorNumber;
        this.unitNumber = unitNumber.trim();
        this.occupied = false;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public boolean isOccupied() {
        return occupied;
    }

    void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    @Override
    public String toString() {
        return "Unit ID: " + unitId
                + ", Unit Number: " + unitNumber
                + ", Floor Number: " + floorNumber
                + ", Occupied: " + occupied;
    }
}
