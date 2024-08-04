package menu;
import api.AdminResource;
import model.*;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class AdminMenu {

    AdminResource adminResource = AdminResource.getInstance();

    public void displayMenu() {
        System.out.println("Admin Menu:");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu (returns back to the MainMenu)");
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
                    seeAllCustomers();
                    break;
                case 2:
                    seeAllRooms();
                    break;
                case 3:
                    seeAllReservations();
                    break;
                case 4:
                    addRoom();
                    break;
                case 5:
                    backToMainMenu();
                    break;
                default:
                    System.out.println("Invalid option. Please try again...");
            }
        }
        scanner.close();
    }

    private void seeAllCustomers() {
        System.out.println("Displaying all customers...");
        Collection<Customer> customers = adminResource.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            for (Customer customer:customers){
                System.out.println(customer);
            }
        }
    }

    private void seeAllRooms() {
        System.out.println("Displaying all rooms...");
        Collection<IRoom> rooms = adminResource.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            for (IRoom room:rooms){
                System.out.println(room);
            }
        }
    }

    private void seeAllReservations() {
        System.out.println("Displaying all reservations...");
        adminResource.displayAllReservations();
    }

    private void addRoom() {
        System.out.println("Adding a room...");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter room number:");
        String roomNumber = scanner.nextLine();

        System.out.println("Enter price:");
        double roomPrice = addRoomPrice(scanner);

        System.out.print("Enter room type (1 for SINGLE, 2 for DOUBLE): ");
        RoomType roomType = addRoomType(scanner);

        Room room = new Room(roomNumber, roomPrice, roomType);

        adminResource.addRoom(Collections.singletonList(room));
        System.out.println("Added room successfully!");
    }

    private static double addRoomPrice(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException exp) {
                System.out.println("Invalid room price! Please enter a valid double number.");
            }
        }
    }

    private static RoomType addRoomType(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    return RoomType.SINGLE;
                case "2":
                    return RoomType.DOUBLE;
                default:
                    System.out.println("Invalid room type! Please, choose 1 for SINGLE or 2 for DOUBLE.");
                    break;
            }
        }
    }

    private void backToMainMenu() {
        System.out.println("Returning to the main menu...");
        MainMenu mainMenu = new MainMenu();
        mainMenu.selectOption();
    }

    public static void main(String[] args) {
        AdminMenu menu = new AdminMenu();
        menu.selectOption();
    }
}

