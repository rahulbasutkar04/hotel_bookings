package org.example.tdd;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hotel_bookings";
    private static final String DB_USER = "rahul";
    private static final String DB_PASSWORD = "rahul";

    public List<room> freeRooms(Date arrival, Date departure) {
        List<room> freeRooms = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT room_number FROM rooms WHERE status = 'available' AND room_number NOT IN " +
                    "(SELECT room_number FROM Bookings WHERE (arrival_date BETWEEN ? AND ?) OR (departure_date BETWEEN ? AND ?))";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setTimestamp(1, new java.sql.Timestamp(arrival.getTime()));

            preparedStatement.setTimestamp(2, new java.sql.Timestamp(departure.getTime()));

            preparedStatement.setTimestamp(3, new java.sql.Timestamp(arrival.getTime()));

            preparedStatement.setTimestamp(4, new java.sql.Timestamp(departure.getTime()));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                freeRooms.add(new room(roomNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return freeRooms;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM Bookings";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingId(resultSet.getInt("booking_id"));
                booking.setClientId(resultSet.getString("client_id"));
                booking.setRoomNumber(resultSet.getInt("room_number"));
                booking.setArrivalDate(resultSet.getDate("arrival_date"));
                booking.setDepartureDate(resultSet.getDate("departure_date"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public List<Booking> getOverdueBookings(Date currentDate) {
        List<Booking> overdueBookings = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM Bookings WHERE departure_date < ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, new java.sql.Timestamp(currentDate.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingId(resultSet.getInt("booking_id"));
                booking.setClientId(resultSet.getString("client_id"));
                booking.setRoomNumber(resultSet.getInt("room_number"));
                booking.setArrivalDate(resultSet.getDate("arrival_date"));
                booking.setDepartureDate(resultSet.getDate("departure_date"));
                overdueBookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return overdueBookings;
    }
}
