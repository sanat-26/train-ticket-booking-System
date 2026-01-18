package ticketBooking.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticketBooking.Entities.Train;
import ticketBooking.Entities.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainService {
    private Train train;
    private List<Train> trainList;
    private static final String TRAINS_PATH = "src/main/java/ticketBooking/localDB/trains.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    public TrainService() throws IOException {
        File trains = new File(TRAINS_PATH);
        trainList = objectMapper.readValue(trains, new TypeReference<List<Train>>() {});
        for (Train u : trainList) {
            if (u.getTrainNo() == null) {
                throw new IllegalStateException("Invalid trains in trains.json: trainNo is null");
            }
        }
    }

    public void saveTrainListToFile() throws IOException{
        try {
            objectMapper.writeValue(new File(TRAINS_PATH), trainList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Train> searchTrains(String src, String dest){
        return trainList.stream().filter(train1 -> validTrain(train1, src, dest)).collect(Collectors.toList());
    }
    public boolean validTrain(Train train, String src, String dest){
        int src_idx = (train.getStations().contains(src)) ? train.getStations().indexOf(src) : -1;
        int dest_idx = (train.getStations().contains(dest)) ? train.getStations().indexOf(dest) : -1;
        if (src_idx >= 0 && dest_idx >= 0 && dest_idx > src_idx) return true;
        return false;
    }
    public void addTrain(Train newTrain){
        Optional<Train> existingTrain = trainList.stream().filter(train1 -> train1.getTrainNo()
                .equals(newTrain.getTrainNo())).findFirst();
        if (existingTrain.isPresent()) updateTrain(newTrain);
        else{
            try {
                trainList.add(newTrain);
                saveTrainListToFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateTrain(Train updatedTrain){
        OptionalInt index = IntStream.range(0, trainList.size())
                .filter(i -> trainList.get(i).getTrainNo().equals(updatedTrain.getTrainNo())).findFirst();
        if (index.isPresent()){
            trainList.set(index.getAsInt(), updatedTrain);
        }
        else addTrain(updatedTrain);
    }

    public List<List<Integer>> getSeatsInfo(Train train){
        Optional<Train> foundTrain = trainList.stream().filter(train1 ->
                train1.getTrainNo().equals(train.getTrainNo())).findFirst();
        if (foundTrain.isPresent()){
            return foundTrain.get().getSeatsInfo();
        }
        else {
            System.out.println("Train does not exist");
            return null;
        }
    }
}
