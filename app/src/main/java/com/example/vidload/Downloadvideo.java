package com.example.vidload;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.yausername.youtubedl_android.DownloadProgressCallback;
import com.yausername.youtubedl_android.YoutubeDL;
import com.yausername.youtubedl_android.YoutubeDLRequest;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class Downloadvideo extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private EditText etUrl;
    private ProgressBar progressBar;
    private TextView tvDownloadStatus;
    //private TextView tvCommandOutput;
    private ProgressBar pbLoading;
   // Button downloadbtn;

    private boolean downloading = false;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private DownloadProgressCallback callback = new DownloadProgressCallback() {
        @Override
        public void onProgressUpdate(float progress, long etaInSeconds) {
            runOnUiThread(() -> {
                        progressBar.setProgress((int) progress);
                        tvDownloadStatus.setText(String.valueOf(progress) + "% (" + String.valueOf(etaInSeconds) + " Seconds Remaining");
                    }
            );
        }
    };

    private static final String TAG = "DownloadingExample";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloadvideo);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        etUrl = findViewById(R.id.et_url);
        progressBar = findViewById(R.id.progress_bar);
        tvDownloadStatus = findViewById(R.id.tv_status);
        pbLoading = findViewById(R.id.pb_status);
        //tvCommandOutput = findViewById(R.id.tv_command_output);
       // downloadbtn=findViewById(R.id.downloadbtn);

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Home:
                Intent intent=new Intent(Downloadvideo.this,firstactivity.class);
                startActivity(intent);
                break;
            case R.id.Play:
                Intent intent2=new Intent(Downloadvideo.this,Home.class);
                startActivity(intent2);
                break;
            case R.id.downloadvideo:
                break;
            case R.id.insta_profile:
                Intent intent1 = new Intent(Downloadvideo.this, Instaprofile.class);
                startActivity(intent1);
                break;
        }
        return true;
    }

   /*   private class RequestDownloadVideoStream extends AsyncTask<String,String,String>{

        private ProgressDialog dialog = new ProgressDialog(Downloadvideo.this);
        private String result;

        protected void onPreExecute() {
            dialog.setMessage("Downloading...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... url) {

            InputStream is = null;
            URL u = null;
            String url1=Uri.decode("https://www.youtube.com/embed/PMCu0Jtizck");
            try {
                u = new URL(url1);
                is = u.openStream();
                HttpURLConnection huc = (HttpURLConnection)u.openConnection(); //to know the size of video
                int size = huc.getContentLength();

                if(huc != null) {
                    String fileName = "FILE.mp4";
                    String storagePath = Environment.getExternalStorageDirectory().toString();
                    File f = new File(storagePath,fileName);

                    FileOutputStream fos = new FileOutputStream(f);
                    byte[] buffer = new byte[1024];
                    int len1 = 0;
                    if(is != null) {
                        while ((len1 = is.read(buffer)) > 0) {
                            fos.write(buffer,0, len1);
                        }
                    }
                    if(fos != null) {
                        fos.close();
                    }
                }
            } catch (MalformedURLException mue) {
                mue.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                try {
                    if(is != null) {
                        is.close();
                    }
                } catch (IOException ioe) {
                    // just going to ignore this one
                }
            }
            return null;
        }
        protected void onPostExecute(Void unused) {
            dialog.dismiss();
        }

    }

*/

    public void Downloadvideo(View view)  {
        EditText input = (EditText) findViewById(R.id.inputfield);
        String url = input.getText().toString();
        // Toast.makeText(this, "Downloading"+" "+url, Toast.LENGTH_LONG).show();
        String url1=Uri.decode(url);
       // new RequestDownloadVideoStream().execute(url1);

                startDownload(url);

    }

    private void startDownload(String url) {
        if (downloading) {
            Toast.makeText(Downloadvideo.this, "cannot start download. a download is already in progress", Toast.LENGTH_LONG).show();
            return;
        }

        if (!isStoragePermissionGranted()) {
            Toast.makeText(Downloadvideo.this, "grant storage permission and retry", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(url)) {
            etUrl.setError(getString(R.string.url_error));
            return;
        }

        YoutubeDLRequest request = new YoutubeDLRequest(url);
        File youtubeDLDir = getDownloadLocation();
        request.addOption("-o", youtubeDLDir.getAbsolutePath() + "/%(title)s.%(ext)s");

        showStart();

        downloading = true;
        Disposable disposable = Observable.fromCallable(() -> YoutubeDL.getInstance().execute(request, callback))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(youtubeDLResponse -> {
                    pbLoading.setVisibility(View.GONE);
                    progressBar.setProgress(100);
                    tvDownloadStatus.setText(getString(R.string.download_complete));
                  //  tvCommandOutput.setText(youtubeDLResponse.getOut());
                    Toast.makeText(Downloadvideo.this, "Download successful", Toast.LENGTH_LONG).show();
                    downloading = false;
                }, e -> {
                    if(BuildConfig.DEBUG) Log.e(TAG,  "failed to download", e);
                    pbLoading.setVisibility(View.GONE);
                    tvDownloadStatus.setText(getString(R.string.download_failed));
                    Log.d("download",e.getMessage());
                    Toast.makeText(Downloadvideo.this, "Download failed", Toast.LENGTH_LONG).show();
                    downloading = false;
                });
        compositeDisposable.add(disposable);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @NonNull
    private File getDownloadLocation() {
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File youtubeDLDir = new File(downloadsDir, "youtubedl-android");

        if (!youtubeDLDir.exists())
            youtubeDLDir.mkdir();

        return youtubeDLDir;
    }

    private void showStart() {
        tvDownloadStatus.setText(getString(R.string.download_start));
        progressBar.setProgress(0);
        pbLoading.setVisibility(View.VISIBLE);
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }
}

