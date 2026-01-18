package ticketBooking;

import ticketBooking.Entities.Train;
import ticketBooking.Entities.User;
import ticketBooking.Services.Booking;
import ticketBooking.Services.TrainService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
        });

        System.out.println("Running ticket booking system !");
        Scanner s = new Scanner(System.in);
        int option = 0;
        Booking booking;
        try{
            booking = new Booking();
        } catch (IOException e) {
            System.out.println("There is something wrong.");
            e.printStackTrace();
            return;
        }

        Train trainSelected = null;
        String src = "";
        String dest = "";

        while (option != 8){
            System.out.println("Choose option :");
            System.out.println("1. Signup");
            System.out.println("2. Login");
            System.out.println("3. Search Trains");
            System.out.println("4. Book Ticket");
            System.out.println("5. Cancel Ticket");
            System.out.println("6. My Bookings");
            System.out.println("7. Logout");
            System.out.println("8. Exit");

            option = s.nextInt();

            TrainService trainService;
            try {
                trainService = new TrainService();
            } catch (IOException e) {
                System.out.println("Failed to load trains.");
                e.printStackTrace();
                return;
            }

            switch (option){
                case 1:
                    System.out.println("Enter your name: ");
                    String nameToSignup = s.next();

                    System.out.println("Create your password: ");
                    String pswrdToSignup = s.next();

                    User userToSignup = new User(nameToSignup, pswrdToSignup, UUID.randomUUID().toString(), new ArrayList<>());
                    booking.signup(userToSignup);
                    break;

                case 2:
                    System.out.println("Enter your name: ");

                    String nameToLogin = s.next();

                    System.out.println("Enter your password: ");

                    String pswrdToLogin = s.next();

                    if (!booking.login(nameToLogin, pswrdToLogin)){
                        System.out.println("Invalid credentials !");
                        break;
                    }
                    System.out.println("Logged In successfully.");
                    System.out.println("Welcome ! " + booking.getCurrentUser().getUserName());
                    break;

                case 3:
                    System.out.println("From : ");
                    src = s.next();

                    System.out.println("To : ");
                    dest = s.next();

                    List<Train> foundTrains = booking.getTrains(src, dest);
                    int serial;
                    System.out.println("Found Trains are: ");
                    for (int i = 0; i < foundTrains.size(); i++) {
                        System.out.println(i + 1 + ". " + foundTrains.get(i).getTrainNo());
                        System.out.println(String.format("Station Times: %-12s %s    %-12s %s %n"
                                , src, foundTrains.get(i).getStationTimes().get(src), dest, foundTrains.get(i).getStationTimes().get(dest)));
                    }

                    System.out.println();
                    System.out.println("Enter the serial number of train you want to book seat in.");
                    serial = s.nextInt();
                    if (serial >= 1 && serial <= foundTrains.size()) {
                        trainSelected = foundTrains.get(serial - 1);
                    } else {
                        System.out.println("Invalid train selection");
                    }

                    break;

                case 4:
                    if (trainSelected == null) {
                        System.out.println("Please search and select a train first.");
                        break;
                    }
                    booking.bookTicket(trainSelected, src, dest);
                    break;


                case 5:
                    System.out.println("Enter the ticketID to cancel : ");
                    String ticketIDtoCancel = s.next();
                    booking.cancelBooking(ticketIDtoCancel);
                    break;

                case 6:
                    booking.fetchBookings();
                    break;

                case 7:
                    booking.logout();
                    System.out.println("Logged Out Successfully.");
                    break;

                default:
                    break;
            }
        }
    }
}
