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
import android.widget.EditText;

import br.com.ca.blueocean.database.InitiativeDAO;
import br.com.ca.blueocean.hiveservices.HiveCreateInitiative;
import br.com.ca.blueocean.hiveservices.HiveUnexpectedReturnException;
import br.com.ca.blueocean.network.DeviceNotConnectedException;
import br.com.ca.blueocean.users.UserManager;
import br.com.ca.blueocean.vo.InitiativeVo;
import br.com.ca.blueocean.vo.UserVo;
import br.com.ca.shareview.R;

public class CreateInitiativeActivity extends AppCompatActivity {

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_initiative);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //left icon for return to previous activity

        //action bar title
        setTitle(getString(R.string.create_initiative));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_create_initiative);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check for empty message
                EditText titleEditText = (EditText) findViewById(R.id.titleEditText);

                if (titleEditText.getText().toString().trim().equals("")) {
                    Resources res = getResources();
                    Snackbar.make(view, res.getString(R.string.all_fields_needed_for_initiative), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {

                    //create initiative
                    String title = (((EditText) findViewById(R.id.titleEditText)).getText()).toString();
                    UserManager signManager = new UserManager(getApplicationContext());
                    UserVo userVo = signManager.getCurrentUser();
                    String userId = userVo.getUserId().toString();

                    //TODO: description not used and passed as empty string, need hive server review
                    new AsyncCreateInitiative().execute(title, "", userId);

                }
            }
        });
    }

    /**
     * onOptionsItemSelected
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Handle presses on the action bar items
        //
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
     * Inner Class that represents the return of background service execution
     *
     * TODO: candidate for refactoring into a generic helper class for use in all async task service execution.
     *
     */
    private class CreateInitiativeAsyncResult{

        public static final int SUCCESS = 0;
        public static final int ERROR = 1;
        public static final int DEVICE_NOT_CONNECTED = 2;

        int resultCode;
        InitiativeVo initiativeVo = null;

        CreateInitiativeAsyncResult(int resultCode, InitiativeVo initiativeVo){
            this.resultCode = resultCode;
            this.initiativeVo = initiativeVo;
        }

        public int getResultCode() {
            return resultCode;
        }
        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }
        public InitiativeVo getInitiativeVo() {
            return initiativeVo;
        }
        public void setInitiativeVo(InitiativeVo initiativeVo) {
            this.initiativeVo = initiativeVo;
        }
    }

    /**
     * AsyncCreateInitiative
     *
     * <p/>
     * Uses AsyncTask to create a task away from the main UI thread, and send message to rest server.
     *
     */
    private class AsyncCreateInitiative extends AsyncTask<String, Void, CreateInitiativeAsyncResult> {
        Resources res = getResources();
        Context context = getApplicationContext();

        final ProgressDialog progressDialog = new ProgressDialog(CreateInitiativeActivity.this,
                R.style.AppTheme_Dark_Dialog);

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
        protected CreateInitiativeAsyncResult doInBackground(String... params) {

            //prepare hive service parameters
            String title = params[0];
            String description = params[1];
            String userId = params[2];

            InitiativeVo initiativeVo = null;

            //prepare hive service
            Context context = getApplicationContext();
            HiveCreateInitiative hiveCreateInitiative = new HiveCreateInitiative(context);

            //call hive service = null;
            CreateInitiativeAsyncResult result = null;
            try {
                initiativeVo = hiveCreateInitiative.createInitiative(title, description, userId);
                if (initiativeVo != null) {

                    //actualize local database
                    InitiativeDAO initiativeDAO = new InitiativeDAO(context);
                    initiativeDAO.insertInitiative(initiativeVo); //TODO: catch exception for inert exception - repeated value

                    //prepare result
                    result = new CreateInitiativeAsyncResult(CreateInitiativeAsyncResult.SUCCESS, initiativeVo);

                }

            } catch (DeviceNotConnectedException e){
                result = new CreateInitiativeAsyncResult(CreateInitiativeAsyncResult.DEVICE_NOT_CONNECTED, null);

            } catch(HiveUnexpectedReturnException e){
                result = new CreateInitiativeAsyncResult(CreateInitiativeAsyncResult.ERROR, null);

            } catch(Exception e){
                result = new CreateInitiativeAsyncResult(CreateInitiativeAsyncResult.ERROR, null);
                //TODO: Unexpected error. Should log to enable analysis of the error
            }

            //return result of background thread execution
            return result;
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
        protected void onPostExecute(CreateInitiativeAsyncResult result) {
            Context context = getApplicationContext();
            progressDialog.dismiss();

            if(result.getResultCode() == CreateInitiativeAsyncResult.SUCCESS) {

                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);

                finish();

            } else if (result.getResultCode() == CreateInitiativeAsyncResult.DEVICE_NOT_CONNECTED) {

                Resources res = getResources();
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.createInitiativeCoordinatorLayout);
                Snackbar.make(coordinatorLayout, res.getString(R.string.device_not_connect), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            } else if (result.getResultCode() == CreateInitiativeAsyncResult.ERROR) {

                Resources res = getResources();
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.createInitiativeCoordinatorLayout);
                Snackbar.make(coordinatorLayout, res.getString(R.string.unexpected_error), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }
}
