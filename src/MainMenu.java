import api.AdminResource;
import api.HotelResource;
import mode1.Customer;
import mode1.IRoom;
import mode1.Reservation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

public class MainMenu {
    public static MainMenu getMainMenu = null;
    static Collection<IRoom> availableRooms = new HashSet<>();
    private static Collection<Reservation> reservationCollection = new HashSet<>();
    static HotelResource hotelResource = HotelResource.getHotelResource();
    public static AdminResource instanceAdminResource = AdminResource.getReservationService();
    static final Scanner input = new Scanner(System.in);
    static final String DATE_PATTERN = "MM/dd/yyyy";
    static SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

    public MainMenu() {
    }


    public static void start() throws ParseException {
        boolean keeprunning = true;
        String optionMainMenu = null;
        String breakLine = "-".repeat(10);
        while (keeprunning) {
            try {
                System.out.println("\nWelcome to the Hotel Reservation Application");
                System.out.println(breakLine);
                System.out.println("1. Find and reserve a room");
                System.out.println("2. See my reservations");
                System.out.println("3. Create an account");
                System.out.println("4. Admin");
                System.out.println("5. Exit");
                System.out.println(breakLine);
                System.out.println("Please select a number for the menu option");
                optionMainMenu = getSwitchOption();
                switch (optionMainMenu) {
                    case "1":
                        findAndReserveARoom();
                        break;
                    case "2":
                        seeMyReservation();
                        break;
                    case "3":
                        createAccount();
                        break;
                    case "4":
                        AdminMenu adminMenu = new AdminMenu();
                        adminMenu.start();
                        break;
                    case "5":
                        keeprunning = false;
                        System.out.println("Thank you");
                        break;
                    default:
                        throw new Exception("Invalid Input");
                }
            } catch (Exception ex) {
                System.out.println(ex.toString() + "\n");
            }
        }
    }

    private static void findAndReserveARoom() {
        String book;
        String account;
        try {
            Date checkInDate = getDate("CheckIn");
            Date checkOutDate = getDate("CheckOut");

            availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
            if (!availableRooms.isEmpty()) {
                System.out.println("Would you like to book a room? y/n");
                book = input.next().toLowerCase().trim();
                if (book.equals("y")) {
                    System.out.println("Do you have an account with us? y/n");
                    account = input.next().toLowerCase().trim();
                    if (account.equals("y")) {
                        System.out.println("Enter Email format: name@domain.com");
                        String email = input.next();
                        Customer customer = hotelResource.getCustomer(email);
                        System.out.println("What room number would you like to reserve?");
                        availableRooms.forEach(System.out::println);
                        String roomNumber = input.next();
                        IRoom room = hotelResource.getRoom(roomNumber);
                        hotelResource.bookARoom(customer.getEmail(), room, checkInDate, checkOutDate);
                    } else if (account.equals("n")) {
                        System.out.println("You have to create an account");
                        createAccount();
                    } else {
                        System.out.println("Invalid input!");
                    }
                } else if (book.equals("n")) {
                    start();
                } else {
                    System.out.println("Invalid input!");
                }
            } else {
                System.out.println("There is no room available.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void seeMyReservation() {
        reservationCollection.clear();
        System.out.println("Please enter your email:");
        String email = input.next();
        reservationCollection = hotelResource.getCustomersReservations(email);
        if (!reservationCollection.isEmpty()) {
            for (Reservation reservation : reservationCollection) {
                System.out.println(reservation);
            }
        } else {
            System.out.println("You have no reservations yet");
        }
    }

    private static void createAccount() {
        System.out.println("Enter Email:");
        String email = input.nextLine();
        System.out.println("First Name:");
        String firstName = input.nextLine();
        System.out.println("Last Name:");
        String lastName = input.nextLine();
        hotelResource.createACustomer(email, firstName, lastName);
    }

    public static String getSwitchOption() {
        String switchOption;
        Scanner mainMenuScanner = new Scanner(System.in);
        switchOption = mainMenuScanner.nextLine();
        return switchOption;
    }

    public static Date getDate(String dateType) {
        // Define expected date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        boolean isValidCheckInDate = false;
        Date checkInDate = null;
        System.out.println("Enter " + dateType + "date mm/dd/yyyy example 02/01/2020");
        while (!isValidCheckInDate) {
            try {
                String dateInput = input.nextLine().trim();
                dateFormat.setLenient(false);
                checkInDate = dateFormat.parse(dateInput);
                isValidCheckInDate = true;
            } catch (Exception e) {
                System.out.println("Please enter a valid " + dateType + "date (mm/dd/yyyy): ");
            }
        }
        return checkInDate;
    }
}
