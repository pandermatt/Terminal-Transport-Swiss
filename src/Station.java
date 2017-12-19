import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Station {
    private final String platform;
    private final String name;
    private final Timestamp arrival;

    public Station(String name, Long arrival, String platform) {
        this.name = name;
        this.arrival = new Timestamp(arrival);
        this.platform = platform;
    }

    public String getName() {
        return name;
    }

    public String getPlatform() {
        return platform;
    }

    public String getArrival() {
        Date date = new Date(arrival.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        //TODO
        sdf.setTimeZone(TimeZone.getTimeZone("GTM"));
        return sdf.format(date);
    }
}
