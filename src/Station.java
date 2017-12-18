import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Station {
    private String name;
    private Timestamp arrival;

    public Station(String name, Long arrival) {
        this.name = name;
        this.arrival = new Timestamp(arrival);
    }

    public String getName() {
        return name;
    }

    public String getArrival() {
        Date date = new Date(arrival.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        //TODO
        sdf.setTimeZone(TimeZone.getTimeZone("GTM"));
        return sdf.format(date);
    }
}
