import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class JsonParser {
    private static final String TRANSPORT_OPENDATA = "http://transport.opendata.ch/v1/connections";

    private static String fetchJson(String from, String to) {
        try {
            URL url = new URL(TRANSPORT_OPENDATA + "?from=" + URLEncoder.encode(from, "UTF-8") + "&to=" +
                    URLEncoder.encode(to, "UTF-8"));
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder inputLine = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                inputLine.append(line);
            }
            in.close();

            return inputLine.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Connection> buildConnection(String fromStation, String toStation) {
        Connection connection = null;
        ArrayList<Connection> connectionList = new ArrayList<>();
        try {
            JSONObject jsonObj = new JSONObject(fetchJson(fromStation, toStation));
            JSONArray connections = jsonObj.getJSONArray("connections");

            for (int i = 0; i < connections.length(); i++) {
                JSONObject currentConnection = connections.getJSONObject(i);
                String from = currentConnection.getJSONObject("from").getJSONObject("station").getString("name");
                String departure = currentConnection.getJSONObject("from").getString("departure");
                String to = currentConnection.getJSONObject("to").getJSONObject("station").getString("name");
                String arrival = currentConnection.getJSONObject("to").getString("arrival");
                String duration = currentConnection.getString("duration");
                int transfers = currentConnection.getInt("transfers");
                connection = new Connection(from, to, duration.substring(3), getDateFromString(departure), getDateFromString(arrival), transfers);

                JSONArray sections = currentConnection.getJSONArray("sections");
                for (int j = 0; j < sections.length(); j++) {
                    Journey journey;
                    if (Objects.equals(sections.getJSONObject(j).get("journey"), null)) {
                        journey = new Journey("Walk");
                        journey.addStations(new Station("Walk " + (sections.getJSONObject(j).getJSONObject("walk")
                                .getInt("duration") / 60) + "'", getDateFromString(sections.getJSONObject(j)
                                .getJSONObject("arrival").getString("arrival")), null));
                    } else {
                        JSONObject currentJourney = sections.getJSONObject(j).getJSONObject("journey");
                        journey = new Journey(currentJourney.getString("number") + " (" +
                                currentJourney.getString("name") + ")");
                        JSONArray passList = currentJourney.getJSONArray("passList");


                        for (int k = 0; k < passList.length(); k++) {
                            Date date = null;
                            if (!Objects.equals(passList.getJSONObject(k).get("arrival"), null)) {
                                date = getDate(passList, k, "arrival");
                            } else if (!Objects.equals(passList.getJSONObject(k).get("departure"), null)) {
                                date = getDate(passList, k, "departure");

                            }
                            String name = passList.getJSONObject(k).getJSONObject("station").getString("name");
                            String platform = null;
                            if (!Objects.equals(passList.getJSONObject(k).get("platform"), null)) {
                                platform = passList.getJSONObject(k).getString("platform");
                            }

                            journey.addStations(new Station(name, date, platform));
                        }
                    }
                    connection.addJourneys(journey);

                }

                JSONArray products = currentConnection.getJSONArray("products");
                for (int j = 0; j < products.length(); j++) {
                    connection.addLine(products.getString(j));
                }

                connectionList.add(connection);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return connectionList;
    }

    private static Date getDate(JSONArray passList, int k, String stringName) throws JSONException, ParseException {
        String time = passList.getJSONObject(k).getString(stringName);
        return getDateFromString(time);
    }

    private static Date getDateFromString(String time) throws ParseException {
        Date result;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        result = df.parse(time);
        return result;
    }
}