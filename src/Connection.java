import java.util.ArrayList;

public class Connection {
    private String from;
    private String to;
    private String duration;
    private int transfers;
    private ArrayList<Journey> journeys = new ArrayList<>();

    public Connection(String from, String to, String duration, int transfers) {
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.transfers = transfers;
    }

    public void addJourneys(Journey journey) {
        journeys.add(journey);
    }

    public void print() {
        System.out.println("------------------------------------------------------");
        System.out.println(from + " -> " + to + " \t(" + duration + ")");
        for (Journey j : journeys) {
            System.out.println("------------------------");
            System.out.println(j.getName());

            System.out.println("---------------");
            for (Station s : j.getStations()) {
                System.out.println(s.getName());
            }
        }
        System.out.println("------------------------------------------------------");
    }
}
