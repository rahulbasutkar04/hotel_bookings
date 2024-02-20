import org.example.tdd.Booking;
import org.example.tdd.CommandService;
import org.example.tdd.Main;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class MainTest {
    @Test
    public void testUpdateRoomStatus() throws SQLException {
        CommandService commandService = new CommandService();

        // Set up connection to the database to verify room status update
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_bookings", "rahul", "rahul")) {
            String query = "SELECT status FROM Rooms WHERE room_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Test updating room status to 'available'
            commandService.updateRoomStatus(101, "available");

            // Verify if the room status is updated to 'available' in the database
            preparedStatement.setInt(1, 101);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals("available", resultSet.getString("status"));
            } else {
                fail("Failed to retrieve room status from the database");
            }

            // Test updating room status to 'booked'
            commandService.updateRoomStatus(101, "booked");

            // Verify if the room status is updated to 'booked' in the database
            preparedStatement.setInt(1, 101);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals("booked", resultSet.getString("status"));
            } else {
                fail("Failed to retrieve room status from the database");
            }
        }
    }

    @Test
    public void testBookARoom() throws SQLException {
        CommandService commandService = new CommandService();

        // Create a Booking object with necessary fields initialized
        Booking booking = new Booking();
        booking.setClientId("testClient");
        booking.setRoomNumber(101); //
        booking.setArrivalDate(new java.util.Date());
        booking.setDepartureDate(new java.util.Date());

        // Test booking a room
        commandService.bookARoom(booking);
    }

}
