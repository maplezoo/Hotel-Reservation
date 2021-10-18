import api.AdminResource;
import api.HotelResource;
import mode1.Customer;
import mode1.IRoom;
import mode1.Reservation;
import service.ReservationService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

public class MainMenu<privat> {
    private static MainMenu mainMenu;
    private static Collection<IRoom> availableRooms = new HashSet<>();
    private static Collection<Reservation> reservationCollection = new HashSet<>();
    private static HotelResource hotelResource = HotelResource.getHotelResource();
    private  static AdminResource instanceAdminResource = AdminResource.getReservationService();
    private static final Scanner input = new Scanner(System.in);
    private static final String DATE_PATTERN = "MM/dd/yyyy";
    private  static SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

    public static MainMenu getMainMenu() {
        if (mainMenu == null) {
            System.out.println("Main Menu: NULL");
            mainMenu = new MainMenu();
        }
        return mainMenu;
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
                        getMainMenu().findAndReserveARoom();
                        break;
                    case "2":
                        getMainMenu().seeMyReservation();
                        break;
                    case "3":
                        getMainMenu().createAccount();
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

    private void findAndReserveARoom() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date checkInDate = getDate("CheckIn");
        Date checkOutDate = getDate("CheckOut");
        availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        if (!availableRooms.isEmpty()) {
            System.out.println("Room(s) available!\n");
            makeReservation(checkInDate, checkOutDate);
        } else {
            Calendar rCheckin = Calendar.getInstance();
            Calendar rCheckout = Calendar.getInstance();

            rCheckin.setTime(checkInDate);
            rCheckout.setTime(checkOutDate);
            rCheckin.add(Calendar.DAY_OF_MONTH, 7);
            rCheckout.add(Calendar.DAY_OF_MONTH, 7);

            checkInDate = rCheckin.getTime();
            checkOutDate = rCheckout.getTime();

            availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
            if (!availableRooms.isEmpty()){
                System.out.println("[No rooms available in the range of date.] \nRecommendation Rooms:");
                System.out.println("Checkin Date: " + sdf.format(checkInDate) );
                System.out.println("Checkout Date:" + sdf.format(checkOutDate));
                makeReservation(checkInDate, checkOutDate);
            } else{
                System.out.println("There is no room available.");
            }
        }
    }

    private void makeReservation(Date checkIn, Date checkOut){
        boolean validateInput = false;
        String book;
        while(!validateInput){
            try{
                System.out.println("Would you like to book a room? y/n");
                book = input.next().toLowerCase().trim();
                if (book.equals("y")) {
                    Customer customer = hotelResource.getCustomer(accountValidation());
                    System.out.println("What room number would you like to reserve?");
                    availableRooms.forEach(System.out::println);
                    String roomNumber = input.next();
                    IRoom room = hotelResource.getRoom(roomNumber);
                    hotelResource.bookARoom(customer.getEmail(), room, checkIn, checkOut);
                } else if (book.equals("n")) {
                    start();
                } else {
                    throw new Exception();
                }
                validateInput = true;
            }catch (Exception e) {
                System.out.println("Invalid input!");
            }
        }
    }

    private String accountValidation(){
        boolean validated = false;
        String account;
        String email = null;

        while (!validated){
            try {
                System.out.println("Do you have an account with us? y/n");
                account = input.next().toLowerCase().trim();
                if (account.equals("y")) {
                    input.nextLine();
                    System.out.println("Enter Email format: name@domain.com");
                    email = input.nextLine();
                } else if (account.equals("n")) {
                    System.out.println("You have to create an account");
                    email = createAccount();
                } else {
                    throw new Exception();
                }
                validated = true;
            } catch (Exception e) {
                System.out.println("Invalid input! Please input y/n");
            }
        }
        return email;
    }


    private void seeMyReservation() {
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

    private String createAccount() {
        input.nextLine();
        System.out.println("Enter Email:");
        String email = input.nextLine();
        System.out.println("First Name:");
        String firstName = input.nextLine();
        System.out.println("Last Name:");
        String lastName = input.nextLine();
        hotelResource.createACustomer(email, firstName, lastName);
        return email;
    }

    public static String getSwitchOption() {
        String switchOption;
        Scanner mainMenuScanner = new Scanner(System.in);
        switchOption = mainMenuScanner.nextLine();
        return switchOption;
    }

    public Date getDate(String dateType) {
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
