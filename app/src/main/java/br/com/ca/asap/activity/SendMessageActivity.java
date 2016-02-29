package br.com.ca.asap.activity;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import br.com.ca.shareview.R;

/**
 * SendMessageActivity
 *
 * This activity permits the edition of a text message thats sent to the rest server
 *
 */
public class SendMessageActivity extends AppCompatActivity {

    /**
     * onCreate
     *
     * Create activity, set the layout and associate a click listener for the button present in the layout.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                String msg = (String) (((EditText) findViewById(R.id.msgEditText)).getText()).toString();
                new AsyncSendMessage().execute(String.valueOf(msg));

            }
        });
    }

    /**
     * AsyncSendMessage
     *
     * <p/>
     * Uses AsyncTask to create a task away from the main UI thread, and send message to rest server.
     */
    private class AsyncSendMessage extends AsyncTask<String, Void, Integer> {
        Resources res = getResources();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        //possible returned states of network query login
        public final int NOT_CONNECTED = 0;
        public final int SEND_MESSAGE_OK = 1;
        public final int SEND_MESSAGE_ERROR = 2;

        /**
         * onPreExecute
         * <p/>
         * Prepare environment before initiate the background execution.
         * Can be used to change a view element (as a progress bar) indicating that is running a background task.
         */
        @Override
        protected void onPreExecute() {
        }

        /**
         * doInBackgroud
         * <p/>
         * AsyncExecution. Executes in its own thread in background.
         * <p/>
         * If it is not demo user, query a REST service to check the state of the user. Returns true if it is a valid user.
         *
         * @param params
         * @return
         */
        @Override
        protected Integer doInBackground(String... params) {

            // variable thats maintains return status for original thread
            int sendMessageStatus = SEND_MESSAGE_ERROR;

            // get information about the network state using ConnectivityManager
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            //check if the device has a valid internet connection
            if (networkInfo != null && networkInfo.isConnected()) {//..if it has, do login in CA Server

                Log.d("LoginActivity", "CA: Network Connection Found");

                //Queries user state using http REST request for login validation.
                HttpURLConnection conn = null;
                InputStream inputStream = null;
                BufferedReader reader = null;
                StringBuffer stringBuffer = null;

                try {

                    //message sent as parameter for the async class
                    String msg = (String) params[0];

                    //format request URL
                    //TODO: include the userId (name?) as a parameter. Uses the singleton class to obtain logged user.

                    //String urlString = "http://192.168.0.8:8080/AsapServer/sendMessage?msg=" + URLEncoder.encode(msg, "UTF-8");
                    String urlString = "http://54.94.205.241:8080/AsapServer/sendMessage?msg=" + URLEncoder.encode(msg, "UTF-8");

                    //URL encoded text
                    URL url = new URL(urlString);
                    //open connection...
                    conn = (HttpURLConnection) url.openConnection();
                    //... prepare request parameters
                    conn.setReadTimeout(50000);// milliseconds
                    conn.setConnectTimeout(50000);// milliseconds
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setRequestMethod("GET");

                    //starts the http request
                    Log.d("SendMessageActivity", "CA: trying to connect using created HttpURLConnection");
                    int responseCode = conn.getResponseCode();
                    Log.d("SendMessageActivity", "CA Says: The response code is: " + responseCode);
                    //...read input stream
                    Log.d("SendMessageActivity", "CA: trying to get input stream using conn.getInputStream");
                    inputStream = conn.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    stringBuffer = new StringBuffer();
                    // Convert the InputStream into a String
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuffer.append(line);
                    }
                    Log.d("SendMessageActivity", "CA: read from http connection: " + stringBuffer.toString());
                    sendMessageStatus = SEND_MESSAGE_OK;

                    // Makes sure that the InputStream is closed after the app is
                    // finished using it.
                } catch (Exception e) {
                    Log.d("SendMessageActivity", e.getMessage());
                    sendMessageStatus = SEND_MESSAGE_ERROR;
                } finally {
                    try {
                        if (conn != null) {
                            conn.disconnect();
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (java.io.IOException e) {
                        Log.d("SendMessageActivity", e.getMessage());
                    }
                }
            } else {
                //Device not connected
                Log.d("SendMessageActivity", "CA: Network Connection Not Found");
                sendMessageStatus = NOT_CONNECTED;
            }

            return new Integer(sendMessageStatus);
        }

        /**
         * onPostExecute
         * <p/>
         * Executes in the original thread and receives the result of the background execution.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(Integer result) {
            Context context = getApplicationContext();

            if(result == SEND_MESSAGE_ERROR) {
                String text = res.getString(R.string.send_message_error);
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else if (result == NOT_CONNECTED) {
                String text = res.getString(R.string.device_not_connect);
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else { //...otherwise just shows message informing that the user is not valid
                String text = res.getString(R.string.send_message_ok);
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
}
