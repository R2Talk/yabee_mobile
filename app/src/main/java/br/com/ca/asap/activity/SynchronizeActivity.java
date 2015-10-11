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
import br.com.ca.asap.vo.LoginStatusVo;

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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (params[0].equals("demo")) {

                //demo synchronize
                DemoSynchronize demoSynchronize = new DemoSynchronize();
                demoSynchronize.demoSynchronize(context);

                //return true for successful synchronization
                return true;

            } else {

                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                //check if the device has a valid internet connection
                if (networkInfo != null && networkInfo.isConnected()) {//..if it has, do login in CA Server
                    Log.d("SynchronizeActivity", "CA: Network Connection Found");
                    //
                    //CA server login
                    //
                    //Send CA Server http request for login validation
                    try {
                        Gson gson = new Gson();

                        //loginStatusVo usar data
                        LoginStatusVo loginStatusVo = new LoginStatusVo(params[0], params[1], false);

                        //format request URL
                        URL url = new URL("http://192.168.0.7:8080/CAWebApp/DoLoginServlet" + "?" + "loginData=" + gson.toJson(loginStatusVo));

                        //open connection and prepare request parameters
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(10000 /* milliseconds */);
                        conn.setConnectTimeout(5000 /* milliseconds */);
                        conn.setRequestMethod("GET");
                        //conn.setDoInput(true);
                        //starts the query
                        //conn.connect();
                        //int response = conn.getResponseCode();
                        //Log.d("LoginActivity", "server response is: " + response);

                        Log.d("SynchronizeActivity", "CA: trying conn.getInputStream");

                        //do the http request
                        is = conn.getInputStream();

                        // Convert the InputStream into a string
                        String contentAsString = readIt(is, len);
                        Log.d("SynchronizeActivity", "CA: reade from http connection: " + contentAsString);


                        //If the user is valid, do call to Synchronize
                        Log.d("SynchronizeActivity", "CA: ...here must call Synchronization object");

                        //else, indicates de user is not valid


                        // Makes sure that the InputStream is closed after the app is
                        // finished using it.

                    } catch (Exception e) {
                        Log.d("SynchronizeActivity", e.getMessage());
                    } finally {
                        try {
                            if (is != null) {
                                is.close();
                            }
                        } catch (IOException e) {
                            Log.d("SynchronizeActivity", e.getMessage());
                        }
                    }

                } else {
                    //Device not connected
                    Log.d("SynchronizeActivity", "CA: Network Connection Not Found");
                    String text = res.getString(R.string.device_not_connect);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

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
