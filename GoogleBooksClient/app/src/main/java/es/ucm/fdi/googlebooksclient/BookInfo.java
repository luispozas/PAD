package es.ucm.fdi.googlebooksclient;

import java.net.URL;

public class BookInfo {
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
}
