package es.ucm.fdi.googlebooksclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int BOOK_LOADER_ID = 0;

    private  BookLoaderCallbacks bookLoaderCallbacks;

    Button searchButton;
    EditText author;
    EditText title;
    RadioGroup radioButton;
    RadioButton defaultAll;
    TextView result;
    RecyclerView recyclerListBook;

    BooksResultListAdapter brla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookLoaderCallbacks = new BookLoaderCallbacks(getApplicationContext(), this);

        brla = new BooksResultListAdapter(new ArrayList<>());
        execListener();

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(BOOK_LOADER_ID) != null){
            loaderManager.initLoader(BOOK_LOADER_ID,null, bookLoaderCallbacks);
        }
    }

    public void execListener(){
        author = findViewById(R.id.bookAuthors);
        title = findViewById(R.id.bookTitle);
        radioButton = findViewById(R.id.radioGroup);
        defaultAll = findViewById(R.id.radioButtonAll);
        defaultAll.setChecked(true);
        result = findViewById(R.id.results);

        recyclerListBook = findViewById(R.id.listBook);
        recyclerListBook.setLayoutManager(new LinearLayoutManager(this));
        recyclerListBook.setAdapter(brla);

        searchButton = findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBooks(v);
            }
        });
    }

    public void searchBooks(View view){
        
        String queryString = author.getText() + " " + title.getText();
        String printType;
        
        switch (radioButton.getCheckedRadioButtonId()){
            case (R.id.radioButtonAll): printType = "All"; break;
            case (R.id.radioButtonBooks): printType = "Books"; break;
            case (R.id.radioButtonMagazines): printType = "Magazines"; break;
            default:
                throw new IllegalStateException("Unexpected value in radioButton");
        }
        
        
        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);
        LoaderManager.getInstance(this)
                .restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);

    }

    public void updateBooksResultList(List<BookInfo> bookInfos){
        if(bookInfos.isEmpty()){
            result.setText("No hay resultados");
        }
        else{
            result.setText("Resultados");
        }

        brla.setBooksData(bookInfos);
        recyclerListBook.getAdapter().notifyDataSetChanged();
    }

    public List<BookInfo> fromJsonResponse(String s){
        List<BookInfo> listBook = new ArrayList<BookInfo>();
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
                // catch if either field is empty and move on.
                try {
                    title = volumeInfo.getString("title");

                    JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                    while (j < authorsArray.length()){
                        authors += (authorsArray.getString(j) + " ");
                        j++;
                    }
                    j = 0;

                    link = volumeInfo.getString("infoLink");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                listBook.add(new BookInfo(title, authors, link.equals("")? null : new URL(link)));

                // Move to the next item.
                i++;
                title = "";
                authors = "";
                link = "";
            }

        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }

        return  listBook;
    }

}