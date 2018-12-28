package com.liam_armstrong.projectw1.models;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class TimeZone {
    private final String EXTERNAL_IP_URL = "http://checkip.amazonaws.com/";
    private ZoneId timezone;
    private InetAddress ipAddress;
    private static TimeZone single_TimeZone = null;

    public static TimeZone getInstance(){
        if(single_TimeZone == null){
            single_TimeZone = new TimeZone();
        }
        return single_TimeZone;
    }

    private TimeZone(){
        try {
            // A File object pointing to GeoLite2 database
            File database = new File("GeoLite2-City.mmdb");
            DatabaseReader reader = new DatabaseReader.Builder(database).build();
            try {
                URL url_name = new URL(EXTERNAL_IP_URL);
                BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));
                ipAddress = InetAddress.getByName(sc.readLine().trim());
                sc.close();
                CityResponse response = reader.city(ipAddress);
                Location location = response.getLocation();
                timezone = ZoneId.of(location.getTimeZone());
                reader.close();
            } catch (Exception e) {
                timezone = ZoneId.systemDefault();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

//    private JSONObject queryURLWithParam(String baseURL, Map params){
//        JSONObject result = null;
//        HttpClient httpclient = HttpClients.createDefault();
//        HttpPost httppost = new HttpPost(baseURL + serializeParams(params));
//        try {
//            HttpResponse httpResponse = httpclient.execute(httppost);
//            String stringResponse = EntityUtils.toString(httpResponse.getEntity());
//            result = new JSONObject(stringResponse);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    private String serializeParams(Map<String, String> params){
//        StringBuilder sb = new StringBuilder();
//        sb.append("?");
//        for (Map.Entry<String,String> e: params.entrySet()){
//            sb.append(e.getKey());
//            sb.append("=");
//            sb.append(e.getValue());
//            sb.append("&");
//        }
//        sb.deleteCharAt(sb.lastIndexOf("&"));
//        return sb.toString();
//    }
//
//    private void setTimezone() throws IOException{
//        Map<String, String> params = new HashMap<>();
//        params.put("key", TIMEZONE_DB_API_KEY);
//        params.put("format", "json");
//        params.put("by", "position");
//        params.put("lng", Double.toString(longitude));
//        params.put("lat", Double.toString(latitude));
//
//        timezone = queryURLWithParam(TIMEZONE_DB_URL, params).getString("abbreviation");
//    }

    public ZoneId getTimezone() {
        return timezone;
    }
}
