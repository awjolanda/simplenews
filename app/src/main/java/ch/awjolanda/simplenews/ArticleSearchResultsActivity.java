package ch.awjolanda.simplenews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;

import java.util.List;

import ch.awjolanda.simplenews.model.NewsArticle;

public class ArticleSearchResultsActivity extends AppCompatActivity {
    private NewsArticle article;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_search_results);
        progressBar = findViewById(R.id.article_search_list_loading);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        article = intent.getParcelableExtra(MainActivity.ARTICLE_INTENT_NAME, NewsArticle.class);

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

    private void generateAlertDialog() {
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        dialogBuilder.setMessage("News-Artikel konnte nicht geladen werden.").setTitle("Fehler");
        dialogBuilder.setView(getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null));
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
