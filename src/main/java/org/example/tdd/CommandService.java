package org.example.tdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommandService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hotel_bookings";
    private static final String DB_USER = "rahul";
    private static final String DB_PASSWORD = "rahul";

    public void bookARoom(Booking booking) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Inserting the booking into the Bookings table
            String query = "INSERT INTO Bookings (client_id, room_number, arrival_date, departure_date) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, booking.getClientId());
            preparedStatement.setInt(2, booking.getRoomNumber());
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(booking.getArrivalDate().getTime()));
            preparedStatement.setTimestamp(4, new java.sql.Timestamp(booking.getDepartureDate().getTime()));
            preparedStatement.executeUpdate();

            //Updating the status of the room to 'booked' in the rooms table of database
            query = "UPDATE Rooms SET status = 'booked' WHERE room_number = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, booking.getRoomNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRoomStatus(int roomNumber, String status) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE Rooms SET status = ? WHERE room_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, roomNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

