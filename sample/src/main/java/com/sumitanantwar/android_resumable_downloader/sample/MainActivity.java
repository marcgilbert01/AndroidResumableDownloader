package com.sumitanantwar.android_resumable_downloader.sample;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sumitanantwar.android_resumable_downloader.DownloadCallback;
import com.sumitanantwar.android_resumable_downloader.DownloadRequest;
import com.sumitanantwar.android_resumable_downloader.Downloadable;
import com.sumitanantwar.android_resumable_downloader.RetryMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private final Context context = this;
    private TextView textViewRequest1;
    private TextView textViewRequest2;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textViewRequest1 = (TextView) findViewById(R.id.textView_request1);
        textViewRequest2 = (TextView) findViewById(R.id.textView_request2);
        handler = new Handler();



        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button.setEnabled(false);


                String baseUrl = "http://staticfiles.popguide.me/";
                String extn = ".tgz";
                String baseDestination = getApplicationContext().getFilesDir() + File.separator + "Packages" + File.separator;


                // REQUEST 1 AND STARTED
                String[] files = {"it", "jap", "kor" };
                final List<Downloadable> downloadables = new ArrayList<Downloadable>();
                for (String fn : files) {

                    String url = baseUrl + fn + extn;
                    String destn = baseDestination + fn + extn;

                    Downloadable d = new Downloadable(url, destn);
                    downloadables.add(d);
                }

                final DownloadRequest request1 = new DownloadRequest(context);
                new Thread(){

                    @Override
                    public void run() {

                        DownloadRequest request1 = new DownloadRequest(context);
                        request1.download(downloadables, new DownloadCallback() {
                            @Override
                            public void onComplete(Downloadable downloadable) {

                                Log.i(LOG_TAG, "Downloaded : " + downloadable.getTargetUrl() + " - to : " + downloadable.getDestinationPath());
                            }

                            @Override
                            public void onDownloadComplete() {

                                //Toast.makeText(context, "Download Complete", Toast.LENGTH_LONG).show();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        textViewRequest1.setText( "Download Complete" );
                                    }
                                });
                            }

                            @Override
                            public void onDownloadProgress(final long completedBytes, final long totalBytes) {

                                //Log.i(LOG_TAG, String.format("Download Progress : %s / %s", completedBytes, totalBytes));
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        textViewRequest1.setText( String.format("Download Progress : %s / %s", completedBytes, totalBytes) );
                                    }
                                });
                            }

                            @Override
                            public void onDownloadFailure(RetryMode retryMode) {

                                final String msg = "Download Failed " + retryMode.name();
                                Log.e(LOG_TAG, msg);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                                        textViewRequest1.setText( msg );
                                    }
                                });
                            }
                        });
                    }
                }.start();




                // REQUEST 2
                String[] files2 = {"pl", "pt", "ru", "en"};
                List<Downloadable> downloadables2 = new ArrayList<Downloadable>();
                for (String fn : files2) {

                    String url = baseUrl + fn + extn;
                    String destn = baseDestination + fn + extn;

                    Downloadable d = new Downloadable(url, destn);
                    downloadables2.add(d);
                }

                DownloadRequest request2 = new DownloadRequest(context);
                request2.download(downloadables2, new DownloadCallback() {
                    @Override
                    public void onComplete(Downloadable downloadable) {

                        Log.i(LOG_TAG, "Downloaded : " + downloadable.getTargetUrl() + " - to : " + downloadable.getDestinationPath());
                    }

                    @Override
                    public void onDownloadComplete() {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(context, "Download Complete", Toast.LENGTH_LONG).show();
                                textViewRequest2.setText( "Download Complete" );
                            }
                        });
                    }

                    @Override
                    public void onDownloadProgress(final long completedBytes, final long totalBytes) {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Log.i(LOG_TAG, String.format("Download Progress : %s / %s", completedBytes, totalBytes));
                                textViewRequest2.setText( String.format("Download Progress : %s / %s", completedBytes, totalBytes) );
                            }
                        });
                    }

                    @Override
                    public void onDownloadFailure(RetryMode retryMode) {

                        final String msg = "Download Failed " + retryMode.name();
                        Log.e(LOG_TAG, msg);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                                textViewRequest2.setText( msg );
                            }
                        });
                    }
                });











            }
        });
    }

}
