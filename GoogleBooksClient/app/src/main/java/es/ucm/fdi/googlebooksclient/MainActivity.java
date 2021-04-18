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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int BOOK_LOADER_ID = 0;

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

        brla = new BooksResultListAdapter(this, new ArrayList<>());
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
        result.setText(R.string.results_loading_tag);
        result.setVisibility(View.VISIBLE);
        brla.setBooksData(new ArrayList<>());
        recyclerListBook.getAdapter().notifyDataSetChanged();
        
        Bundle queryBundle = new Bundle();
        queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);
        LoaderManager.getInstance(this)
                .restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);

    }

    public void updateBooksResultList(List<BookInfo> booksInfo){

        if(booksInfo.isEmpty()){
            result.setText(R.string.results_not_found_tag);
        }
        else{
            result.setText(R.string.results_found_tag);
        }

        brla.setBooksData(booksInfo);
        recyclerListBook.getAdapter().notifyDataSetChanged();
    }


}