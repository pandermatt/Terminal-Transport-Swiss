import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Station {
    private final String platform;
    private final String name;
    private final Date arrival;

    public Station(String name, Date arrival, String platform) {
        this.name = name;
        this.arrival = arrival;
        this.platform = platform;
    }

    public String getName() {
        return name;
    }

    public String getPlatform() {
        return platform != null ? platform : "";
    }

    public String getArrival() {
        if (arrival == null)
            return "";

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(arrival);
    }
}
