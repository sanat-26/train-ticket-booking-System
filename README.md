# Train Ticket Booking System

This repository contains a command-line based train ticket booking system built in Java. It allows users to sign up, log in, search for trains, book tickets, view their bookings, and cancel tickets. The application uses JSON files as a local database to persist user and train data.

## Features

*   **User Authentication**: Secure user signup and login with password hashing using jBCrypt.
*   **Train Search**: Search for available trains between a specified source and destination.
*   **Ticket Booking**: Book an available seat on a selected train.
*   **Booking Management**: View a list of all tickets booked by the current user.
*   **Ticket Cancellation**: Cancel a previously booked ticket using its unique ID.
*   **Data Persistence**: User accounts, train schedules, and seat information are stored locally in JSON files.

## Project Structure

The project is organized into several packages to separate concerns:

```
└── ticketBooking/
    ├── App.java                  # Main application entry point and CLI handler
    ├── Entities/                 # Data model classes
    │   ├── Ticket.java
    │   ├── Train.java
    │   └── User.java
    ├── Services/                 # Business logic for booking and train management
    │   ├── Booking.java
    │   └── TrainService.java
    ├── localDB/                  # JSON files for data storage
    │   ├── trains.json
    │   └── users.json
    └── util/                     # Utility classes
        └── userServiceUtil.java    # Password hashing and verification
```

## How to Run

### Prerequisites
*   Java Development Kit (JDK) 8 or higher.
*   The following dependencies must be available in your classpath:
    *   **Jackson Databind**: For JSON serialization/deserialization.
    *   **jBCrypt**: For hashing and verifying user passwords.

### Compilation and Execution
1.  Clone the repository:
    ```sh
    git clone https://github.com/sanat-26/train-ticket-booking-system.git
    cd train-ticket-booking-system
    ```
2.  Ensure the required JARs (Jackson, jBCrypt) are in your project's classpath.
3.  Compile the Java files:
    ```sh
    javac -cp "path/to/your/libs/*:." ticketBooking/App.java
    ```
4.  Run the application from the project root directory:
    ```sh
    java -cp "path/to/your/libs/*:." ticketBooking.App
    ```
    The application will start, and you will see the main menu in the console.

## Usage

Once the application is running, you will be presented with a menu of options.

1.  **Signup (Option 1)**: Create a new user account by providing a username and password.
2.  **Login (Option 2)**: Log in with your existing credentials. Most other actions require you to be logged in.
3.  **Search Trains (Option 3)**: Enter a source and destination city (e.g., `Kanpur`, `Patna`) to see a list of available trains. Select a train to proceed with booking.
4.  **Book Ticket (Option 4)**: After searching and selecting a train, this option books a ticket for you and provides a unique ticket ID.
5.  **My Bookings (Option 6)**: View all tickets you have successfully booked.
6.  **Cancel Ticket (Option 5)**: Enter a ticket ID from "My Bookings" to cancel a reservation.
7.  **Logout (Option 7)**: Log out of your current session.
8.  **Exit (Option 8)**: Terminate the application.
