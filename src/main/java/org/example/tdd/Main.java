package org.example.tdd;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        CommandService commandService = new CommandService();
        QueryService queryService = new QueryService();
        Scanner scanner = new Scanner(System.in);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new RoomStatusUpdater(), 0, 24, TimeUnit.HOURS); // Check availability at every 24 hours

        while (true) {
            System.out.println("Menu:");
            System.out.println("1.See available rooms");
            System.out.println("2.See booked rooms and their data");
            System.out.println("3.Book a room");
            System.out.println("4.Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // details available rooms
                    List<room> availableRooms = queryService.freeRooms(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
                    System.out.println("\nAvailable Rooms:");
                    for (org.example.tdd.room room : availableRooms) {
                        System.out.println("Room Number: " + room.getRoomNumber());
                    }
                    break;
                case 2:
                    // details of booked rooms
                    List<Booking> bookedRooms = queryService.getAllBookings();
                    System.out.println("\nBooked Rooms:");
                    for (Booking booking : bookedRooms) {
                        System.out.println("Booking ID: " + booking.getBookingId() +
                                ", Client ID: " + booking.getClientId() +
                                ", Room Number: " + booking.getRoomNumber() +
                                ", Arrival Date: " + booking.getArrivalDate() +
                                ", Departure Date: " + booking.getDepartureDate());
                    }
                    break;
                case 3:
                    //information collection to  Book a room
                    System.out.println("\nEnter room number you want to book:");
                    int roomNumber = scanner.nextInt();
                    System.out.println("Enter client ID:");
                    String clientId = scanner.next();
                    System.out.println("Enter arrival date (yyyy-MM-dd):");
                    String arrivalDateString = scanner.next();
                    System.out.println("Enter departure date (yyyy-MM-dd):");
                    String departureDateString = scanner.next();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date arrivalDate;
                    Date departureDate;
                    try {
                        arrivalDate = new Date(sdf.parse(arrivalDateString).getTime());
                        departureDate = new Date(sdf.parse(departureDateString).getTime());
                    } catch (ParseException e) {
                        System.out.println("Enter date in exact format");
                        break;
                    }

                    Booking booking = new Booking();
                    booking.setClientId(clientId);
                    booking.setRoomNumber(roomNumber);
                    booking.setArrivalDate(arrivalDate);
                    booking.setDepartureDate(departureDate);

                    // function call to Book the room
                    commandService.bookARoom(booking);
                    System.out.println("Room booked successfully!");
                    break;
                case 4:
                    System.out.println("Thank you!!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");


            }
        }
    }
}
