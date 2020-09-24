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
// import java.lang.Object;

public class PullFromAPI {
    /*
     * based on
     * https://technology.amis.nl/2015/05/12/make-http-post-request-from-java-se-no-
     * frills-no-libraries-just-plain-java/
     */

    // private static final String USER_AGENT = "Mozilla/5.0";

    public static JsonArray sendJson(final String json) throws MalformedURLException, IOException {
        String targeturl = "https://api.pntestbox.com/vsspp/pp/bizfn/berthingSchedule/retrieveByBerthingDate/v1.2";
        // Creating empty string
        String output = "";
        // method call for generating json

        URL myurl = new URL(targeturl);
        HttpURLConnection con = (HttpURLConnection) myurl.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);

        con.setRequestProperty("Content-Type", "application/json;");
        con.setRequestProperty("Accept", "application/json,text/plain");
        con.setRequestProperty("Method", "POST");
        con.setRequestProperty("Apikey", "d0ceb61c5edd48ce964d65bffacf3274");
        OutputStream os = con.getOutputStream();
        os.write(json.toString().getBytes("UTF-8"));
        os.close();

        StringBuilder sb = new StringBuilder();
        int HttpResult = con.getResponseCode();
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
        JsonArray results = JSONObject.get("results").getAsJsonArray(); // returns type object
        //System.out.println(results.size());
        return results;
    }

    public static void SendtoDatabase(JsonArray results) throws MalformedURLException, IOException{
        String targeturl = "http://localhost:8080/create/";
        for (int i = 0; i < results.size(); i++) {
        //JsonElement result = results.get(i);
            String jsonMessage = results.get(i).toString();
            System.out.println(jsonMessage); //JSON string you are sending

        //Sending over to database API
            URL myurl = new URL(targeturl);
            HttpURLConnection con = (HttpURLConnection)myurl.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json;");
            con.setRequestProperty("Accept", "application/json,text/plain");
            con.setRequestProperty("Method", "POST");
            OutputStream os = con.getOutputStream();
            os.write(jsonMessage.toString().getBytes("UTF-8"));
            os.close();
 
 
            StringBuilder sb = new StringBuilder();  
            int HttpResult =con.getResponseCode();
            if(HttpResult ==HttpURLConnection.HTTP_OK){
            BufferedReader br = new BufferedReader(new   InputStreamReader(con.getInputStream(),"utf-8"));  
 
            String line = null;
            while ((line = br.readLine()) != null) {  
            sb.append(line + "\n");  
            }
             br.close(); 
             System.out.println(""+sb.toString());  
 
        }else{
            System.out.println(con.getResponseCode());
            System.out.println(con.getResponseMessage());  
        }
    }  
}

    public static void main(final String[] args) {
        try {
            JsonArray results = PullFromAPI.sendJson("{\"dateFrom\":\"2020-07-14\" , \"dateTo\":\"2020-07-14\"}");
            // Sending to database 
            SendtoDatabase(results);
        } catch (final MalformedURLException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}