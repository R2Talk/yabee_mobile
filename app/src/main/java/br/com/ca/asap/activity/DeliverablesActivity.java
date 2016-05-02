package br.com.ca.asap.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.ca.asap.adapter.DeliverablesAdapter;
import br.com.ca.asap.database.DeliverableDAO;
import br.com.ca.asap.email.DeliverableTextReporter;
import br.com.ca.asap.email.EmailChannel;
import br.com.ca.asap.vo.DeliverableVo;
import br.com.ca.shareview.R;

/**
 * Deliverables Activity
 *
 * Show list of deliverables associated with an identified Initiative.
 *
 * @author Rodrigo Carvalho
 */
public class DeliverablesActivity extends AppCompatActivity {

    public final static String EXTRA_INITIATIVE_ID = "INITIATIVE_ID"; //expected value to the activity initialization
    public final static String EXTRA_INITIATIVE_TITLE = "INITIATIVE_TITLE"; //expected value to the activity initialization

    String initiativeTitle = null;
    String initiativeId = null;

    DeliverablesAdapter adapter = null;

    ArrayList<String> deliverableCodeArrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverables);

        //
        //Get parameters from previous activity
        //
        Intent myIntent = getIntent(); // gets the previously created intent
        Bundle extras = myIntent.getExtras();
        initiativeId = extras.getString(EXTRA_INITIATIVE_ID);
        initiativeTitle = extras.getString(EXTRA_INITIATIVE_TITLE);

        //action bar title
        setTitle(initiativeTitle);

        //Initialize List View
        //
        adapter = new DeliverablesAdapter(this, getDeliverableArrayList(initiativeId));// 1. pass context and data to the custom adapter
        ListView listView = (ListView) findViewById(R.id.deliverables_listView); // 2. Get ListView from activity_main.xml
        listView.setAdapter(adapter); // 3. setListAdapter

        //Register context menu associated with the deliverables list view
        //
        registerForContextMenu(listView);
    }

    /**
     * onCreateContextMenu
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        //menu inflater
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_deliverable, menu);

    }

    /**
     * onContextItemSelected
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item){

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        Intent intent = null;

        View view = null;

        view = getViewByPosition(position);

        switch(item.getItemId()){
            case (R.id.action_show_details):
                Log.d("tag", deliverableCodeArrayList.get(position));
                //Intent
                //Intent intent = new Intent(InitiativesActivity.this, SendMessageActivity.class);
                intent = new Intent(DeliverablesActivity.this, DeliverableUpdateActivity.class);
                //Start Intent
                startActivity(intent);

            case (R.id.action_prioritize):
                Log.d("tag", deliverableCodeArrayList.get(position));
        }

        return true; //super.onContextItemSelected(item);
    }

    /**
     * getViewByPosition
     *
     * @return
     */
    private View getViewByPosition(int position){
        View view = null;

        ListView listView = (ListView) findViewById(R.id.deliverables_listView);
        view = listView.getAdapter().getView(position, null, listView);

        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Set activity menu
        //
        getMenuInflater().inflate(R.menu.menu_deliverables, menu); // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Handle presses on the action bar items
        //
        switch (item.getItemId()) {

            case R.id.action_share:
                String to = null;
                String cc = null;
                String subject = null;
                String emailText = null;

                //prepare share parameters
                //
                to = getString(R.string.emailTo);
                cc = "";
                subject = getString(R.string.emailSubject);
                DeliverableTextReporter deliverableTextReporter = new DeliverableTextReporter(getApplicationContext());
                emailText = deliverableTextReporter.getDeliverablesTextReport(this.initiativeId, this.initiativeTitle);

                EmailChannel emailChannel= new EmailChannel();
                emailChannel.callEmailApp(this, to, cc, subject, emailText);

                return true;

            case R.id.initiative_report:

                Intent intent = new Intent(DeliverablesActivity.this, DeliverablesReportActivity.class);

                Bundle extras = new Bundle();
                extras.putString(DeliverablesActivity.EXTRA_INITIATIVE_ID, this.initiativeId);
                extras.putString(DeliverablesActivity.EXTRA_INITIATIVE_TITLE, this.initiativeTitle);
                intent.putExtras(extras);

                startActivity(intent);

                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * getDeliverableArrayList
     *
     * Generate DeliverableVo ArrayList for use with ListView Adapter
     *
     * @param initiativeId
     * @return
     */
    private ArrayList<DeliverableVo> getDeliverableArrayList(String initiativeId){

        ArrayList<DeliverableVo> deliverableVoArrayList = new ArrayList<>();
        List<DeliverableVo> deliverableVoList;

        Context context = getApplicationContext();

        DeliverableDAO deliverableDAO = new DeliverableDAO(context);
        deliverableVoList = deliverableDAO.selectDeliverablesByInitiativeId(initiativeId);

        //access initiative list via Iterator
        Iterator iterator = deliverableVoList.iterator();
        while(iterator.hasNext()){
            DeliverableVo deliverableVo = (DeliverableVo) iterator.next();
            //add into ArrayList
            deliverableVoArrayList.add(deliverableVo);

            //
            //ADD INTO DATA STRUCTURE (CODE ARRAY LIST) FOR FUTURE CONTEXT MENU EVENT REFERENCE
            //
            deliverableCodeArrayList.add(deliverableVo.getCode());
        }

        return deliverableVoArrayList;
    }
}
