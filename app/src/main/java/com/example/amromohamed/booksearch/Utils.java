package com.example.amromohamed.booksearch;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<SearchResultData> fetchSearchResults(String mUrl) {

        // Create URL object
        URL url = createUrl(mUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<SearchResultData> data = extractDataFromJson(jsonResponse);
        return data;
    }

    private static List<SearchResultData> extractDataFromJson(String jsonResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        List<SearchResultData> list = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);

            JSONArray jsonArray = baseJsonResponse.getJSONArray("items");
            for (int i=0; i<jsonArray.length();i++){

                String title = "N/A";
                String subTitile = "N/A";
                String publisher = "N/A";
                String publishedDate = "N/A";
                String description = "N/A";
                String pageCount = "N/A";
                String averagerating ="N/A";
                String ratingsCount = "N/A";
                String thumbnail = "N/A";
                String authorsNmaes[] = {"N/A"};


                JSONObject currentBook= jsonArray.getJSONObject(i);
                if(currentBook.has("volumeInfo")) {
                    JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
                    if(volumeInfo.has("title"))
                        title = volumeInfo.getString("title");
                    if(volumeInfo.has("subtitle"))
                        subTitile = volumeInfo.getString("subtitle");
                    if(volumeInfo.has("authors")) {
                        JSONArray authors = volumeInfo.getJSONArray("authors");
                        authorsNmaes = new String[authors.length()];

                        for (int j = 0; j < authors.length(); j++)
                            authorsNmaes[j] = authors.getString(j);
                    }
                    if(volumeInfo.has("publisher"))
                        publisher = volumeInfo.getString("publisher");
                    if(volumeInfo.has("publishedDate"))
                        publishedDate = volumeInfo.getString("publishedDate");
                    if(volumeInfo.has("description"))
                        description = volumeInfo.getString("description");
                    if(volumeInfo.has("pageCount"))
                    pageCount = volumeInfo.getString("pageCount");
                    if(volumeInfo.has("averageRating"))
                        averagerating = volumeInfo.getString("averageRating");
                    if(volumeInfo.has("ratingsCount"))
                        ratingsCount = volumeInfo.getString("ratingsCount");
                    if(volumeInfo.has("imageLinks")) {
                        JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                        thumbnail = imageLinks.getString("thumbnail");
                    }
                }
                list.add(new SearchResultData(title,subTitile,authorsNmaes,publisher,publishedDate,
                        description,pageCount,averagerating,ratingsCount,thumbnail));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    boolean isHas(JSONObject jsonObject,String fieldName){
        return jsonObject.has(fieldName);
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream= null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
            if(inputStream!=null)
                inputStream.close();
        }
        return jsonResponse;
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if(inputStream !=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = reader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    private static URL createUrl(String mUrl) {
        URL url = null;
        try {
            url=new URL(mUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
