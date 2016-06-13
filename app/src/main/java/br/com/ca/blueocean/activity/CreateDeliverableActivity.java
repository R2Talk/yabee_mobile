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
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import br.com.ca.blueocean.database.DeliverableDAO;
import br.com.ca.blueocean.hiveservices.HiveCreateDeliverable;
import br.com.ca.blueocean.hiveservices.HiveUnexpectedReturnException;
import br.com.ca.blueocean.network.DeviceNotConnectedException;
import br.com.ca.blueocean.util.DatePickerFragment;
import br.com.ca.blueocean.vo.DeliverableVo;
import br.com.ca.shareview.R;

public class CreateDeliverableActivity extends AppCompatActivity {

    public final static String EXTRA_INITIATIVE_ID = "INITIATIVE_ID"; //expected value to the activity initialization
    public final static String EXTRA_USER_ID = "USER_ID"; //expected value to the activity initialization

    // create deliverable data
    private String isPriority = "NO";
    private String status = "OPEN";
    private String initiativeId = null;
    private String userId = null;
    private String title = null;
    private String description = null;
    private String date = null;

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deliverable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //
        //Get parameters from previous activity
        //
        Intent myIntent = getIntent(); // gets the previously created intent
        Bundle extras = myIntent.getExtras();

        this.initiativeId = extras.getString(EXTRA_INITIATIVE_ID);
        this.userId = extras.getString(EXTRA_USER_ID);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_create_deliverable);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check creation data
                EditText titleEditText = (EditText) findViewById(R.id.titleEditText);
                EditText descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);

                if ((titleEditText.getText().toString().trim().equals("")) || (date == null)) {
                    Resources res = getResources();
                    Snackbar.make(view, res.getString(R.string.fields_needed_for_deliverable), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {

                    //create deliverable
                    title = (((EditText) findViewById(R.id.titleEditText)).getText()).toString();
                    description = (((EditText) findViewById(R.id.descriptionEditText)).getText()).toString();

                    new AsyncCreateDeliverable().execute(title, description, date, status, userId, initiativeId, isPriority);
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
     * onCalendarImageClick
     *
     * @param view
     */
    public void onCalendarImageClick(View view){

        //BE WARE: Overwriting "onDataSet" method to get the values chosen in the date picker dialog.
        DialogFragment newFragment = new DatePickerFragment(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                ((TextView) findViewById(R.id.deliverableDateTextView)).setText("" + day + "/" + month + "/" + year);
                date = year + "-" + month + "-" + day;
            }
        };

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Inner Class that represents the return of background service execution
     *
     * TODO: candidate for refactoring into a generic helper class for use in all async task service execution.
     *
     */
    private class CreateDeliverableAsyncResult{

        public static final int SUCCESS = 0;
        public static final int ERROR = 1;
        public static final int DEVICE_NOT_CONNECTED = 2;

        int resultCode;
        DeliverableVo deliverableVo = null;

        CreateDeliverableAsyncResult(int resultCode, DeliverableVo deliverableVo){
            this.resultCode = resultCode;
            this.deliverableVo = deliverableVo;
        }

        public int getResultCode() {
            return resultCode;
        }
        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }
        public DeliverableVo getDeliverableVo() {
            return deliverableVo;
        }
        public void setDeliverableVo(DeliverableVo deliverableVo) { this.deliverableVo = deliverableVo;}
    }

    /**
     * AsyncCreateDeliverable
     *
     * <p/>
     * Uses AsyncTask to create a task away from the main UI thread, and send message to rest server.
     *
     */
    private class AsyncCreateDeliverable extends AsyncTask<String, Void, CreateDeliverableAsyncResult> {
        Resources res = getResources();
        Context context = getApplicationContext();

        final ProgressDialog progressDialog = new ProgressDialog(CreateDeliverableActivity.this,
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
        protected CreateDeliverableAsyncResult doInBackground(String... params) {

            //prepare hive service parameters
            String title = params[0];
            String description = params[1];
            String date = params[2];
            String status = params[3];
            String userId = params[4];
            String initiativeId = params[5];
            String isPriority = params[6];

            CreateDeliverableAsyncResult result = null;
            DeliverableVo deliverableVo = null;

            //prepare hive service
            Context context = getApplicationContext();
            HiveCreateDeliverable hiveCreateDeliverable = new HiveCreateDeliverable(context);

            try {

                deliverableVo = hiveCreateDeliverable.createDeliverable(title, description, date, status, userId, initiativeId, isPriority);

                if (deliverableVo != null) {

                    //actualize local database with newly created deliverable
                    DeliverableDAO deliverableDAO = new DeliverableDAO(context);
                    deliverableDAO.insertDeliverableVo(deliverableVo); //TODO: catch exception for local inert error

                    //prepare result for previous activity
                    result = new CreateDeliverableAsyncResult(CreateDeliverableAsyncResult.SUCCESS, deliverableVo);
                }

            } catch (DeviceNotConnectedException e){
                result = new CreateDeliverableAsyncResult(CreateDeliverableAsyncResult.DEVICE_NOT_CONNECTED, null);

            } catch(HiveUnexpectedReturnException e){
                result = new CreateDeliverableAsyncResult(CreateDeliverableAsyncResult.ERROR, null);

            } catch(Exception e){
                result = new CreateDeliverableAsyncResult(CreateDeliverableAsyncResult.ERROR, null);
                //TODO: Its an unexpected error. Should log to enable analysis of the error
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
        protected void onPostExecute(CreateDeliverableAsyncResult result) {
            Context context = getApplicationContext();
            progressDialog.dismiss();

            if(result.getResultCode() == CreateDeliverableAsyncResult.SUCCESS) {

                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

            } else if (result.getResultCode() == CreateDeliverableAsyncResult.DEVICE_NOT_CONNECTED) {

                Resources res = getResources();
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.createDeliverableCoordinatorLayout);
                Snackbar.make(coordinatorLayout, res.getString(R.string.device_not_connect), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            } else if (result.getResultCode() == CreateDeliverableAsyncResult.ERROR) {

                Resources res = getResources();
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.createInitiativeCoordinatorLayout);
                Snackbar.make(coordinatorLayout, res.getString(R.string.unexpected_error), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }
}
