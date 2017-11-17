package com.example.android.newsfeed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.net.Uri;


class Utils {

    private static final String API_URL_START="https://content.guardianapis.com/search?";
    private static final String QUERY_PARAM = "q";
    private static final String STAR_RATING = "star-rating";
    private static final String PAGE_SIZE = "page-size";
    private static final String FORMAT = "format";
    private static final String TAGS = "show-tags";
    private static final String FIELDS = "show-fields";
    private static final String ORDERBY = "order-by";
    private static final String API_KEY = "api-key";


    static String getInfo(String queryString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String articleJSONString = null;

        try{
            Uri builtURI = Uri.parse(API_URL_START).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(STAR_RATING, "1|2|3|4|5")
                    .appendQueryParameter(PAGE_SIZE, "30")
                    .appendQueryParameter(FORMAT, "json")
                    .appendQueryParameter(TAGS, "contributor")
                    .appendQueryParameter(FIELDS, "starRating,headline,thumbnail,short-url")
                    .appendQueryParameter(ORDERBY, "relevance")
                    .appendQueryParameter(API_KEY, "test")
                    .build();

            URL requestUrl = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null){
                builder.append(line + "\n");
            }

            if (builder.length() == 0){
                return null;
            }

            articleJSONString = builder.toString();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (reader != null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return articleJSONString;
    }

}//class
