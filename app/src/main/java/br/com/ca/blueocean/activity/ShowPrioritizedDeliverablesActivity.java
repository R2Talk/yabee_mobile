package br.com.ca.blueocean.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.ca.blueocean.adapter.DeliverablesAdapter;
import br.com.ca.blueocean.database.DeliverableDAO;
import br.com.ca.blueocean.users.UserManager;
import br.com.ca.blueocean.vo.DeliverableVo;
import br.com.ca.blueocean.vo.UserVo;
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
    public final static String USER_PRIORITIES = "USER";
    public final static String ALL_PRIORITIES = "ALL";

    String prioritiesFilter = null;
    DeliverablesAdapter adapter = null;
    ArrayList<DeliverableVo> deliverablesList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_prioritized_deliverables);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // left back icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get parameters from previous activity
        //
        Intent myIntent = getIntent(); // gets the previously created intent
        Bundle extras = myIntent.getExtras();
        prioritiesFilter = extras.getString(EXTRA_PRIORITIES_FILTER);

        if (prioritiesFilter.equals(USER_PRIORITIES)) { // get user priorities

            //1. action bar title
            setTitle(getString(R.string.userPrioritizedActivityLabel));

            //2. get current user
            UserManager signManager = new UserManager(getApplicationContext());
            UserVo userVo = signManager.getCurrentUser();

            //3. get current user priorities
            deliverablesList = getDeliverableArrayList(USER_PRIORITIES, userVo.getUserId().toString());

        } else { //else get all priorities

            //1. action bar title
            setTitle(getString(R.string.allPrioritizedActivityLabel));

            //3. get all priorities
            deliverablesList = getDeliverableArrayList(ALL_PRIORITIES, null);

        }

        //Initialize List View
        //
        // 1. pass context and data to the custom adapter
        adapter = new DeliverablesAdapter(this, deliverablesList);
        // 2. Get ListView from activity xml
        ListView listView = (ListView) findViewById(R.id.prioritized_deliverables_listView);
        // 3. setListAdapter
        listView.setAdapter(adapter);

        //set on click handler to show deliverable details

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //Intent
                Intent intent = new Intent(ShowPrioritizedDeliverablesActivity.this, DeliverableDetailsActivity.class);

                //Intent Parameter
                TextView deliverableId = (TextView) view.findViewById(R.id.deliverableIdTextView); //view list item is received as a parameter
                Bundle extras = new Bundle();
                extras.putString(DeliverableDetailsActivity.EXTRA_DELIVERABLE_ID, deliverableId.getText().toString());
                intent.putExtras(extras);

                //Start Intent
                startActivity(intent);
            }
        });

    }

    /**
     * onStart
     *
     * refresh prioritized deliverables list view
     */
    @Override
    protected void onStart() {
        super.onStart();
        refreshPrioritizeDeliverablesListView();
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
     * refreshPrioritizeDeliverablesListView
     *
     */
    private void refreshPrioritizeDeliverablesListView(){
        //reload content
        adapter.clear();
        if (prioritiesFilter.equals(USER_PRIORITIES)) { // get user priorities
            //get current user
            UserManager signManager = new UserManager(getApplicationContext());
            UserVo userVo = signManager.getCurrentUser();
            //get current user priorities
            deliverablesList = getDeliverableArrayList(USER_PRIORITIES, userVo.getUserId().toString());

        } else { //else get all priorities
            //get all priorities
            deliverablesList = getDeliverableArrayList(ALL_PRIORITIES, null);
        }
        adapter.addAll((ArrayList<DeliverableVo>) deliverablesList);
        adapter.notifyDataSetChanged();
    }


    /**
     * getDeliverableArrayList
     *
     * Generate DeliverableVo ArrayList for use with ListView Adapter
     *
     * @param filter
     * @return
     */
    private ArrayList<DeliverableVo> getDeliverableArrayList(String filter, String userId){

        ArrayList<DeliverableVo> deliverableVoArrayList = new ArrayList<>();
        List<DeliverableVo> deliverableVoList = null;

        Context context = getApplicationContext();

        DeliverableDAO deliverableDAO = new DeliverableDAO(context);
        deliverableVoList = deliverableDAO.getPrioritizedDeliverables(filter, userId);

        //access initiative list via Iterator
        Iterator iterator = deliverableVoList.iterator();
        while(iterator.hasNext()){
            DeliverableVo deliverableVo = (DeliverableVo) iterator.next();
            //add into ArrayList
            deliverableVoArrayList.add(deliverableVo);
        }

        return deliverableVoArrayList;
    }
}
