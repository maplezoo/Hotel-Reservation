import api.AdminResource;
import mode1.*;

import java.security.InvalidKeyException;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

public class AdminMenu {
    private static Scanner input= new Scanner(System.in);
    public static AdminResource adminResource =   AdminResource.getReservationService();

    public void start(){
        boolean keeprunning = true;
        String optionMainMenu =  null;
        String breakLine = "-".repeat(10);

        try(Scanner scanner = new Scanner(System.in)){
            while (keeprunning) {
                try{
                    System.out.println("\nAdmin Menu");
                    System.out.println(breakLine);
                    System.out.println("1. See all Customers");
                    System.out.println("2. See all Rooms");
                    System.out.println("3. See all Reservations");
                    System.out.println("4. Add a Room");
                    System.out.println("5. Back to Main Menu");
                    System.out.println(breakLine);
                    System.out.println("Please select a number for the menu option");
                    optionMainMenu = MainMenu.getSwitchOption();
                    switch (optionMainMenu){
                        case "1":
                            seeAllCustomers();
                            break;
                        case "2":
                            seeAllRooms();
                        case "3":
                            adminResource.displayAllReservation();
                            break;
                        case "4":
                            addARoom();
                            break;
                        case "5":
                            MainMenu mainMenu = new MainMenu();
                            mainMenu.start();
                            break;
                        default:
                            System.out.println("Invalid Input");
                            break;
                        }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void addARoom() {
        List<IRoom> roomList = new ArrayList<>();
        boolean keepAdding = true;
        while (keepAdding){
            String roomNumber = getRoomNumber();
            Double roomPrice = getRoomPrice();
            RoomType roomType = getRoomType();
            Room newRoom = new Room(roomNumber, roomPrice, roomType);
            roomList.add(newRoom);
            if (!addMore()){
                keepAdding = false;
            };
        }
        adminResource.addRoom(roomList);
    }

    private boolean addMore(){
        boolean yesNo = false;
        boolean validateInput = false;
        while (!validateInput){
            try{

                System.out.println("Would you like to add another room? y/n");
                String userInput = input.nextLine();
                if (userInput.equals("n")){
                    yesNo = false;
                }else if(userInput.equals("y")){
                    yesNo = true;
                } else{
                    throw new Exception();
                }
                validateInput = true;
            }catch (Exception ex){
                System.out.println("Error - Invalid Input\n");
            }
        }
        return yesNo;
    }

    private String getRoomNumber(){
        boolean isValidateNumber = false;
        Pattern pattern = Pattern.compile("([0-9]+)");
        String roomNumber = null;
        while (!isValidateNumber){
            try {
                System.out.println("Enter the room number you want to create:");
                roomNumber = input.nextLine();
                if (!pattern.matcher(roomNumber).matches()){
                    System.out.println("Error, Invalid format, please enter numbers only");
                    throw new Exception();
                }
                for (IRoom r: adminResource.getAllRooms()){
                    if (r.getRoomNumber().equals(roomNumber)){
                        System.out.println("Error, the room ID you entered already exists. ");
                        throw new Exception();
                    }
                }
                isValidateNumber = true;
            } catch (Exception e) {
                System.out.println("Try Again");
            }
        }
        return roomNumber;
    }

    private Double getRoomPrice() {
        boolean isValidatePrice = false;
        Pattern pattern = Pattern.compile("([0-9]+)\\.?([0-9]+)?");
        Double roomPrice = null;
        while (!isValidatePrice){
            try {
                System.out.println("Enter the price per night: ");
                roomPrice = input.nextDouble();
                if (!pattern.matcher(roomPrice.toString()).matches()){
                    throw new Exception();
                }
                isValidatePrice = true;
            }catch (Exception ex){
                System.out.println("Error - Invalid Input. Please enter a number.Example: 100 or 100.4");
            }
        }
        return roomPrice;
    }

    private RoomType getRoomType() {
        boolean isValidateType = false;
        RoomType roomType = null;
        String roomTypeRegex = "([1-2])";
        Pattern pattern = Pattern.compile(roomTypeRegex);
        while (!isValidateType){
            try{
                System.out.println("Enter room type: 1 for single bed, 2 for double");
                input.nextLine();
                String userInput = input.nextLine();
                if (!pattern.matcher(userInput).matches()){
                    throw new Exception();
                }
                isValidateType = true;
                if (userInput.equals("1")){
                    roomType = RoomType.SINGLE;
                } else{
                    roomType = RoomType.DOUBLE;
                }
            } catch (Exception e) {
                System.out.println("Try Again");
            }
        }
        return roomType;
    }

    private void seeAllRooms() {
        Collection<IRoom> allRooms;
        allRooms = adminResource.getAllRooms();
        if (allRooms.isEmpty()){
            System.out.println("No rooms in the database.");
        }else{
            for (IRoom r: allRooms){
            System.out.println(r.toString());
            }
        }
    }

    private void seeAllCustomers(){
        Map<String, Customer> allCustomers;
        allCustomers = adminResource.getAllCustomers();
        int i = 1;
        if (allCustomers.isEmpty()){
            System.out.println("No customer in the database.");
        }else{
            for (Map.Entry customer : allCustomers.entrySet()){
                System.out.println("Customer " + i + " -- " + customer.getValue());
                i++;
            }
        }
    }




}
