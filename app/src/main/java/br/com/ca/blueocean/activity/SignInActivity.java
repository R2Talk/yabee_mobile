package br.com.ca.blueocean.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.ca.blueocean.hiveservices.HiveSignIn;
import br.com.ca.blueocean.users.UserManager;
import br.com.ca.blueocean.vo.UserVo;
import br.com.ca.shareview.R;


/**
 * SignInActivity
 *
 * Use hive service to sign in, and save the logged user using user package classes
 *
 * @author Rodrigo Carvalho
 */
public class SignInActivity extends AppCompatActivity {

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

        //check if the user is already logged in
        UserManager signManager = new UserManager(getApplicationContext());

        if(signManager.knownUser()){

            signManager.initializeSessionFromPreferences();

            //call show initiatives for user already logged
            Intent intent = new Intent(getApplicationContext(), InitiativesActivity.class);
            startActivity(intent);


        } else {

            setContentView(R.layout.activity_signin);
            final Button button = (Button) findViewById(R.id.btn_login);

            //click sign in button
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String email = (String) (((TextView) findViewById(R.id.emailEditText)).getText()).toString();
                    if (email.equals("")) {
                        Snackbar.make(view, R.string.identify_yourself, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    } else {
                        doSignIn(((TextView) findViewById(R.id.emailEditText)).getText(), ((TextView) findViewById(R.id.pwdEditText)).getText());
                    }
                }
            });

            //click sign up link
            TextView signupLinkView = (TextView) findViewById(R.id.link_signup);
            signupLinkView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //Intent for SignIn activity
                    Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                    //Start Intent
                    startActivity(intent);
                }
            });
        }

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

        doSignIn(((TextView) findViewById(R.id.emailEditText)).getText(), ((TextView) findViewById(R.id.pwdEditText)).getText());
    }

    /**
     * doSignIn
     *
     * Execute Async Class DoAsyncSignIn
     *
     * @param name
     * @param pwd
     */
    // Do login
    private void doSignIn(CharSequence name, CharSequence pwd){
        new DoAsyncSignIn().execute(String.valueOf(name), String.valueOf(pwd));
    }

    /**
     * DoAsyncSignIn
     *
     * Uses AsyncTask to create a task away from the main UI thread, and check login.
     * If login is valid, call the enter activity
     */
    private class DoAsyncSignIn extends AsyncTask<String, Void, Integer> {
        Resources res = getResources();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        final ProgressDialog progressDialog = new ProgressDialog(SignInActivity.this,
                R.style.AppTheme_Dark_Dialog);

        // possible returns values from sign in service
        //TODO: define values to mark network errors and permit to show the appropriate message in this UI Thread
        public final int VALID_USER = 1;
        public final int INVALID_USER = 3;


        /**
         * onPreExecute
         *
         * Prepare environment before initiate the background execution.
         * Can be used to change a view element (as a progress bar) indicating that is running a background task.
         *
         */
        @Override
        protected void onPreExecute() {
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(res.getString(R.string.authenticating));
            progressDialog.show();
        }

        /**
         * doInBackgroud
         * <p/>
         * AsyncExecution. Executes in its own thread in background.
         * <p/>
         *
         * If it is not Guest user, query a REST service to check the state of the user. Returns true if it is a valid user.
         *
         * @param params
         * @return
         */
        @Override
        protected Integer doInBackground(String... params) {

            //initialize user validation state
            int userState = INVALID_USER;

            //retrieved user
            UserVo userVo = null;

            //
            // TODO: Needs refactoring. Demo option to accept guest user.
            //
            if (params[0].equals("guest")) {

                //get the user data and status
                userVo = new UserVo(2,"guest","guest@yabee.com", "guest", true);

                //indicates user status to be used in the UI thread
                userState = VALID_USER;

                //save logged user
                UserManager signManager = new UserManager(getApplicationContext());
                signManager.signIn(userVo);

                //return true for validated login
                return userState;

            } else {

                //prepare hive service
                Context context = getApplicationContext();
                HiveSignIn hiveSignIn = new HiveSignIn(context);

                //prepare hive service parameters
                String email = params[0];
                String pwd = params[1];

                //call hive service
                userVo = hiveSignIn.signIn(email, pwd); //TODO: Create and check exceptions for every hive service

                if (userVo.getValidated()==true) { //check user status
                    //indicates user status to be used in the UI thread - onPostExecute -
                    userState = VALID_USER;
                    //save logged user
                    UserManager signManager = new UserManager(getApplicationContext());
                    signManager.signIn(userVo);
                } else {
                    //indicates user status to be used in the UI thread - onPostExecute -
                    userState = INVALID_USER;
                }

               //return status of user signin
               return userState;
            }
        }

        /**
         * onPostExecute
         * <p/>
         * Executes in the original thread and receives the result of the background execution.
         * <p/>
         * Displays the results of the AsyncTask.
         *
         * @param result
         */
        //
        @Override
        protected void onPostExecute(Integer result) {
            Context context = getApplicationContext();

            progressDialog.dismiss();

            //check if the result, sent as a Boolean by doInBackGround, is true
            //...and call initiatives activity
            if (result == VALID_USER) {
                //Intent intent = new Intent(context, SynchronizeInitiativesActivity.class);
                Intent intent = new Intent(SignInActivity.this, SynchronizeInitiativesActivity.class);
                startActivity(intent);
            } else { //...otherwise just shows message informing that the user is not valid
                String text = res.getString(R.string.wrongNameOrPwd);
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.signinCoordinatorLayout);
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, text, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }
}
