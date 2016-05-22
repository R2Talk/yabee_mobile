package br.com.ca.blueocean.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.com.ca.blueocean.database.DeliverableDAO;
import br.com.ca.blueocean.vo.DeliverableVo;
import br.com.ca.shareview.R;

/**
 * DeliverableUpdateActivity
 *
 * TODO:
 *
 * Show and permits edition and update of deliverable status and rating.
 *
 * @author Rodrigo Carvalho
 */
public class DeliverableUpdateActivity extends AppCompatActivity {

    public final static String EXTRA_DELIVERABLE_ID = "DELIVERABLE_ID"; //expected value to the activity initialization

    private String deliverableId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverable_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //
        //Get parameters from previous activity
        //
        Intent myIntent = getIntent(); // gets the previously created intent
        Bundle extras = myIntent.getExtras();
        deliverableId = extras.getString(EXTRA_DELIVERABLE_ID);

        //use DAO to get deliverable by id
        DeliverableDAO deliverableDAO = new DeliverableDAO(getApplicationContext());
        DeliverableVo deliverableVo = deliverableDAO.getDeliverableById(deliverableId);

        //set details view with deliverableVo values
        initializeActivityDetailsView(deliverableVo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Set activity menu
        //
        getMenuInflater().inflate(R.menu.menu_deliverable_update, menu); // Inflate the menu; this adds items to the action bar if it is present.
        return true;
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
     * initializeActivityDetailsView
     *
     */
    private void initializeActivityDetailsView(DeliverableVo deliverableVo){

        //get views
        TextView deliverableTextView = (TextView) findViewById(R.id.titleTextView);
        TextView deliverableDescriptionView = (TextView) findViewById(R.id.descriptionTextView);
        TextView deliverableDueDateView = (TextView) findViewById(R.id.dueDateTextView);
        TextView deliverableCurrentUserView = (TextView) findViewById(R.id.currentUserTextView);
        TextView deliverableCommentsView = (TextView) findViewById(R.id.commentsTextView);
        TextView deliverablePrioritizedByView = (TextView) findViewById(R.id.prioritizedByTextView);
        TextView deliverablePriorityCommentsView = (TextView) findViewById(R.id.priorityCommentsTextView);

        //set views values
        deliverableTextView.setText(deliverableVo.getTitle());
        deliverableDescriptionView.setText(deliverableVo.getDescription());
        deliverableDueDateView.setText(deliverableVo.getDuedate());
        deliverableCurrentUserView.setText(deliverableVo.getCurrentusername());
        deliverableCommentsView.setText(deliverableVo.getComments());
        if ((deliverableVo.getIsPriority()).equals("NO")){
            deliverablePrioritizedByView.setVisibility(View.VISIBLE);
            deliverablePriorityCommentsView.setVisibility(View.VISIBLE);
        } else{
            deliverablePrioritizedByView.setText(deliverableVo.getPrioritizedBy());
            deliverablePriorityCommentsView.setText(deliverableVo.getPriorityComment());
        }

    }

}
