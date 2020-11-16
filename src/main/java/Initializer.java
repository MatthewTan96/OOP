package main.java;
import main.java.*;
import java.util.Scanner;
import java.lang.Thread;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  
import com.google.gson.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Initializer {

    public static void sendMessageWeek(String apiKey) throws MalformedURLException, IOException{
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime sixDaysLater = today.plusDays(6);
        // Getting rid of the time 
        String nowString = dtf.format(today);
        String sixDaysLaterString = dtf.format(sixDaysLater);
        System.out.println(nowString);
        System.out.println(sixDaysLaterString);

        String JsonMessage = "{\"dateFrom\":\"" + nowString +"\" , \"dateTo\":\""+sixDaysLaterString+"\"}";
        System.out.println(JsonMessage);

        // Actual execution 
        try {
            String results = PullFromAPI.sendJson(JsonMessage,apiKey);
            // Sending to database 
            PullFromAPI.SendtoDatabase(results);
        } catch (final MalformedURLException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessageToday(String apiKey) throws MalformedURLException, IOException{
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime today = LocalDateTime.now();
        // LocalDateTime sixDaysLater = today.plusDays(6);
        // Getting rid of the time 
        String nowString = dtf.format(today);
        // String sixDaysLaterString = dtf.format(sixDaysLater);
        System.out.println(nowString);
        // System.out.println(sixDaysLaterString);

        String JsonMessage = "{\"dateFrom\":\"" + nowString +"\" , \"dateTo\":\""+nowString+"\"}";
        System.out.println(JsonMessage);

        // Actual execution 
        try {
            String results = PullFromAPI.sendJson(JsonMessage,apiKey);
            // Sending to database 
            PullFromAPI.SendtoDatabase(results);
        } catch (final MalformedURLException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static String setDomain(String allowedDomain) throws MalformedURLException, IOException{
        String targeturl = "http://localhost:8080/postDomain";
        String json = "{\"domain\":\"" + allowedDomain+"\"}";
        String results = "Failed";
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
        // con.setRequestProperty("Apikey", apiKey);
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
            results = "" + sb.toString();
            System.out.println(output);
        } else {
            System.out.println(con.getResponseCode());
            System.out.println(con.getResponseMessage());
        }

        // JsonObject JSONObject = new Gson().fromJson(output, JsonObject.class);
        // JsonArray results = JSONObject.get("results").getAsJsonArray(); // returns type object
        // To return string 
        // try{
            // String results = JSONObject.getString("results");
        // } catch (NullPointerException e){
        //     e.printStackTrace();
        // } catch (ClassCastException e){
        //     e.printStackTrace();
        // }
        
        return results;
    }
    
    public static void main(final String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        // Getting non-compileable user input 
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter restricted account domain >");
        String allowedDomain = sc.nextLine();
        
        // Setting the domain
        try{
            setDomain(allowedDomain);
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        
        System.out.print("Enter time interval to call API >");
        int interval = sc.nextInt();
        System.out.println(interval);
        sc.nextLine();
        System.out.print("Enter API Key> ");
        String apiKey = sc.nextLine();

        boolean runOnce = true;
        
        LocalDateTime timeLastTriggered = LocalDateTime.now();

        while (true){
            LocalDateTime now = LocalDateTime.now();

            if (runOnce){
                try{
                    sendMessageToday(apiKey);
                }catch (final MalformedURLException e) {
                    e.printStackTrace();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                
                runOnce = false; 
            }

            // By default, API called every hour regardless 
            // This will call the vessels for the current day 
            // Today variable will be the time it is first called. Extract the hour 
            // Check if there is a change in the hour 
            if (now.getHour() != timeLastTriggered.getHour()){
                try{
                    sendMessageToday(apiKey);
                }catch (final MalformedURLException e) {
                    e.printStackTrace();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

            // // By default, API called every hour regardless 
            // This will call the vessels for the current Week 
            // Today variable will be the time it is first called. Extract the day 
            // Check if there is a change in the day 

            if (now.getDayOfMonth() != timeLastTriggered.getDayOfMonth()){
                try{
                    sendMessageWeek(apiKey);
                }catch (final MalformedURLException e) {
                    e.printStackTrace();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

            // Calling based on the interval 
            System.out.println(dtf.format(timeLastTriggered.plusMinutes(interval)));
            System.out.println(dtf.format(now));
            
            if (dtf.format(timeLastTriggered.plusMinutes(interval)).equals(dtf.format(now))){
                // System.out.println("works");
                timeLastTriggered = now;
                try{
                    sendMessageToday(apiKey);
                }catch (final MalformedURLException e) {
                    e.printStackTrace();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

            // Setting the reset button 
            // Code runs every minute 
            try{
                Thread.sleep(60 * 1000);
            } catch (final InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
