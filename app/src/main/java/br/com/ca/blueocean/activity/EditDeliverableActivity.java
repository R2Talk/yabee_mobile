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
import br.com.ca.blueocean.hiveservices.HiveUpdateDeliverable;
import br.com.ca.blueocean.network.DeviceNotConnectedException;
import br.com.ca.blueocean.util.DatePickerFragment;
import br.com.ca.blueocean.vo.DeliverableVo;
import br.com.ca.shareview.R;

public class EditDeliverableActivity extends AppCompatActivity  {

    //BEWARE: The class define its all constants for identifying data parameters from previous activity
    //BEWARE: Any activty that call this activity via Intent must reference these static constants
    public final static String EXTRA_DELIVERABLE_ID = "DELIVERABLE_ID"; //expected value to the activity initialization
    public final static String EXTRA_DELIVERABLE_TITLE = "DELIVERABLE_TITLE"; //expected value to the activity initialization
    public final static String EXTRA_DELIVERABLE_DESCRIPTION = "DELIVERABLE_DESCRIPTION"; //expected value to the activity initialization
    public final static String EXTRA_DELIVERABLE_DATE = "DELIVERABLE_DATE"; //expected value to the activity initialization

    //class variables representing the deliverable data that's being edited
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

        /* Get parameters from previous activity */
        Intent myIntent = getIntent(); // gets the previously created intent
        Bundle extras = myIntent.getExtras();

        //read parameters sent by the previous activity
        deliverableId = extras.getString(EXTRA_DELIVERABLE_ID);
        title = extras.getString(EXTRA_DELIVERABLE_TITLE);
        description = extras.getString(EXTRA_DELIVERABLE_DESCRIPTION);
        dueDate = extras.getString(EXTRA_DELIVERABLE_DATE);

        /* Initialize edit text fields (title, description, date) */
        //Creates a DeliverableVo representing the original updatable data (read from the previous activity)
        thisDeliverableVo = new DeliverableVo(deliverableId, "", title, description, "", "",dueDate,
                "", "", "", "", "", "", "", "");
        //update activity views
        updateEditActivityDetailsView(thisDeliverableVo);

        /* Set floating action button event handler for update action */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_edit_deliverable);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* get update data from views updated fields) */
                // get views references
                EditText titleUpdateEditText = (EditText) findViewById(R.id.titleUpdateEditText);
                EditText descriptionUpdateEditText = (EditText) findViewById(R.id.descriptionUpdateEditText);
                TextView dueDateUpdateTextView = (TextView) findViewById(R.id.deliverableDueDateUpdateTextView);
                // update thisDeviverableVo
                thisDeliverableVo.setIddeliverable(deliverableId);
                thisDeliverableVo.setTitle(titleUpdateEditText.getText().toString());
                thisDeliverableVo.setDescription(descriptionUpdateEditText.getText().toString());
                thisDeliverableVo.setDuedate(dueDateUpdateTextView.getText().toString());

                /* validade */
                if ((thisDeliverableVo.getTitle()).trim().equals("")|| (thisDeliverableVo.getDuedate()).trim().equals("")){
                    Resources res = getResources();
                    Snackbar.make(view, res.getString(R.string.fields_needed_for_update_deliverable), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {

                    /* update */
                    //update deliverable using AsyncActivity
                    new AsyncUpdateDeliverable().execute(thisDeliverableVo.getIddeliverable(),thisDeliverableVo.getTitle(), thisDeliverableVo.getDescription(), thisDeliverableVo.getDuedate());

                    /* return to the caller Intent */
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("DELIVERABLEVO", thisDeliverableVo);
                    setResult(Activity.RESULT_OK, resultIntent);

                    /* finish activity */
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
                this.finish(); // app icon in action bar clicked; goto parent activity.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * onCalendarClick
     *
     * Creates a Date Picker Dialog and get selected date value
     *
     * BEWARE: This is the model for every needed Date Picker Dialog
     *
     * @param view
     */
    public void onCalendarClick(View view){

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
                        textView.setText("" + year + "-" + month + "-" + day);
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
     * Actualize the view fields with deliverableVo data
     */
    private void updateEditActivityDetailsView(DeliverableVo deliverableVo){

        //get text views reference
        EditText deliverableTitleUpdateEditText = (EditText) findViewById(R.id.titleUpdateEditText);
        EditText deliverableDescriptionUpdateEditText = (EditText) findViewById(R.id.descriptionUpdateEditText);
        TextView deliverableDueDateUpdateEditText = (TextView) findViewById(R.id.deliverableDueDateUpdateTextView);

        //set edit text values
        deliverableTitleUpdateEditText.setText(deliverableVo.getTitle());
        deliverableDescriptionUpdateEditText.setText(deliverableVo.getDescription());
        deliverableDueDateUpdateEditText.setText(deliverableVo.getDuedate());
    }

    /**
     * AsyncUpdateDeliverable
     *
     * <p/>
     * Uses AsyncTask to update a task away from the main UI thread
     * BE WARE: It´s necessary because to send a message to rest server is a network operation and can´t execute in the main UI thread.
     *
     */
    private class AsyncUpdateDeliverable extends AsyncTask<String, Void, Integer> {

        private static final int SUCCESS = 0;
        private static final int ERROR = 1;

        Resources res = getResources();
        Context context = getApplicationContext();

        final ProgressDialog progressDialog = new ProgressDialog(EditDeliverableActivity.this,
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
            /* show a progress dialog */
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
        protected Integer doInBackground(String... params) {

            //result
            Integer result;

            //get async task parameters
            String iddeliverable = params[0];
            String title = params[1];
            String description = params[2];
            String duedate = params[3];
            //prepare hive service parameter
            DeliverableVo updateDeliverableVo = new DeliverableVo(iddeliverable, "", title, description, "", "", duedate, "", "", "", "", "", "", "", "", "");

            try {
                //prepare hive service
                Context context = getApplicationContext();
                HiveUpdateDeliverable hiveUpdateDeliverable = new HiveUpdateDeliverable(context);
                hiveUpdateDeliverable.hiveUpdateDeliverable(updateDeliverableVo);

                //actualize local database with newly updated deliverable
                DeliverableDAO deliverableDAO = new DeliverableDAO(context);
                deliverableDAO.updateDeliverable(updateDeliverableVo);

            } catch(Exception e){
                result = new Integer(ERROR); //generic error
            }

            //return success
            result = new Integer(SUCCESS);
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
        protected void onPostExecute(Integer result) {

            /* dismiss the progress dialog created in onPreExecute */
            try {
                Context context = getApplicationContext();
                progressDialog.dismiss();
            } catch(Exception e) {
                //BEWARE: The lifecycle of the main thread s independet of the AsyncTask lifecycle, so the pregressDialog may no longer exist
            }

            return;
        }
    }
}
