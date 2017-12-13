package br.com.ca.blueocean.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Iterator;

import br.com.ca.blueocean.adapter.InitiativeUsersAdapter;
import br.com.ca.blueocean.hiveservices.HiveAddInitiativeUser;
import br.com.ca.blueocean.hiveservices.HiveGetKnownUsersByUserId;
import br.com.ca.blueocean.users.CurrentUser;
import br.com.ca.blueocean.users.UserManager;
import br.com.ca.blueocean.vo.UserVo;
import br.com.ca.shareview.R;


public class AddInitiativeUserActivity extends AppCompatActivity {

    //identify expected parameters from previous activity
    public final static String EXTRA_INITIATIVE_ID = "INITIATIVE_ID"; //expected value to the activity initialization
    public final static String EXTRA_INITIATIVE_TITLE = "INITIATIVE_TITLE"; //expected value to the activity initialization

    //code for identifying return result from activity
    public final static int ADD_INITIATIVE_USER_INTENT_CALL = 0;

    //to read initialization parameters passed by previous actvity
    String initiativeTitle = null;
    String initiativeId = null;

    AutoCompleteTextView actv = null;
    String[] knownUsersEmails = null;
    ArrayAdapter<String> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_initiative_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //
        //Get parameters from previous activity
        //
        Intent previousIntent = getIntent(); // gets the previously created intent
        Bundle extras = previousIntent.getExtras();
        initiativeId = extras.getString(EXTRA_INITIATIVE_ID);
        initiativeTitle = extras.getString(EXTRA_INITIATIVE_TITLE);

        //action bar title
        setTitle(initiativeTitle);

        //
        //add person floating button
        //
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get typed email
                EditText userEmailEditText = (EditText) findViewById(R.id.autoCompleteTextView);

                if ((userEmailEditText.getText().toString().trim().equals(""))){ //TODO: check email sintax
                    Resources res = getResources();
                    Snackbar.make(view,res.getString(R.string.all_fields_needed_for_add_user_into_initiative), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //execute AsyncTask
                    new AsyncAddInitiativeUser().execute(userEmailEditText.getText().toString().trim(), initiativeId);
                }

            }
        });

        //
        //Get known user emails using AsyncTask with hive api call
        //...and prepares AutoCompleteTextView.
        //

        //current user
        UserManager signManager = new UserManager(getApplicationContext());
        UserVo userVo = signManager.getCurrentUser();
        String currentUserId = Integer.toString(userVo.getUserId());
        //execute AsyncTask
        new AsyncGetKnownUsersByUserId().execute(currentUserId);
    }

    /**
     * onOptionsItemSelected
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * AsyncGetKnownUsers
     *
     * <p/>
     * Uses AsyncTask to create a task away from the main UI thread, and send message to rest server.
     *
     */
    private class AsyncGetKnownUsersByUserId extends AsyncTask<String, Void, ArrayList<UserVo>> {
        Resources res = getResources();
        Context context = getApplicationContext();

        final ProgressDialog progressDialog = new ProgressDialog(AddInitiativeUserActivity.this,
                R.style.AppTheme_Dark_Dialog);

        /**
         * onPreExecute
         *
         * <p/>
         * Executes in the original UI thread before starting new thread for background execution.
         *
         */
        @Override
        protected void onPreExecute() {
            //show progress dialog
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(res.getString(R.string.synchronizing));
            progressDialog.show();
        }

        /**
         * doInBackgroud
         *
         * <p/>
         * AsyncExecution. Executes in its own thread in background.
         *
         * @param params
         * @return
         */
        @Override
        protected ArrayList<UserVo> doInBackground(String... params) {

            //prepare hive service parameters
            String userId = params[0];

            //return value
            ArrayList<UserVo> userVoArrayList = null;

            //prepare hive service
            Context context = getApplicationContext();
            HiveGetKnownUsersByUserId hiveGetKnownUsersByUserId = new HiveGetKnownUsersByUserId(context);

            try {
                userVoArrayList = hiveGetKnownUsersByUserId.getKnownUsersByUserId(userId);

            } /* catch (DeviceNotConnectedException e){
                result = null;

            } catch(HiveUnexpectedReturnException e){
                result = null;

            } */ catch(Exception e){
                userVoArrayList = null;
            }

            //return result of background thread execution
            return userVoArrayList;
        }

        /**
         * onPostExecute
         *
         * <p/>
         * Executes in the original thread and receives the result of the background execution.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(ArrayList<UserVo> result) {
            Context context = getApplicationContext();
            progressDialog.dismiss();

            if(result != null) {

                //get known users by user id
                String[] knownUsersEmails = new String[result.size()];
                for (int i = 0; i < result.size(); i++) {
                    knownUsersEmails[i] = result.get(i).getEmail();
                }

                //prepare AutoCompleteTextView
                actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
                adapter = new ArrayAdapter<String>(AddInitiativeUserActivity.this, android.R.layout.simple_dropdown_item_1line, knownUsersEmails);
                actv.setAdapter(adapter);

            } else  {
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.add_initiative_user_coordinatorlayout);
                Snackbar.make(coordinatorLayout, res.getString(R.string.unexpected_error), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        }
    }


    /**
     * AsyncAddInitiativeUser
     *
     * <p/>
     * Uses AsyncTask to create a task away from the main UI thread, and send message to rest server.
     *
     */
    private class AsyncAddInitiativeUser extends AsyncTask<String, Void, String> {
        Resources res = getResources();
        Context context = getApplicationContext();

        final ProgressDialog progressDialog = new ProgressDialog(AddInitiativeUserActivity.this,
                R.style.AppTheme_Dark_Dialog);

        /**
         * onPreExecute
         *
         * <p/>
         * Executes in the original UI thread before starting new thread for background execution.
         *
         */
        @Override
        protected void onPreExecute() {
            //show progress dialog
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(res.getString(R.string.synchronizing));
            progressDialog.show();
        }

        /**
         * doInBackgroud
         *
         * <p/>
         * AsyncExecution. Executes in its own thread in background.
         *
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(String... params) {

            //prepare hive service parameters
            String userEmail = params[0];
            String initiativeId = params[1];

            //return value
            String returnCode = null;

            //prepare hive service
            Context context = getApplicationContext();
            HiveAddInitiativeUser hiveAddInitiativeUser = new HiveAddInitiativeUser(context);

            try {
                hiveAddInitiativeUser.addInitiativeUser(userEmail, initiativeId);
                returnCode = "success";
            }  /* catch (DeviceNotConnectedException e){
                userVoList = null;

            } catch(HiveUnexpectedReturnException e){
                userVoList = null;

            }  */ catch(Exception e){
                returnCode = "failure";
            }

            //return result of background thread execution
            return returnCode;
        }

        /**
         * onPostExecute
         *
         * <p/>
         * Executes in the original thread and receives the result of the background execution.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            Context context = getApplicationContext();
            progressDialog.dismiss();

            /*
            if(result != "sucsess") {
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.initiative_users_coordinatorlayout);
                Snackbar.make(coordinatorLayout, res.getString(R.string.unexpected_error), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
            */

            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);

            finish();

        }
    }
}
