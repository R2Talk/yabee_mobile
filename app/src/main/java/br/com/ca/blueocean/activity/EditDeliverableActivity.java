package br.com.ca.blueocean.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import br.com.ca.blueocean.database.DeliverableDAO;
import br.com.ca.blueocean.hiveservices.HiveCreateDeliverable;
import br.com.ca.blueocean.hiveservices.HiveUnexpectedReturnException;
import br.com.ca.blueocean.network.DeviceNotConnectedException;
import br.com.ca.blueocean.util.DatePickerFragment;
import br.com.ca.blueocean.vo.DeliverableVo;
import br.com.ca.shareview.R;

public class EditDeliverableActivity extends AppCompatActivity  {

    public final static String EXTRA_DELIVERABLE_ID = "DELIVERABLE_ID"; //expected value to the activity initialization
    public final static String EXTRA_DELIVERABLE_TITLE = "DELIVERABLE_TITLE"; //expected value to the activity initialization
    public final static String EXTRA_DELIVERABLE_DESCRIPTION = "DELIVERABLE_DESCRIPTION"; //expected value to the activity initialization
    public final static String EXTRA_DELIVERABLE_DATE = "DELIVERABLE_DATE"; //expected value to the activity initialization

    // create deliverable data
    private String deliverableId = null;
    private String title = null;
    private String description = null;
    private String dueDate = null;

    //this DeleivrableVo
    DeliverableVo thisDeliverableVo = null;

    //date picker dialog
    DatePickerDialog datePickerDialog;

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deliverable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //
        //Get parameters from previous activity
        //
        Intent myIntent = getIntent(); // gets the previously created intent
        Bundle extras = myIntent.getExtras();

        //read parameters sent by the previous activity
        deliverableId = extras.getString(EXTRA_DELIVERABLE_ID);
        title = extras.getString(EXTRA_DELIVERABLE_TITLE);
        description = extras.getString(EXTRA_DELIVERABLE_DESCRIPTION);
        dueDate = extras.getString(EXTRA_DELIVERABLE_DATE);

        //
        //Creates a DeliverableVo with only the original updatable data (read from the previous activity)
        //
        thisDeliverableVo = new DeliverableVo(deliverableId, "", title, description, "", "",dueDate,
                "", "", "", "", "", "", "", "");

        //
        //Initialize edit text fields (title, description, date)
        //
        updateEditActivityDetailsView(thisDeliverableVo);

        //
        //Set event handler for update action
        //
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_edit_deliverable);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //read current edition data (updated fields)
                EditText titleUpdateEditText = (EditText) findViewById(R.id.titleUpdateEditText);
                EditText descriptionUpdateEditText = (EditText) findViewById(R.id.descriptionUpdateEditText);
                TextView dueDateUpdateTextView = (TextView) findViewById(R.id.deliverableDueDateUpdateTextView);

                thisDeliverableVo.setTitle(titleUpdateEditText.getText().toString());
                thisDeliverableVo.setDescription(descriptionUpdateEditText.getText().toString());
                thisDeliverableVo.setDuedate(dueDateUpdateTextView.getText().toString());

                if ((thisDeliverableVo.getTitle()).trim().equals("")|| (thisDeliverableVo.getDuedate()).trim().equals("")){
                    Resources res = getResources();
                    //TODO: change message text
                    Snackbar.make(view, res.getString(R.string.fields_needed_for_deliverable), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {

                    //update deliverable using AsyncActivity

                    //new AsyncUpdatDeliverable().execute(title, description, date);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("DELIVERABLEVO", thisDeliverableVo);
                    setResult(Activity.RESULT_OK, resultIntent);

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
     *
     * @param view
     */
    public void onCalendarUpdateImageClick(View view){

        final Calendar c = Calendar.getInstance();
        int c_year = c.get(Calendar.YEAR);
        int c_month = c.get(Calendar.MONTH);
        int c_day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(EditDeliverableActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view,
                                          int year, int month, int day) {
                        month = month + 1;
                        TextView textView = (TextView) findViewById(R.id.deliverableDueDateUpdateTextView);
                        textView.setText("" + day + "/" + month + "/" + year);
                    }
                },
                c_year,
                c_month,
                c_day);

        datePickerDialog.show();

    }

    /**
     * updateEditActivityDetailsView
     *
     */
    private void updateEditActivityDetailsView(DeliverableVo deliverableVo){

        //get text views
        EditText deliverableTitleUpdateEditText = (EditText) findViewById(R.id.titleUpdateEditText);
        EditText deliverableDescriptionUpdateEditText = (EditText) findViewById(R.id.descriptionUpdateEditText);
        TextView deliverableDueDateUpdateEditText = (TextView) findViewById(R.id.deliverableDueDateUpdateTextView);

        //set edit text values
        deliverableTitleUpdateEditText.setText(deliverableVo.getTitle());
        deliverableDescriptionUpdateEditText.setText(deliverableVo.getDescription());
        deliverableDueDateUpdateEditText.setText(deliverableVo.getDuedate());
    }


    /**
     * Inner Class that represents the return of background service execution
     *
     * TODO: candidate for refactoring into a generic helper class for use in all async task service execution.
     *
     */
    private class EditDeliverableAsyncResult{

        public static final int SUCCESS = 0;
        public static final int ERROR = 1;
        public static final int DEVICE_NOT_CONNECTED = 2;

        int resultCode;
        DeliverableVo deliverableVo = null;

        EditDeliverableAsyncResult(int resultCode, DeliverableVo deliverableVo){
            this.resultCode = resultCode;
            this.deliverableVo = deliverableVo;
        }

        public int getResultCode() {
            return resultCode;
        }
        public DeliverableVo getDeliverableVo() {
            return deliverableVo;
        }
    }

    /**
     * AsyncEditDeliverable
     *
     * <p/>
     * Uses AsyncTask to update a task away from the main UI thread, and send message to rest server.
     *
     */
    private class AsyncEditDeliverable extends AsyncTask<String, Void, EditDeliverableAsyncResult> {
        Resources res = getResources();
        Context context = getApplicationContext();

        final ProgressDialog progressDialog = new ProgressDialog(EditDeliverableActivity.this,
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
        protected EditDeliverableAsyncResult doInBackground(String... params) {

            //prepare hive service parameters
            String title = params[0];
            String description = params[1];
            String date = params[2];


            EditDeliverableAsyncResult result = null;
            DeliverableVo deliverableVo = null;

            //prepare hive service
            Context context = getApplicationContext();
            //TODO: HiveUpdateDeliverable hiveUpdateDeliverable = new HiveUpdateDeliverable(context);

            try {

                //TODO: deliverableVo = hiveUpdateDeliverable.updateDeliverable(title, description, date);

                if (deliverableVo != null) {

                    //actualize local database with newly updated deliverable
                    DeliverableDAO deliverableDAO = new DeliverableDAO(context);
                    //TODO: deliverableDAO.updateDeliverableVo(deliverableVo); //TODO: catch exception for local inert error

                    //prepare result for previous activity
                    result = new EditDeliverableAsyncResult(EditDeliverableAsyncResult.SUCCESS, deliverableVo);
                }

            } /** catch (DeviceNotConnectedException e){
                result = new EditDeliverableAsyncResult(EditDeliverableAsyncResult.DEVICE_NOT_CONNECTED, null);

            } catch(HiveUnexpectedReturnException e){
                result = new EditDeliverableAsyncResult(EditDeliverableAsyncResult.ERROR, null);

            } */catch(Exception e){
                result = new EditDeliverableAsyncResult(EditDeliverableAsyncResult.ERROR, null);
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
        protected void onPostExecute(EditDeliverableAsyncResult result) {
            Context context = getApplicationContext();
            progressDialog.dismiss();

            if(result.getResultCode() == EditDeliverableAsyncResult.SUCCESS) {

                return;

            } else if (result.getResultCode() == EditDeliverableAsyncResult.DEVICE_NOT_CONNECTED) {

                Resources res = getResources();
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.createDeliverableCoordinatorLayout);
                Snackbar.make(coordinatorLayout, res.getString(R.string.device_not_connect), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            } else if (result.getResultCode() == EditDeliverableAsyncResult.ERROR) {

                Resources res = getResources();
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.createInitiativeCoordinatorLayout);
                Snackbar.make(coordinatorLayout, res.getString(R.string.unexpected_error), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }
}
