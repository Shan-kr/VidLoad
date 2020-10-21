package com.example.vidload;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

public class videoplay extends AppCompatActivity {

    TextView mtitle,mdescription;
    ImageView mthumbnail;

     String link;
    WebView wb;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplay);

        //ActionBar actionbar=getSupportActionBar();
         mtitle=findViewById(R.id.title);
        mdescription=findViewById(R.id.description);
        mthumbnail=findViewById(R.id.thumbnail);
        wb=findViewById(R.id.webview);


        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String description=intent.getStringExtra("description");
        String thumbnail=intent.getStringExtra("thumbnail");
        String videoId=intent.getStringExtra("videoId");
       // actionbar.setTitle("title");

        mtitle.setText(title);
        mdescription.setText(description);
        //Picasso.get().load(thumbnail).into(mthumbnail);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setUseWideViewPort(true);
        wb.getSettings().setBuiltInZoomControls(true);
        wb.getSettings().setPluginState(WebSettings.PluginState.ON);
        String url="https://www.youtube.com/embed/"+videoId;
        link=url;
        wb.loadUrl(url);

    }
    public void linkcopy(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Link",link);
        clipboard.setPrimaryClip(clip);
        Toast toast = Toast.makeText(this, " Link Copied", Toast.LENGTH_LONG);
        toast.show();
    }

    public void downloadvid(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Link",link);
        clipboard.setPrimaryClip(clip);
        Toast toast = Toast.makeText(this, " Link Copied", Toast.LENGTH_LONG);
        toast.show();
        Intent intent2=new Intent(videoplay.this,Downloadvideo.class);
        startActivity(intent2);
    }
}
