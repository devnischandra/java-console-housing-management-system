import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Main class for the Housing Management System.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Housing Management System!");

        try {
            Society society = createSociety();
            boolean running = true;

            while (running) {
                printMenu();
                int choice = readInt("Choose an option: ");

                switch (choice) {
                    case 0 -> displaySocietyDetails(society);
                    case 1 -> addUnit(society);
                    case 2 -> removeUnit(society);
                    case 3 -> listUnits(society);
                    case 4 -> addMember(society);
                    case 5 -> removeMember(society);
                    case 6 -> listMembers(society);
                    case 7 -> {
                        System.out.println("Exiting the program. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (java.util.NoSuchElementException e) {
            System.out.println("\nInput stream closed. Exiting the program.");
        } finally {
            scanner.close();
        }
    }

    private static Society createSociety() {
        System.out.println("Creating a new society...");
        String societyName = readNonBlankString("Enter society name: ");
        String societyAddress = readNonBlankString("Enter society address: ");

        Society society = new Society(societyName, societyAddress);
        System.out.println("Society created: " + society);
        return society;
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("0. Society Details");
        System.out.println("1. Add Unit");
        System.out.println("2. Remove Unit");
        System.out.println("3. List Units");
        System.out.println("4. Add Member");
        System.out.println("5. Remove Member");
        System.out.println("6. List Members");
        System.out.println("7. Exit");
    }

    private static void displaySocietyDetails(Society society) {
        System.out.println("\nSociety Details:");
        System.out.println(society);
    }

    private static void addUnit(Society society) {
        int floorNumber = readNonNegativeInt("Enter floor number: ");
        String unitNumber = readNonBlankString("Enter unit number: ");

        try {
            Unit unit = new Unit(floorNumber, unitNumber);
            society.addUnit(unit);
            System.out.println("Unit added: " + unit);
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to add unit: " + e.getMessage());
        }
    }

    private static void removeUnit(Society society) {
        if (society.getUnits().isEmpty()) {
            System.out.println("No units available to remove.");
            return;
        }

        String unitId = readNonBlankString("Enter unit ID to remove: ");
        Unit unitToRemove = findUnitById(society, unitId);

        if (unitToRemove == null) {
            System.out.println("Unit not found.");
            return;
        }

        try {
            society.removeUnit(unitToRemove);
            System.out.println("Unit removed: " + unitToRemove);
        } catch (IllegalStateException e) {
            System.out.println("Unable to remove unit: " + e.getMessage());
        }
    }

    private static void listUnits(Society society) {
        System.out.println("\nListing all units:");
        if (society.getUnits().isEmpty()) {
            System.out.println("No units available.");
            return;
        }

        for (Unit unit : society.getUnits()) {
            System.out.println(unit);
        }
    }

    private static void addMember(Society society) {
        if (society.getUnits().isEmpty()) {
            System.out.println("No units are available. Add a unit before registering a member.");
            return;
        }

        String memberName = readNonBlankString("Enter member name: ");
        LocalDate dateOfBirth = readDateOfBirth();
        String phoneNumber = readPhoneNumber();

        while (true) {
            String unitId = readNonBlankString("Enter assigned unit ID: ");
            Unit assignedUnit = findUnitById(society, unitId);

            if (assignedUnit == null) {
                System.out.println("Unit not found. Please enter a valid unit ID.");
                continue;
            }

            if (assignedUnit.isOccupied()) {
                System.out.println("The selected unit is already occupied. Please choose another unit.");
                continue;
            }

            try {
                Member member = new Member(memberName, dateOfBirth, phoneNumber, assignedUnit);
                society.addMember(member);
                System.out.println("Member added: " + member);
                return;
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Unable to register member: " + e.getMessage());
                return;
            }
        }
    }

    private static void removeMember(Society society) {
        if (society.getMembers().isEmpty()) {
            System.out.println("No members available to remove.");
            return;
        }

        String memberId = readNonBlankString("Enter member ID to remove: ");
        Member memberToRemove = findMemberById(society, memberId);

        if (memberToRemove == null) {
            System.out.println("Member not found.");
            return;
        }

        society.removeMember(memberToRemove);
        System.out.println("Member removed: " + memberToRemove);
    }

    private static void listMembers(Society society) {
        System.out.println("\nListing all members:");
        if (society.getMembers().isEmpty()) {
            System.out.println("No members available.");
            return;
        }

        for (Member member : society.getMembers()) {
            System.out.println(member);
        }
    }

    private static Unit findUnitById(Society society, String unitId) {
        for (Unit unit : society.getUnits()) {
            if (unit.getUnitId().equalsIgnoreCase(unitId)) {
                return unit;
            }
        }
        return null;
    }

    private static Member findMemberById(Society society, String memberId) {
        for (Member member : society.getMembers()) {
            if (member.getMemberId().equalsIgnoreCase(memberId)) {
                return member;
            }
        }
        return null;
    }

    private static String readNonBlankString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isBlank()) {
                return input;
            }

            System.out.println("Input cannot be blank. Please try again.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

    private static int readNonNegativeInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value >= 0) {
                return value;
            }
            System.out.println("Value cannot be negative.");
        }
    }

    private static LocalDate readDateOfBirth() {
        while (true) {
            String input = readNonBlankString("Enter date of birth (YYYY-MM-DD): ");

            try {
                LocalDate dateOfBirth = LocalDate.parse(input);
                if (dateOfBirth.isAfter(LocalDate.now())) {
                    System.out.println("Date of birth cannot be in the future.");
                    continue;
                }
                return dateOfBirth;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Use the format YYYY-MM-DD.");
            }
        }
    }

    private static String readPhoneNumber() {
        while (true) {
            String phoneNumber = readNonBlankString("Enter phone number: ");
            String normalized = phoneNumber.replaceAll("[\\s()-]", "");

            if (normalized.matches("\\+?[0-9]{7,15}")) {
                return normalized;
            }

            System.out.println(
                    "Invalid phone number. Enter 7 to 15 digits, with an optional leading +.");
        }
    }
}
