package br.com.ca.asap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.ca.asap.adapter.DeliverablesAdapter;
import br.com.ca.asap.vo.DeliverableVo;
import br.com.ca.shareview.R;

/**
 * ShowPrioritizedDeliverablesActivity
 *
 * Show user selected priorities for the day
 *
 * @author Rodrigo Carvalho
 */
public class ShowPrioritizedDeliverablesActivity extends AppCompatActivity {

    public final static String EXTRA_PRIORITIES_FILTER = "PRIORITIES_FILTER"; //expected value that defines if is user priorities (USER) or all priorities (ALL)

    DeliverablesAdapter adapter = null;
    ArrayList<DeliverableVo> deliverablesList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_prioritized_deliverables);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //read intent extra parameter

        //If is to show user priorities
            //1. action bar title
            //setTitle(getString(R.string.userPrioritizedActivityLabel));
            //deliverablesList = getDeliverableArrayList("user");

        //else if is to show all priorities
            //setTitle(getString(R.string.allPrioritizedActivityLabel));
            //deliverablesList = getDeliverableArrayList("user");


        //Initialize List View
        //
        // 1. pass context and data to the custom adapter
        //adapter = new DeliverablesAdapter(this, deliverablesList);
        // 2. Get ListView from activity xml
        //ListView listView = (ListView) findViewById(R.id.prioritized_deliverables_listView);
        // 3. setListAdapter
        //listView.setAdapter(adapter);

        //set on click handler to show deliverable details
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //Intent
                Intent intent = new Intent(ShowPrioritizedDeliverablesActivity.this, DeliverableUpdateActivity.class);

                //Intent Parameter
                TextView deliverableId = (TextView) view.findViewById(R.id.deliverableIdTextView); //view list item is received as a parameter

                Bundle extras = new Bundle();
                extras.putString(DeliverableUpdateActivity.EXTRA_DELIVERABLE_ID, deliverableId.getText().toString());
                intent.putExtras(extras);

                //Start Intent
                startActivity(intent);
            }
        });
        */

    }

    //TODO: inflate menu as in DeliverablesActivity

    //TODO: include menu itens treatment as in DeliverablesActivity

    //TODO: include methos to read prioritized activities as in DeliverablesActivity
}
