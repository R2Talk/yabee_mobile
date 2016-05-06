package br.com.ca.asap.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import br.com.ca.asap.database.DeliverableDAO;
import br.com.ca.asap.vo.DeliverableVo;
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
     * initializeActivityDetailsView
     *
     */
    private void initializeActivityDetailsView(DeliverableVo deliverableVo){

        //get views
        TextView deliverableCodeView = (TextView) findViewById(R.id.codeTextView);
        TextView deliverableTextView = (TextView) findViewById(R.id.titleTextView);
        TextView deliverableDescriptionView = (TextView) findViewById(R.id.descriptionTextView);
        //TextView deliverableStatusView = (TextView) findViewById(R.id.statusTextView); //TODO: remove for deleted views
        TextView deliverableDueDateView = (TextView) findViewById(R.id.dueDateTextView);
        TextView deliverableValueView = (TextView) findViewById(R.id.valueTextView);
        TextView deliverableCurrentUserView = (TextView) findViewById(R.id.currentUserTextView);
        TextView deliverableCommentsView = (TextView) findViewById(R.id.commentsTextView);
        //TextView deliverablePrioritizedView = (TextView) findViewById(R.id.prioritizedTextView);
        TextView deliverablePrioritizedByView = (TextView) findViewById(R.id.prioritizedByTextView);
        TextView deliverablePriorityCommentsView = (TextView) findViewById(R.id.priorityCommentsTextView);

        //set views values
        deliverableCodeView.setText(deliverableVo.getCode());
        deliverableTextView.setText(deliverableVo.getTitle());
        deliverableDescriptionView.setText(deliverableVo.getDescription());
        //deliverableStatusView.setText(deliverableVo.getStatus());
        deliverableDueDateView.setText(deliverableVo.getDuedate());
        deliverableValueView.setText(deliverableVo.getDeliverableValue());
        deliverableCurrentUserView.setText(deliverableVo.getCurrentusername());
        deliverableCommentsView.setText(deliverableVo.getComments());
        //deliverablePrioritizedView.setText(deliverableVo.getIsPriority());
        if ((deliverableVo.getIsPriority()).equals("NO")){
            deliverablePrioritizedByView.setVisibility(View.VISIBLE);
            deliverablePriorityCommentsView.setVisibility(View.VISIBLE);
        } else{
            deliverablePrioritizedByView.setText(deliverableVo.getPrioritizedBy());
            deliverablePriorityCommentsView.setText(deliverableVo.getPriorityComment());
        }

    }

}
