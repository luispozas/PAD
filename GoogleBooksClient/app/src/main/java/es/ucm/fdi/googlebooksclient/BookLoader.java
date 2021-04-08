package es.ucm.fdi.googlebooksclient;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<BookInfo>> {

    private String query;
    private String type;
    private MainActivity mainActivity;

    public BookLoader(@NonNull Context context, MainActivity mainActivity, String query, String type) {
        super(context);
        this.query = query;
        this.type = type;
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public List<BookInfo> loadInBackground() {
        String json = ServiceUtils.getBookInfoJson(query, type);
        return mainActivity.fromJsonResponse(json);
    }
}
