package ch.awjolanda.simplenews.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.net.URL;
import java.time.LocalDateTime;

public class NewsArticle implements Parcelable {
    private String title;
    private String headline;
    private String body;
    private String author;
    private String newsSourceName;
    private LocalDateTime publishedAt;
    private URL articleUrl;
    private URL headerImageUrl;

    public NewsArticle(String title, String headline, String body, String author, String newsSourceName, LocalDateTime publishedAt, URL articleUrl, URL headerImageUrl) {
        this.title = title;
        this.headline = headline;
        this.body = body;
        this.author = author;
        this.newsSourceName = newsSourceName;
        this.publishedAt = publishedAt;
        this.articleUrl = articleUrl;
        this.headerImageUrl = headerImageUrl;
    }

    public NewsArticle() {
    }

    protected NewsArticle(Parcel in) {
        title = in.readString();
        headline = in.readString();
        body = in.readString();
        author = in.readString();
        newsSourceName = in.readString();
    }

    public static final Creator<NewsArticle> CREATOR = new Creator<NewsArticle>() {
        @Override
        public NewsArticle createFromParcel(Parcel in) {
            return new NewsArticle(in);
        }

        @Override
        public NewsArticle[] newArray(int size) {
            return new NewsArticle[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getHeadline() {
        return headline;
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public String getNewsSourceName() {
        return newsSourceName;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public URL getArticleUrl() {
        return articleUrl;
    }

    public URL getHeaderImageUrl() {
        return headerImageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setNewsSourceName(String newsSourceName) {
        this.newsSourceName = newsSourceName;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setArticleUrl(URL articleUrl) {
        this.articleUrl = articleUrl;
    }

    public void setHeaderImageUrl(URL headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(headline);
        parcel.writeString(body);
        parcel.writeString(author);
        parcel.writeString(newsSourceName);
    }
}
