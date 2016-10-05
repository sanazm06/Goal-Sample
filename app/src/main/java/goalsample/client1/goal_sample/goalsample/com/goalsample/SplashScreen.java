package goalsample.client1.goal_sample.goalsample.com.goalsample;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_SHOW_TIME = 2000;
    private ProgressBar progressOverlay;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        progressOverlay = (ProgressBar) findViewById(R.id.progress);
        progressOverlay.setProgress(100);

        new BackgroundSplashTask().execute();


    }
    private class BackgroundSplashTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            // I have just give a sleep for this thread
            // if you want to load database, make
            // network calls, load images
            // you can do here and remove the following
            // sleep

            // do not worry about this Thread.sleep
            // this is an async task, it will not disrupt the UI
            try {
                Thread.sleep(SPLASH_SHOW_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            // any info loaded can during splash_show
            // can be passed to main activity using
            // below
            i.putExtra("loaded_info", " ");
            startActivity(i);
            finish();
        }

    }

}
