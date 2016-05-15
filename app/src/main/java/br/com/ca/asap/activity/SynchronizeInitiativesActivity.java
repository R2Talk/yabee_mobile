package br.com.ca.asap.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.ca.asap.preferences.PreferencesHelper;
import br.com.ca.asap.synchronize.Synchronizer;
import br.com.ca.shareview.R;
import br.com.ca.asap.demo.DemoSynchronize;

/*
 * SynchronizeInitiativesActivity
 * TODO: need refactoring. demo version.
 *
 * obs: in manifest uses the android:noHistory attribute.
 */
public class SynchronizeInitiativesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronize_initiatives);
        //start synchronization right after creation basic steps
        doSynchronize();
    }

    // Do synchronization
    private void doSynchronize(){
        new DoAsyncSynchronize().execute("");
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

            Boolean dataSynchronized = false;

            if (params[0].equals("demo")) { //TODO: remove. test purpose only.

                //demo synchronize
                DemoSynchronize demoSynchronize = new DemoSynchronize();
                demoSynchronize.demoSynchronize(context);

                //return true for successful synchronization
                return true;

            } else {

                //hive fetch
                Synchronizer synchronizer = new Synchronizer(context); //TODO: pass userId as parameter
                synchronizer.deleteAndFetchInitiatives();

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

                //
                //save the last synchronization date.
                //
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date date = new Date();
                PreferencesHelper preferencesHelper = new PreferencesHelper(getApplicationContext(), PreferencesHelper.APP_PREFERENCES);
                preferencesHelper.setStringPreferenceValue(PreferencesHelper.LAST_SYNC, dateFormat.format(date));

                Intent intent = new Intent(context, InitiativesActivity.class);
                startActivity(intent);

            } else {
                //TODO: need refactoring. write code for message and option to go retry ou go to local processing

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
