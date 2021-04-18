package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BooksResultListAdapter extends RecyclerView.Adapter<BooksResultListAdapter.ViewHolder> {

    private List<BookInfo> mBooksData;
    private final Context mContext;

    public BooksResultListAdapter(Context context, List<BookInfo> booksData){
        mBooksData = booksData;
        mContext = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView author;
        TextView url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.bookTitleItem);
            author = itemView.findViewById(R.id.bookAuthorItem);
            url = itemView.findViewById(R.id.bookUrlItem);
        }

        @Override
        public void onClick(View v) {
            String strUrl = (String) url.getText();
            if(strUrl.isEmpty()) return;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(strUrl));
            mContext.startActivity(i);
        }
    }

    @NonNull
    @Override
    public BooksResultListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksResultListAdapter.ViewHolder holder, int position) {
        BookInfo bookInfo = mBooksData.get(position);
        holder.title.setText(bookInfo.getTitle());
        holder.author.setText(bookInfo.getAuthor());
        holder.url.setText(bookInfo.getLink()!= null? bookInfo.getLink().toString() : "");
    }

    @Override
    public int getItemCount() {
        return mBooksData.size();
    }

    public void setBooksData(List<BookInfo> data){
        mBooksData = data;
    }

}
