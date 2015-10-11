package br.com.ca.asap.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.ca.shareview.R;
import br.com.ca.asap.demo.DemoLogin;
import br.com.ca.asap.vo.LoginStatusVo;


/*
 * LoginActivity
 * TODO: need refactoring. demo version.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

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

    // Called when the user clicks the Login button
    public void onClickLogin(View view) {
        doLogin(((TextView) findViewById(R.id.nameEditText)).getText(), ((TextView) findViewById(R.id.pwdEditText)).getText());
    }

    // Do login
    private void doLogin(CharSequence name, CharSequence pwd){
        new DoAsyncLogin().execute(String.valueOf(name), String.valueOf(pwd));
    }

    // Uses AsyncTask to create a task away from the main UI thread, and check login.
    // If login is valid, call the enter activity
    private class DoAsyncLogin extends AsyncTask<String, Void, Boolean> {
        Resources res = getResources();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        @Override
        protected void onPreExecute(){
            //show progress bar view
            //ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
            //bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved web page content.
            int len = 500;

            Boolean validated = false;

            //
            // TODO: Need refactoring. Demo code.
            if (params[0].equals("demo")) {

                //demo login validation
                DemoLogin demoLogin = new DemoLogin(res.getString(R.string.demoVersion));
                demoLogin.doDemoLogin();

                //indicates that user is valid
                validated = true;

                //return true for validated login
                return validated;

            } else {

                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                //check if the device has a valid internet connection
                if (networkInfo != null && networkInfo.isConnected()) {//..if it has, do login in CA Server
                    Log.d("LoginActivity", "CA: Network Connection Found");

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
                        //Log.d("LoginActivity", "CA Says: The response is: " + response);

                        Log.d("LoginActivity", "CA: trying conn.getInputStream");

                        //do the http request
                        is = conn.getInputStream();

                        // Convert the InputStream into a string
                        String contentAsString = readIt(is, len);
                        Log.d("LoginActivity", "CA: reade from http connection: " + contentAsString);

                        //... and indicates that user is valid
                        validated = true;

                        //else, indicates de user is not valid

                        // Makes sure that the InputStream is closed after the app is
                        // finished using it.
                    } catch (Exception e) {
                        Log.d("DoAsyncLogin", e.getMessage());
                        validated = false;

                    } finally {
                        try {
                            if (is != null) {
                                is.close();
                            }
                        } catch (java.io.IOException e) {
                            Log.d("DoAsyncLogin", e.getMessage());
                        }
                    }

                } else {
                    //Device not connected
                    Log.d("LoginActivity", "CA: Network Connection Not Found");
                    String text = res.getString(R.string.device_not_connect);
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    validated = false;
                }

                return validated;
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Boolean result) {
            Context context = getApplicationContext();

            //check if the result, sent as a Boolean by doInBackGround, is true
            //...and call initiatives activity
            if(result.booleanValue() == true) {
                Intent intent = new Intent(context, SynchronizeActivity.class);
                startActivity(intent);
            } else { //...otherwise just shows message informing that the user is not valid
                String text = res.getString(R.string.wrongNameOrPwd);
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
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
