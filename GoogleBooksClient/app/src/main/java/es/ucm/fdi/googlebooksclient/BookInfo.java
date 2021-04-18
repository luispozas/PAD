package es.ucm.fdi.googlebooksclient;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookInfo {
    private static final String TAG = "BookInfo";
    String title;
    String author;
    URL link;

    public BookInfo(String title, String author, URL link) {
        this.title = title;
        this.author = author;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public static List<BookInfo> fromJsonResponse(String s){
        List<BookInfo> listBook = new ArrayList<>();
        if(s == null || s.isEmpty()) return listBook;
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            int i = 0;
            int j = 0;
            String title = "";
            String authors = "";
            String link = "";

            while (i < itemsArray.length()) {
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author, title and link from the current item,
                try {
                    title = volumeInfo.getString("title");
                } catch (JSONException e) {
                    Log.d(TAG, e.toString());
                }

                try {
                    JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                    while (j < authorsArray.length()) {
                        if(authorsArray.length() - j <= 1)
                            authors += authorsArray.getString(j);
                        else authors += (authorsArray.getString(j) + ", ");
                        j++;
                    }
                    j = 0;
                }catch (JSONException e){
                    Log.d(TAG, e.toString());
                    authors = "Without authors";
                }

                try{
                    link = volumeInfo.getString("infoLink");
                }
                catch (JSONException e){
                    Log.d(TAG, e.toString());
                }

                listBook.add(new BookInfo(title, authors, link.equals("")? null : new URL(link)));

                // Move to the next item.
                i++;
                title = "";
                authors = "";
                link = "";
            }

        } catch (JSONException | MalformedURLException e) {
            Log.d(TAG, e.toString());
        }

        return  listBook;
    }
}
