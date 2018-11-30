package com.example.android.newsapp.Utils;

import com.example.android.newsapp.Models.NewsItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public  static List<NewsItem> parseNews(String jsonResult)
    {
        ArrayList<NewsItem> newsList = new ArrayList<>();
        try{
            JSONObject mainJsonObject = new JSONObject(jsonResult);
            JSONArray items = mainJsonObject.getJSONArray("articles");
            for (int i = 0; i<items.length(); i++){
                JSONObject item = items.getJSONObject(i);
                newsList.add(new NewsItem(
                        item.getString("title" ),
                        item.getString("description"),
                        item.getString("url"),
                        item.getString("publishedAt"),
                        item.getString("urlToImage")
                        ));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return newsList;
    }
}
