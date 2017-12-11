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
import android.widget.ListView;
import android.widget.ScrollView;

//import br.com.ca.yabee.R;
import java.util.ArrayList;
import java.util.List;

import br.com.ca.blueocean.adapter.InitiativeUsersAdapter;
import br.com.ca.blueocean.hiveservices.HiveGetInitiativeUsers;
import br.com.ca.blueocean.hiveservices.HiveUnexpectedReturnException;
import br.com.ca.blueocean.network.DeviceNotConnectedException;
import br.com.ca.blueocean.vo.UserVo;
import br.com.ca.shareview.R;

public class ShowInitiativeUsersActivity extends AppCompatActivity {

    //identify expected parameters from previous activity
    public final static String EXTRA_INITIATIVE_ID = "INITIATIVE_ID"; //expected value to the activity initialization
    public final static String EXTRA_INITIATIVE_TITLE = "INITIATIVE_TITLE"; //expected value to the activity initialization

    //to read initialization parameters passed by previous actvity
    String initiativeTitle = null;
    String initiativeId = null;

    //ListView Adapter
    InitiativeUsersAdapter initiativeUsersAdapter = null;
    ArrayList<UserVo> userVoArrayList = null;
    //Activity ListView
    ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_initiative_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //
        //Get parameters from previous activity
        //

        //read extras
        Intent myIntent = getIntent(); // gets the previously created intent
        Bundle extras = myIntent.getExtras();
        initiativeId = extras.getString(EXTRA_INITIATIVE_ID);
        initiativeTitle = extras.getString(EXTRA_INITIATIVE_TITLE);
        //set activity title
        setTitle(initiativeTitle);

        //FloatingButton click listener
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                //call AddInitiativeUserActivity for user inclusion in the initiative
                //
                Intent intent;
                Bundle extras;

                //create intent for calling AddInitiativeUserActiviy
                intent = new Intent(ShowInitiativeUsersActivity.this, AddInitiativeUserActivity.class);
                //put parameters
                extras = new Bundle();
                extras.putString(AddInitiativeUserActivity.EXTRA_INITIATIVE_ID, initiativeId);
                extras.putString(AddInitiativeUserActivity.EXTRA_INITIATIVE_TITLE, initiativeTitle);
                intent.putExtras(extras);
                //start activity for result
                startActivityForResult(intent, AddInitiativeUserActivity.ADD_INITIATIVE_USER_INTENT_CALL);

            }
        });

        //
        //Prepare initiative users list view using AsyncTask for hive api call
        //Get users associated with this initiative.read initiative users using hive service executed
        //
        new AsyncGetInitiativeUsers().execute(initiativeId);

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
     * onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AddInitiativeUserActivity.ADD_INITIATIVE_USER_INTENT_CALL) {

            if(resultCode == RESULT_OK){
                //Successful execution implies in initiative created in the cloud server and actualized in the local database
                //Refresh list view with the actualized local database
                //refreshInitiativesListView();;
            }

            if (resultCode == RESULT_CANCELED) {
                //If there's no result
            }
        }
    }

    /**
     * AsyncGetInitiativeUsers
     *
     * <p/>
     * Uses AsyncTask to create a task away from the main UI thread, and send message to rest server.
     *
     */
    private class AsyncGetInitiativeUsers extends AsyncTask<String, Void, ArrayList<UserVo>> {
        Resources res = getResources();
        Context context = getApplicationContext();

        final ProgressDialog progressDialog = new ProgressDialog(ShowInitiativeUsersActivity.this,
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
            String initiativeId = params[0];

            //return value
            ArrayList<UserVo> userVoList = null;

            //prepare hive service
            Context context = getApplicationContext();
            HiveGetInitiativeUsers hiveGetInitiativeUsers = new HiveGetInitiativeUsers(context);

            try {
                userVoList = hiveGetInitiativeUsers.getInitiativeUsers(initiativeId);

            } /* catch (DeviceNotConnectedException e){
                result = null;

            } catch(HiveUnexpectedReturnException e){
                result = null;

            } */ catch(Exception e){
                userVoList = null;
            }

            //return result of background thread execution
            return userVoList;
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
                userVoArrayList = result;

                // 1. pass context and data to the custom adapter
                //adapter = new InitiativeUsersAdapter(this, (ArrayList<UserVo>) getInitiativesList());
                initiativeUsersAdapter = new InitiativeUsersAdapter(ShowInitiativeUsersActivity.this, userVoArrayList);
                // 2. Get ListView from activity_main.xml
                listView = (ListView) findViewById(R.id.initiativeUsersList);
                // 3. setListAdapter
                listView.setAdapter(initiativeUsersAdapter);

            } else  {
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.initiative_users_coordinatorlayout);
                Snackbar.make(coordinatorLayout, res.getString(R.string.unexpected_error), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        }
    }
}
