package ch.awjolanda.simplenews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import ch.awjolanda.simplenews.model.NewsArticle;

public class NewsArticleJsonParser {

    public static List<NewsArticle> parseNewsArticleQueryResult(String queryResult) throws JSONException {
        JSONObject jsonObj = new JSONObject(queryResult);
        JSONArray articlesArray = jsonObj.getJSONArray("articles");

        List<NewsArticle> articles = new ArrayList<>();
        for (int i = 0; i < articlesArray.length(); i++) {
            JSONObject articleJson = articlesArray.getJSONObject(i);
            NewsArticle article = new NewsArticle();

            article.setTitle(articleJson.getString("title"));
            article.setHeadline(articleJson.getString("description"));
            article.setBody(articleJson.getString("content"));
            article.setAuthor(articleJson.getString("author"));
            article.setNewsSourceName(articleJson.getString("source"));

            String publishedAtString = articleJson.getString("publishedAt");
            LocalDateTime publishedAtDateTime = LocalDateTime.parse(publishedAtString.substring(0, publishedAtString.length() - 1));
            article.setPublishedAt(publishedAtDateTime);

            URL articleUrl = null;
            String articleUrlString = articleJson.getString("url");
            try {
                articleUrl = new URL(articleUrlString);
            } catch (MalformedURLException e) {
            }
            article.setArticleUrl(articleUrl);

            articles.add(article);
        }

        return articles;
    }

}
