import java.util.ArrayList;

public class Connection {
    private String from;
    private String to;
    private String duration;
    private int transfers;
    private ArrayList<String> lines = new ArrayList<>();
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

    public void addLine(String line) {
        lines.add(line);
    }

    public String getLines() {
        return String.join(", ", lines);
    }

    public void print() {
        printSummary();

        for (Journey j : journeys) {
            SystemOut.centerPrint(j.getName(), 42);
            for (Station s : j.getStations()) {
                System.out.format("%-30s %2s %8s %n",
                        s.getName(), s.getPlatform(), s.getArrival());
            }
        }
        SystemOut.centerPrint("", 42);
    }

    public void printSummary() {
        System.out.format("%-31s (%s) %n", from + " -> " + to, duration);
        System.out.format("Transfers %d %30s %n", transfers, "Lines: " + getLines());
    }
}
