import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Connection {
    private final String from;
    private final String to;
    private final String duration;
    private final Date arrival;
    private final Date departure;
    private final int transfers;
    private ArrayList<String> lines = new ArrayList<>();
    private ArrayList<Journey> journeys = new ArrayList<>();

    public Connection(String from, String to, String duration, Date departure, Date arrival, int transfers) {
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.arrival = arrival;
        this.departure = departure;
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
        System.out.format("From: %-30s %s%n", from, getDeparture());
        System.out.format("%42s%n", duration);
        System.out.format("To:   %-30s %s%n", to, getArrival());
        System.out.format("Transfers %d %30s %n", transfers, "Lines: " + getLines());
    }

    public String getArrival() {
        if (arrival == null)
            return "";

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(arrival);
    }

    public String getDeparture() {
        if (departure == null)
            return "";

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(departure);
    }
}
