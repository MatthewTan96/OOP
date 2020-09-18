package main.java; 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
// import main.java;

// Import Gson library
import com.google.gson.*;
// Importing JSONObject library
import java.lang.Object;
  
public class PullFromAPI  {
    /*
     * based on https://technology.amis.nl/2015/05/12/make-http-post-request-from-java-se-no-frills-no-libraries-just-plain-java/
     */
  
    // private static final String USER_AGENT = "Mozilla/5.0";
    private static final String targeturl = "https://api.pntestbox.com/vsspp/pp/bizfn/berthingSchedule/retrieveByBerthingDate/v1.2";
  
 
public static void sendJson(final String json) throws MalformedURLException, IOException {

    // Creating empty string
    String output = "";
    // method call for generating json

    final URL myurl = new URL(targeturl);
    final HttpURLConnection con = (HttpURLConnection) myurl.openConnection();
    con.setDoOutput(true);
    con.setDoInput(true);

    con.setRequestProperty("Content-Type", "application/json;");
    con.setRequestProperty("Accept", "application/json,text/plain");
    con.setRequestProperty("Method", "POST");
    con.setRequestProperty("Apikey", "d0ceb61c5edd48ce964d65bffacf3274");
    final OutputStream os = con.getOutputStream();
    os.write(json.toString().getBytes("UTF-8"));
    os.close();

    final StringBuilder sb = new StringBuilder();
    final int HttpResult = con.getResponseCode();
    if (HttpResult == HttpURLConnection.HTTP_OK) {
        final BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));

        String line = null;

        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        // System.out.println(""+sb.toString());
        output = "" + sb.toString();
    } else {
        System.out.println(con.getResponseCode());
        System.out.println(con.getResponseMessage());
    }

    JsonObject JSONObject = new Gson().fromJson(output, JsonObject.class);
    JsonArray results = JSONObject.get("results").getAsJsonArray(); //returns type object
    System.out.println(results.size());

    Vessel toSend;

    // Iterating through the different vessel information and adding it into the class
    for (int i = 0; i < results.size(); i++) {
        JsonElement result = results.get(i);
        System.out.println(results.get(i));
        JsonObject vesselInformation = result.getAsJsonObject();
        // System.out.println(vesselInformation.toString());
        String vesselShortName = vesselInformation.get("abbrVslM").getAsString();
        String incomingVoyageNumber = vesselInformation.get("inVoyN").getAsString();
        String outgoingVoyageNumber = vesselInformation.get("outVoyN").getAsString();
        String berthTimeRequired = vesselInformation.get("bthgDt").getAsString();
        String expectedDateTimeDeparture = vesselInformation.get("unbthgDt").getAsString();
        String berthNumber = vesselInformation.get("berthN").getAsString();
        String status = vesselInformation.get("status").getAsString(); 
        toSend = new Vessel(vesselShortName,incomingVoyageNumber,outgoingVoyageNumber,
        berthTimeRequired,expectedDateTimeDeparture,berthNumber,status);
        System.out.println(toSend.toString());
        // break;
        }

}

public static void main(final String[] args) {
    try {
        PullFromAPI.sendJson("{\"dateFrom\":\"2020-07-14\" , \"dateTo\":\"2020-07-14\"}");
    } catch (final MalformedURLException e) {
        e.printStackTrace();
    } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}