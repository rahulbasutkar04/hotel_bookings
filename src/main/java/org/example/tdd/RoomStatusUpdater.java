package org.example.tdd;


import java.util.Date;
import java.util.List;

public class RoomStatusUpdater implements Runnable {
    private CommandService commandService = new CommandService();
    private QueryService queryService = new QueryService();

    @Override
    public void run() {
        System.out.println("Room status update task started.");
        // Retrieves all bookings where departure date has passed
        List<Booking> overdueBookings = queryService.getOverdueBookings(new Date(System.currentTimeMillis()));

        // Update room status for each overdue booking
        for (Booking booking : overdueBookings) {
            System.out.println("Updating room status for booking: " + booking.getBookingId());
            commandService.updateRoomStatus(booking.getRoomNumber(), "available");
        }
        System.out.println("Room status update task completed.");
    }

}



