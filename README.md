This is an example of CQRS.This made an open source if possible do necessary changes.
Just enusre that you have an MYSQL db and your own local databsae before executing this.
here is the following inforamtion related to DB.


....................................................................................................................................
DBNAME=hotel_bookings
CREATE TABLE Rooms (
    room_number INT PRIMARY KEY,
    floor INT,
    status VARCHAR(20) -- Example: 'available', 'booked', 'under maintenance', etc.
);

CREATE TABLE Bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    client_id VARCHAR(50),
    room_number INT,
    arrival_date DATETIME,
    departure_date DATETIME,
    FOREIGN KEY (room_number) REFERENCES Rooms(room_number)
);
......................................................................................................................................
