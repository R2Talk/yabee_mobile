package br.com.ca.blueocean.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.ca.blueocean.hiveservices.HiveCreateInitiative;
import br.com.ca.blueocean.users.SignManager;
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
                EditText descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);


                if ((titleEditText.getText().toString().equals("")) || (descriptionEditText.getText().toString().equals(""))) {
                    Resources res = getResources();
                    Snackbar.make(view, res.getString(R.string.all_fields_needed_for_initiative), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {

                    //create initiative
                    String title = (String) (((EditText) findViewById(R.id.titleEditText)).getText()).toString();
                    String description = (String) (((EditText) findViewById(R.id.descriptionEditText)).getText()).toString();
                    SignManager signManager = new SignManager(getApplicationContext());
                    UserVo userVo = signManager.getCurrentUser();
                    String userId = userVo.getUserId().toString();

                    new AsyncCreateInitiative().execute(title, description, userId);

                    //TODO: check that on post execute insert initiative into the local database

                    setResult(Activity.RESULT_OK); // return to InitiativesActivity

                    //Intent returnIntent = new Intent();
                    //returnIntent.putExtra("result",result);
                    //setResult(Activity.RESULT_OK,returnIntent);

                    finish();
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
     * AsyncCreateInitiative
     *
     * <p/>
     * Uses AsyncTask to create a task away from the main UI thread, and send message to rest server.
     *
     */
    private class AsyncCreateInitiative extends AsyncTask<String, Void, InitiativeVo> {
        Resources res = getResources();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        //possible returned states of network query login
        public final int NOT_CONNECTED = 0;

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
        protected InitiativeVo doInBackground(String... params) {

            // variable thats maintains return status for original thread
            //int sendMessageStatus = CREATE_INITIATIVE_OK;

            //prepare hive service parameters
            String title = params[0];
            String description = params[1];
            String userId = params[1];

            InitiativeVo initiativeVo = null;

            //prepare hive service
            Context context = getApplicationContext();
            HiveCreateInitiative hiveCreateInitiative = new HiveCreateInitiative(context);

            //call give service to send message
            initiativeVo = hiveCreateInitiative.createInitiative(title, description, userId);

            //return result of background thread execution
            return initiativeVo;
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
        protected void onPostExecute(InitiativeVo result) {
            Context context = getApplicationContext();

            if(result == null) { //TODO: consider returning a new vo class with InitiativeVo AND an int signalizing errors types
                String text = res.getString(R.string.create_initiative_error);
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            } else { //TODO: actualize local database

                //insert InitiativeVo in the local database


            }
        }
    }
}
