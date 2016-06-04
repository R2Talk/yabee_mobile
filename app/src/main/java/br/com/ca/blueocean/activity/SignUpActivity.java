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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.ca.blueocean.hiveservices.HiveSignUp;
import br.com.ca.blueocean.users.UserManager;
import br.com.ca.blueocean.vo.UserVo;
import br.com.ca.shareview.R;

/**
 * SignUpActivity
 *
 * This activity register a user in hive server
 *
 * @author Rodrigo Carvalho
 */
public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * onClick_createAccountButton
     *
     * Call hive service for Account Creation and session initialization for new user
     *
     * @param view
     */
    public void onClick_createAccountButton (View view) {

        //call Hive Service for Account Creation and check for result
        //        on success: save user in preferences, save singleton user instance and call welcome activity
        //        on user already exist, create SnackBar message informing

        //1. Get values from views
        String name = (String) (((TextView) findViewById(R.id.nameEditText)).getText()).toString();
        String email = (String) (((TextView) findViewById(R.id.emailEditText)).getText()).toString();
        String pwd = (String) (((TextView) findViewById(R.id.pwdEditText)).getText()).toString();

        //2. Check data conditions (not empty, password with minimum number of characters, email well formed)
        if (name.equals("") || email.equals("") || pwd.equals("")) {

            Snackbar.make(view, R.string.all_fields_needed_for_account, Snackbar.LENGTH_LONG).setAction("Action", null).show();

            //TODO: include others conditions for fields validation

        } else {

            new DoAsyncSignUp().execute(name, email, pwd);
        }
    }

    /**
     * DoAsyncSignUp
     *
     * Uses AsyncTask to create a task away from the main UI thread, and call sign up service.
     *
     * If sign up has successful execution , call welcome activity.
     *
     */
    private class DoAsyncSignUp extends AsyncTask<String, Void, Integer> {
        Resources res = getResources();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
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
            progressDialog.setMessage(res.getString(R.string.synchronizing));
            progressDialog.show();
        }

        /**
         * doInBackgroud
         * <p/>
         * AsyncExecution. Executes in its own thread in background.
         * <p/>
         *
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


            //prepare hive service
            Context context = getApplicationContext();
            HiveSignUp hiveSignUp = new HiveSignUp(context);

            //prepare hive service parameters
            String name = params[0];
            String email = params[1];
            String pwd = params[2];

                //call hive service
                userVo = hiveSignUp.signUp(name, email, pwd); //TODO: Create and check exceptions for every hive service


                if ((userVo!=null) && (userVo.getValidated()==true)) { //check user status
                    //save logged user
                    UserManager signManager = new UserManager(getApplicationContext());
                    signManager.signIn(userVo);

                    //indicates user status to be used in the UI thread - onPostExecute -
                    userState = VALID_USER;

                } else {
                    //indicates user status to be used in the UI thread - onPostExecute -
                    userState = INVALID_USER;
                }

                //return status of user signin
                return userState;
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
                Intent intent = new Intent(SignUpActivity.this, WelcomeScreenActivity.class);
                startActivity(intent);

            } else { //...otherwise shows message informing that the email is already registered

                String text = res.getString(R.string.user_already_exists);
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.signupCoordinatorLayout);
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, text, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }
}
