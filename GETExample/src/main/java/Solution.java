import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class Solution {

    public static void main(String[] args) throws Exception {
        System.out.println(getCountries("un", 100090));
    }

    public static int getCountries(String str, int p) throws Exception {
        String searchUrl ="https://jsonmock.hackerrank.com/api/countries/search?name=" + str + "&page=";
        long currentPage = 1;
        long targetPage = 1;
        JSONParser parser = new JSONParser();

        int count = 0;
        while (true) {
            try {
                String url = searchUrl + currentPage;
                String response = sendGET(url);
                Object obj = parser.parse(response);
                JSONObject jsonObject = (JSONObject) obj;
                targetPage = (Long) jsonObject.get("total_pages");
                // loop array
                JSONArray data = (JSONArray) jsonObject.get("data");
                Iterator<JSONObject> iterator = data.iterator();
                while (iterator.hasNext()) {
                    JSONObject country = iterator.next();
                    if((Long) country.get("population") > p)
                        count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            currentPage++;
            if(currentPage > targetPage)
                break;
        }

        return count;
    }
    private static String sendGET(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        //con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } else {
            return "";
        }
    }
}
