package org.example.tdd;

import java.util.Date;
import java.util.List;

public class RoomStatusUpdater implements Runnable {
    private CommandService commandService = new CommandService();
    private QueryService queryService = new QueryService();

    @Override
    public void run() {
        // Retrieves all bookings where departure date has passed
        List<Booking> overdueBookings = queryService.getOverdueBookings(new Date(System.currentTimeMillis()));

        // Update room status for each overdue bookings
        for (Booking booking : overdueBookings) {
            commandService.updateRoomStatus(booking.getRoomNumber(), "available");
        }
    }
}

