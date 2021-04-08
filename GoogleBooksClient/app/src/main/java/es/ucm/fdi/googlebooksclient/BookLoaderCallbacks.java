package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>> {

    public static String EXTRA_PRINT_TYPE = "Type";
    public static String EXTRA_QUERY = "Query";

    Context context;
    MainActivity mainActivity;

    public BookLoaderCallbacks(Context context, MainActivity mainActivity){
        this.context = context;
        this.mainActivity = mainActivity;

    }

    @NonNull
    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, @Nullable Bundle args) {
        String query = args.getString(EXTRA_QUERY);
        String type = args.getString(EXTRA_PRINT_TYPE);
        return new BookLoader(context, mainActivity, query, type);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<BookInfo>> loader, List<BookInfo> data) {
        mainActivity.updateBooksResultList(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<BookInfo>> loader) {

    }
}
