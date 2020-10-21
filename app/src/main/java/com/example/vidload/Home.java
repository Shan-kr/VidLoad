package com.example.vidload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity implements Adapter.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ListView list;
    RecyclerView recyclerView;
    List<Videos> videosList;
    Adapter adapter;
    WebView wb;
    public static final String title="title";
    public static final  String description="description";
    public  static final  String thumbnail="thumbnail";
    public  static  final String videoId="videoId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       recyclerView=findViewById(R.id.videolist);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        wb=findViewById(R.id.webview);

        setSupportActionBar(toolbar);
          navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    public void Search(View view) {
        // MaterialButton input_url = view.findViewById(R.id.search);
        EditText input_url = (EditText) findViewById(R.id.search);
        String url = input_url.getText().toString();
        Log.d("url", url);

        Toast toast = Toast.makeText(this, url, Toast.LENGTH_LONG);
        toast.show();
        loadres(url);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new Adapter(this,videosList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(Home.this);

    }
    private void loadres(final String q){
        videosList =new ArrayList<>();

       // final TextView textView = (TextView) findViewById(R.id.result);
        RequestQueue queue = Volley.newRequestQueue(this);
        String address ="https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=25&q="+q+"&key=AIzaSyAK7NAoHGz6y4RMNiVnhDVrPZK7azoaqkY";

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, address, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("items");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                JSONObject id=jo.getJSONObject("id");
                                JSONObject snippet=jo.getJSONObject("snippet");
                                JSONObject thumbnail =snippet.getJSONObject("thumbnails");
                                JSONObject mediumurl=thumbnail.getJSONObject("medium");



                                Videos vid=new Videos();
                                vid.setTitle(snippet.getString("title").toString());
                                vid.setDescription(snippet.getString("description").toString());

                                vid.setThumbnail(mediumurl.getString("url").toString());
                                if(!id.isNull("videoId")){
                                   vid.setVideoId(id.getString("videoId").toString());
                               }
                                if(!id.isNull("channelId")){
                                    vid.setVideoId(id.getString("channelId").toString());
                                }


                                videosList.add(vid);


                                String title =snippet.getString("title");
                              // textView.setText(title.toString());
                                Log.d("response",snippet.toString());
                                Log.d("response1",jo.toString());
                                Log.d("response2",mediumurl.toString());


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                       Log.d("error","It didn't work");

                    }
                });

        queue.add(jsonObjectRequest);
    }

    @Override
    public void OnItemClick( int position) {
        Intent intent = new Intent(Home.this,videoplay.class);
        Videos clickedItem = videosList.get(position);
        intent.putExtra(title,clickedItem.getTitle());
        intent.putExtra(description,clickedItem.getDescription());
        intent.putExtra(thumbnail,clickedItem.getThumbnail());
        intent.putExtra(videoId, clickedItem.getVideoId());
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Home:
                Intent intent=new Intent(Home.this,firstactivity.class);
                startActivity(intent);
                break;
            case R.id.Play:
                break;
            case R.id.downloadvideo:
                         Intent intent2=new Intent(Home.this,Downloadvideo.class);
                         startActivity(intent2);
                         break;
            case R.id.insta_profile:
                    Intent intent1=new Intent(Home.this,Instaprofile.class);
                    startActivity(intent1);
                    break;
        }
        return true;
    }
}
