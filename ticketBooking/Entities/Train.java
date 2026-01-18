package ticketBooking.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)

public class Train {
    private Integer trainNo;
    private List<List<Integer>> seatsInfo;
    private Map<String, String> stationTimes;
    private List<String> stations;

    public Train(Integer trainNo, List<List<Integer>> seatsInfo, Map<String, String> stationTimes, List<String> stations){
        this.trainNo = trainNo;
        this.seatsInfo = seatsInfo;
        this.stationTimes = stationTimes;
        this.stations = stations;
    }

    public Train(){}
    public Integer getTrainNo(){
        return trainNo;
    }
    public List<String> getStations(){
        return stations;
    }
    public Map<String, String> getStationTimes(){
        return stationTimes;
    }
    public List<List<Integer>> getSeatsInfo(){
        return seatsInfo;
    }

    public void setTrainNo(Integer trainNo) {
        this.trainNo = trainNo;
    }

    public void setSeatsInfo(List<List<Integer>> seatsInfo) {
        this.seatsInfo = seatsInfo;
    }

    public void setStationTimes(Map<String, String> stationTimes) {
        this.stationTimes = stationTimes;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

}
