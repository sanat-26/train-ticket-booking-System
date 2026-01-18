package ticketBooking.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import ticketBooking.Entities.Ticket;
import ticketBooking.Entities.Train;
import ticketBooking.Entities.User;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import ticketBooking.util.userServiceUtil;

public class Booking {
    private User user;
    private User currentUser = null;
    private static final String USERS_PATH = "src/main/java/ticketBooking/localDB/users.json";
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<User> userList;
    public LocalDate today = LocalDate.now();

    public Booking(User user1) throws IOException {
        this.user = user1;
        loadUsersIntoList();
    }

    public Booking() throws IOException{
        loadUsersIntoList();
    }

    public void loadUsersIntoList() throws IOException{
        File users = new File(USERS_PATH);
        userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
        for (User u : userList) {
            if (u.getUserName() == null || u.getUserName().isBlank()) {
                throw new IllegalStateException("Invalid user in users.json: username is null");
            }
        }
    }

    public Boolean login(String userName, String password){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getUserName().equals(userName)
                    && userServiceUtil.verifyPassword(password, user1.getHashedPassword());
        }).findFirst();
        if(foundUser.isPresent()){
            currentUser = foundUser.get();
            return true;
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout(){
        currentUser = null;
    }

    public boolean signup(User user1){
        try {
            userList.add(user1);
            saveUsertoList();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void saveUsertoList() throws IOException{
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public void fetchBookings(){
        if (currentUser == null){
            throw new IllegalStateException("User not logged in");
        }
        currentUser.printTickets();
    }

    public void cancelBooking(String ticketId){
        if(currentUser == null) throw new IllegalStateException("Login first!");

        boolean removed = (currentUser.getTicketsBooked().removeIf(ticket -> ticket.getTicketID().equals(ticketId)));
        if (removed) System.out.println("Ticket cancelled successfully.");
        else {
            throw new IllegalArgumentException("No ticket found to cancel.");
        }
    }

    public List<Train> getTrains(String src, String dest){
        try {
            TrainService trainService = new TrainService();
            return trainService.searchTrains(src, dest);
        }
        catch (IOException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public boolean bookTicket(Train Train, String src, String dest) {
        try {
            if (currentUser == null) throw new IllegalStateException("Login first!");
            TrainService trainService = new TrainService();

                List<List<Integer>> seats = trainService.getSeatsInfo(Train);
                if(seats != null) {
                    for (int i = 0; i < seats.size(); i++) {
                        List<Integer> row = seats.get(i);
                        for (int j = 0; j < row.size(); j++) {
                            Integer seat = row.get(j);
                            if (seat == 0) {
                                row.set(seat, 1);
                                Train.setSeatsInfo(seats);
                                System.out.println("Ticket booked successfully");
                                String temp_ticketID = String.format("%s%d%d", Train.getTrainNo(), i, j);
                                System.out.println("Your ticketID is: " + temp_ticketID);
                                Ticket temp_ticket = new Ticket(src, dest, currentUser.getUUID(), temp_ticketID, today.toString(), Train);
                                currentUser.addTicket(currentUser, temp_ticket);
                                trainService.addTrain(Train);
                                return true;
                            }
                        }
                    }
                    return false;
                }else{
                    System.out.println("Booking unsuccessful.");
                    return false;
                }
        } catch (IOException e) {
            System.out.println("Booking unsuccessful!!");
            e.printStackTrace();
            return false;
        }
    }
}
