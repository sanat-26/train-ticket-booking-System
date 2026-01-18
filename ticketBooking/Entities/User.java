package ticketBooking.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import ticketBooking.util.userServiceUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String userName;
    private String password;
    private String hashedPassword;
    private String UUID;
    private List<Ticket> ticketsBooked;

    public User(String userName, String password, String UUID, List<Ticket> ticketsBooked){
        this.userName = userName;
        this.hashedPassword = userServiceUtil.hashPassword(password);
        this.UUID = UUID;
        this.ticketsBooked = ticketsBooked;
    }

    public User(){} //this is required by jackson for deserialization
    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getUUID(){
        return UUID;
    }

    public List<Ticket> getTicketsBooked() {
        return ticketsBooked;
    }

    public void printTickets(){
        for(Ticket ticket : ticketsBooked) System.out.println(ticket.getTicketInfo());
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public void setTicketsBooked(List<Ticket> ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }

    public boolean addTicket(User user, Ticket temp_ticket) throws IOException {
        try {
            Optional<Ticket> foundTicket = ticketsBooked.stream().filter(ticket ->
                    ticket.getTicketID().equals(temp_ticket.getTicketID())).findFirst();
            if (foundTicket.isPresent()) {
                System.out.println("Ticket already exists!");
                return false;
            }else {
                ticketsBooked.add(temp_ticket);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error adding ticket to MyBookings !");
            e.printStackTrace();
            return false;
        }
    }
}
