package br.com.ca.asap.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.ca.shareview.R;
import br.com.ca.asap.demo.DemoSynchronize;
import br.com.ca.asap.vo.UserVo;

/*
 * SynchronizeActivity
 * TODO: need refactoring. demo version.
 */
public class SynchronizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronize);
        //start synchronization right after creation basic steps
        doSynchronize();
    }

    // Do synchronization
    private void doSynchronize(){
        new DoAsyncSynchronize().execute("demo");
    }

    // Uses AsyncTask to create a task away from the main UI thread, and synchronize the data with CA Server.
    //
    private class DoAsyncSynchronize extends AsyncTask<String, Void, Boolean> {
        Resources res = getResources();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        @Override
        protected void onPreExecute(){
            //show progress bar view
            ProgressBar bar = (ProgressBar) findViewById(R.id.progressBarSynchronizing);
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String...params) {
            InputStream is = null;
            int len = 500; // Only display the first 500 characters of the retrieved web page content.

            Boolean dataSynchronized = false;

            //
            //TODO: This Thread.sleep code is for application test only. Should be removed in the final version
            //

            if (params[0].equals("demo")) {

                //demo synchronize
                DemoSynchronize demoSynchronize = new DemoSynchronize();
                demoSynchronize.demoSynchronize(context);

                //return true for successful synchronization
                return true;

            } else {

                return true;
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Boolean result) {
            Context context = getApplicationContext();

            //hide progress bar view
            ProgressBar bar = (ProgressBar) findViewById(R.id.progressBarSynchronizing);
            bar.setVisibility(View.GONE);

            //check if the result, sent as a Boolean by doInBackGround, is true
            //...and call initiatives activity
            if(result.booleanValue() == true) {
                Intent intent = new Intent(context, InitiativesActivity.class);
                intent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                //TODO: need refactoring. write code for message and option to go retry ou go to local processing
                Log.d("SynchronizeActivity","a problem with synchronization activity");
            }
        }

        //
        // helper methods for login activity
        //

        // Reads an InputStream and converts it to a String.
        public String readIt(InputStream stream, int len) throws IOException {
            Reader reader;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }
    }
}
