import java.util.ArrayList;

public class Journey {
    private String name;
    private ArrayList<Station> stations = new ArrayList<>();

    public Journey(String name) {
        this.name = name;
    }

    public void addStations(Station station) {
        stations.add(station);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }
}
