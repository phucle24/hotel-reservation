package menu;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {
    HotelResource hotelResource = HotelResource.getInstance();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public void displayMenu() {
        System.out.println("Main Menu:");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin (open the admin menu described below)");
        System.out.println("5. Exit (exit the application)");
    }

    public void selectOption() {
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (option != 5) {
            displayMenu();
            System.out.print("Select an option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    findAndReserveRoom();
                    break;
                case 2:
                    seeMyReservations();
                    break;
                case 3:
                    createAccount();
                    break;
                case 4:
                    openAdminMenu();
                    break;
                case 5:
                    exitApplication();
                    break;
                default:
                    System.out.println("Invalid option. Please try again...");
            }
        }

        scanner.close();
    }

    private void findAndReserveRoom() {
        System.out.println("Finding and reserving a room...");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter CheckIn Date with format (mm/dd/yyyy):");
        Date checkIn = getInputDate(scanner);

        System.out.println("Enter CheckOut Date with format (mm/dd/yyyy):");
        Date checkOut = getInputDate(scanner);

        if (checkIn != null && checkOut != null) {
            Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);

            if (availableRooms.isEmpty()) {
                    System.out.println("No rooms found.");
            } else {
                    printRooms(availableRooms);

                    System.out.println("Enter email to book room:");
                    String customerEmail = scanner.nextLine();
                    Customer customer = hotelResource.getCustomer(customerEmail);

                    if (customer != null) {
                        System.out.println("Enter number room to book: ");
                        String numberRoom = scanner.nextLine();
                        IRoom room = hotelResource.getRoom(numberRoom);
                        hotelResource.bookARoom(customerEmail, room, checkIn, checkOut);
                    } else {
                        System.out.println("Customer not found for email: " +customerEmail+ "Please try again...");
                    }

            }
        }
    }

    private Date getInputDate(Scanner scanner) {
        while (true) {
            try {
                return dateFormat.parse(scanner.nextLine().trim());
            } catch (ParseException e) {
                System.out.println("Invalid date format! Please enter a date in the format mm/dd/yyyy:");
            }
        }
    }

    private void printRooms(Collection<IRoom> rooms) {
        if (rooms.isEmpty()) {
            System.out.println("No available rooms.");
        } else {
            System.out.println("Available rooms:");
            for (IRoom room : rooms) {
                System.out.println(room);
            }
        }
    }

    private void seeMyReservations() {
        System.out.println("Displaying your reservations...");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your email:");
        String customerEmail = scanner.nextLine();

        Collection<Reservation> reservations = hotelResource.getCustomersReservations(customerEmail);
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("Could not find reservations");
        } else {
            System.out.println("Reservations already: ");
            for (Reservation reservation : reservations) {
                System.out.println("\n" + reservation);
            }
        }
    }

    private void createAccount() {
        System.out.println("Creating an account...");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter your email:");
            String email = scanner.nextLine().trim();

            System.out.println("Enter your first name:");
            String firstName = scanner.nextLine().trim();

            System.out.println("Enter your last name:");
            String lastName = scanner.nextLine().trim();

            try {
                hotelResource.createACustomer(email, firstName, lastName);
                System.out.println("Your account was created successfully!");
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getLocalizedMessage());
                System.out.println("Something went wrong ! Please try again.");
            }
        }
    }

    private void openAdminMenu() {
        System.out.println("Opening admin menu...");
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.selectOption();
    }

    private void exitApplication() {
        System.out.println("Exiting the application...");
        System.exit(0);
    }

    public static void mainMenu() {
        MainMenu menu = new MainMenu();
        menu.selectOption();
    }
}
