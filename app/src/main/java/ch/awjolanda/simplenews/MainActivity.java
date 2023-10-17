package ch.awjolanda.simplenews;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ch.awjolanda.simplenews.model.NewsArticle;

public class MainActivity extends AppCompatActivity {

    public static final String ARTICLE_INTENT_NAME = "article";

    private static final String NEWS_API_URL = "https://newsapi.org/v2/everything?q=keyword&apiKey=07d52032d45d43fb997f0a73e1d3675e";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.article_list_loading);
        progressBar.setVisibility(View.VISIBLE);

        ArrayAdapter<NewsArticle> newsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);

        ListView newsArticlesView = findViewById(R.id.article_list);
        newsArticlesView.setAdapter(newsAdapter);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, NEWS_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<NewsArticle> newsArticles = NewsArticleJsonParser.parseNewsArticleQueryResult(response);
                    newsAdapter.addAll(newsArticles);
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    generateAlertDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                generateAlertDialog();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0");

                return params;


            }
        };

        queue.add(stringRequest);

        newsArticlesView.setOnItemClickListener((parent, v, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), ArticleDetailsActivity.class);
            NewsArticle selected = (NewsArticle) parent.getItemAtPosition(position);
            intent.putExtra(ARTICLE_INTENT_NAME, selected);
            startActivity(intent);
        });
    }

    private void generateAlertDialog() {
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.error_box, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    String searchText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Suche");
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(getApplicationContext(), ArticleSearchResultsActivity.class);
                intent.putExtra("searchString", s);
                startActivity(intent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchText = s;
                return true;
            }
        });

        return true;
    }
}