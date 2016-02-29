package br.com.ca.asap.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import br.com.ca.asap.vo.UserVo;
import br.com.ca.shareview.R;


/**
 * LoginActivity
 *
 * TODO: needs refactoring. this is a demo version.
 */
public class LoginActivity extends AppCompatActivity {

   /**
     * onCreate
     *
     * creates activity, sets the layout and associates a listener for the Floating Action Button.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FloatingActionButton fabLogin = (FloatingActionButton) findViewById(R.id.fabLogin);
        fabLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = (String) (((TextView) findViewById(R.id.nameEditText)).getText()).toString();
                if (name.equals("")){
                    Snackbar.make(view, R.string.identify_yourself, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    doLogin(((TextView) findViewById(R.id.nameEditText)).getText(), ((TextView) findViewById(R.id.pwdEditText)).getText());
                }

            }
        });
    }

    /**
     * onCreateOptionMenu
     *
     * inflate menu_login menu.
     * actually does not inflates the menu because is the login page and should not give menu options for the user.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    /**
     * onOptionsItemSelected
     *
     * handle action bar item click.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * onClickLogin
     *
     * Called when the user clicks the Login button.
     * This event is mapped in the layout xml file associated with this class.
     *
     * @param view
     */
    public void onClickLogin(View view) {
        doLogin(((TextView) findViewById(R.id.nameEditText)).getText(), ((TextView) findViewById(R.id.pwdEditText)).getText());
    }

    /**
     * doLogin
     *
     * Execute Async Class DoAsyncLogin
     *
     * @param name
     * @param pwd
     */
    // Do login
    private void doLogin(CharSequence name, CharSequence pwd){
        new DoAsyncLogin().execute(String.valueOf(name), String.valueOf(pwd));
    }

    /**
     * DoAsyncLogin
     *
     * Uses AsyncTask to create a task away from the main UI thread, and check login.
     * If login is valid, call the enter activity
     */
    private class DoAsyncLogin extends AsyncTask<String, Void, Integer> {
        Resources res = getResources();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        //possible returned states of network query login
        public final int NOT_CONNECTED = 0;
        public final int VALID_USER    = 1;
        public final int INVALID_USER  = 3;

        /**
         * onPreExecute
         *
         * Prepare environment before initiate the background execution.
         * Can be used to change a view element (as a progress bar) indicating that is running a background task.
         */
        @Override
        protected void onPreExecute(){
        }

        /**
         * doInBackgroud
         *
         * AsyncExecution. Executes in its own thread in background.
         *
         * If it is not demo user, query a REST service to check the state of the user. Returns true if it is a valid user.
         *
         * @param params
         * @return
         */
        @Override
        protected Integer doInBackground(String... params) {

            // Only display the first 500 characters of the retrieved web page content.
            int len = 500;

            //initialize user validation state
            int userState = INVALID_USER;

            //
            // TODO: Needs refactoring. Demo code to accept a demo user.
            //
            if (params[0].equals("demo")) {
                //indicates that user is valid
                userState = VALID_USER;

                //return true for validated login
                return userState;

            } else {

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
                        Gson gson = new Gson(); // example: String string = gson.toJson(userVo)

                        //userVo user identification and validation status
                        UserVo userVo = new UserVo(params[0], params[1], false);

                        //format request URL
                        //URL url = new URL("http://192.168.0.8:8080/AsapServer/login");
                        // String urlString = "http://54.94.205.241:8080/AsapServer/sendMessage?msg=" + URLEncoder.encode("LOGIN MESSAGE","UTF-8");
                        String urlString = "http://192.168.0.8:8080/AsapServer/signin?name=" + URLEncoder.encode(userVo.getName(),"UTF-8") + "&"+ "password=" + URLEncoder.encode(userVo.getPassword(),"UTF-8");

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
                        //conn.setDoInput(true);
                        //starts the query
                        Log.d("LoginActivity", "CA: trying to connect using created HttpURLConnection");
                        int responseCode = conn.getResponseCode();
                        Log.d("LoginActivity", "CA Says: The response code is: " + responseCode);
                        //...read input stream
                        Log.d("LoginActivity", "CA: trying to get input stream using conn.getInputStream");
                        inputStream = conn.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(inputStream));
                        stringBuffer = new StringBuffer();
                        // Convert the InputStream into a String
                        String line = "";
                        while ((line = reader.readLine()) != null){
                            stringBuffer.append(line);
                        }
                        Log.d("LoginActivity", "CA: read from http connection: " + stringBuffer.toString());

                        //TODO: uses rest string return do create an UserVo
                        //TODO: creates a singleton class that has tha userVo as a property and a static method to get it. Uses this class to obtain logged user to send and get messages.

                        //... and indicates that user is valid
                        userState = VALID_USER;

                        // Makes sure that the InputStream is closed after the app is
                        // finished using it.
                    } catch (Exception e) {
                        Log.d("DoAsyncLogin", e.getMessage());
                        userState = INVALID_USER;

                    } finally {
                        try {
                            if (conn != null){
                                conn.disconnect();
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        } catch (java.io.IOException e) {
                            Log.d("DoAsyncLogin", e.getMessage());
                        }
                    }
                } else {
                    //Device not connected
                    Log.d("LoginActivity", "CA: Network Connection Not Found");
                    userState = NOT_CONNECTED;
                }

                return new Integer(userState);
            }
        }

        /**
         * onPostExecute
         *
         * Executes in the original thread and receives the result of the background execution.
         *
         * Displays the results of the AsyncTask.
         *
         * @param result
         */
        //
        @Override
        protected void onPostExecute(Integer result) {
            Context context = getApplicationContext();

            //check if the result, sent as a Boolean by doInBackGround, is true
            //...and call initiatives activity
            if(result == VALID_USER) {
                Intent intent = new Intent(context, SynchronizeActivity.class);
                startActivity(intent);
            } else if (result == NOT_CONNECTED) {
                String text = res.getString(R.string.device_not_connect);
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else { //...otherwise just shows message informing that the user is not valid
                    String text = res.getString(R.string.wrongNameOrPwd);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
            }
        }

        /**
         * readMessage
         *
         * Test method for reading json message
         *
         */
        private void readMessages() {
            //Queries user state using http REST request for login validation.
            HttpURLConnection conn = null;
            InputStream inputStream = null;
            BufferedReader reader = null;
            StringBuffer stringBuffer = null;
            try {
                //format request URL
                //URL url = new URL("http://192.168.0.8:8080/AsapServer/login");
                //String urlGetMsgslString = "http://192.168.0.8:8080/AsapServer/getMessages";
                String urlGetMsgslString = "http://54.94.205.241:8080/AsapServer/getMessages";

                //URL encoded text
                URL url = new URL(urlGetMsgslString);
                //open connection...

                conn = (HttpURLConnection) url.openConnection();
                //... prepare request parameters
                conn.setReadTimeout(50000);// milliseconds
                conn.setConnectTimeout(50000);// milliseconds
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestMethod("GET");
                //starts the query
                Log.d("LoginActivity", "CA: trying to connect using created HttpURLConnection");
                int responseCode = conn.getResponseCode();
                Log.d("LoginActivity", "CA Says: The response code is: " + responseCode);
                //...read input stream
                Log.d("LoginActivity", "CA: trying to get input stream using conn.getInputStream");
                inputStream = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                stringBuffer = new StringBuffer();
                // Convert the InputStream into a String
                String line = "";
                while ((line = reader.readLine()) != null){
                    stringBuffer.append(line);
                }
                Log.d("LoginActivity", "CA: read from http connection: " + stringBuffer.toString());

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } catch (Exception e) {
                Log.d("DoAsyncLogin", e.getMessage());
            } finally {
                try {
                    if (conn != null){
                        conn.disconnect();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (java.io.IOException e) {
                    Log.d("DoAsyncLogin", e.getMessage());
                }
            }
        }
    }
}
