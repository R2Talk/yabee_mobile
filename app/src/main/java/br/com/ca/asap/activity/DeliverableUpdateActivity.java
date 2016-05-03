package br.com.ca.asap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    /**
     * initializeActivityDetailsView
     *
     */
    private void initializeActivityDetailsView(DeliverableVo deliverableVo){

        //get views
        TextView deliverableCodeView = (TextView) findViewById(R.id.codeTextView);
        TextView deliverableTitleView = (TextView) findViewById(R.id.titleTextView);
        /*
        TextView titleView = (TextView) findViewById(R.id.);
        TextView statusView = (TextView) findViewById(R.id.);
        TextView due_dateView = (TextView) findViewById(R.id.);
        TextView responsibleView = (TextView) findViewById(R.id.);
        */

        //set views values
        deliverableCodeView.setText(deliverableVo.getCode());
        deliverableTitleView.setText(deliverableVo.getTitle());
    }

}
