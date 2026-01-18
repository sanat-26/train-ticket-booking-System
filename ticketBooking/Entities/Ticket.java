package ticketBooking.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Ticket {
    private String departStation;
    private String arrivalStation;
    private String UUID;
    private String ticketID;
    private String dateOfTravel;
    private Train train;

    public Ticket(String departStation, String arrivalStation, String UUID, String ticketID,String dateOfTravel, Train train){
        this.departStation = departStation;
        this.arrivalStation = arrivalStation;
        this.UUID = UUID;
        this.ticketID = ticketID;
        this.dateOfTravel = dateOfTravel;
        this.train = train;
    }
    public Ticket(){}

    public String getDepartStation() {
        return departStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public String getUUID() {
        return UUID;
    }

    public String getDateOfTravel() {
        return dateOfTravel;
    }

    public Train getTrain(){
        return train;
    }
    public String getTicketID(){
        return ticketID;
    }
    public String getTicketInfo(){
        return String.format("TRAIN No.: %d Departure: %s Arrival: %s UUID: %s ticketID: %s date: %s",
                train.getTrainNo(),
                departStation,
                arrivalStation,
                UUID,
                ticketID,
                dateOfTravel);
    }

    public void setDepartStation(String departStation) {
        this.departStation = departStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public void setDateOfTravel(String dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public void setTrain(Train train) {
        this.train = train;
    }
}
