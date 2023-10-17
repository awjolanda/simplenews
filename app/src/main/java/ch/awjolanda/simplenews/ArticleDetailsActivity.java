package ch.awjolanda.simplenews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import ch.awjolanda.simplenews.model.NewsArticle;

public class ArticleDetailsActivity extends AppCompatActivity {
    private NewsArticle article;

    private static final int TITLE_MAX_LENGTH = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        Intent intent = getIntent();
        article = intent.getParcelableExtra(MainActivity.ARTICLE_INTENT_NAME, NewsArticle.class);
        String title = article.getTitle();
        String headline = article.getHeadline();
        String body = article.getBody();
        String author = article.getAuthor();
        URL articleUrl = article.getArticleUrl();
        TextView titleText = findViewById(R.id.article_title);
        titleText.setText(title);
        title = title.length() > TITLE_MAX_LENGTH ? title.substring(0, TITLE_MAX_LENGTH) + "..." : title;
        setTitle(title);
        TextView headlineText = findViewById(R.id.article_description);
        headlineText.setText(headline);
        TextView bodyText = findViewById(R.id.article_content);
        bodyText.setText(body);
        TextView authorText = findViewById(R.id.article_author);
        authorText.setText(author);
        Button articleUrlText = findViewById(R.id.article_url_button);
        articleUrlText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(articleUrl);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("about:blank"));
                startActivity(browserIntent);
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
