package br.com.ca.asap.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

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

    String initiativeId = null;
    DeliverablesAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverables);

        //Get parameter initiativeId from previous activity
        //
        Intent myIntent = getIntent(); // gets the previously created intent
        TextView initiativeTextView = (TextView) this.findViewById(R.id.initiativeTextView);
        initiativeId = myIntent.getStringExtra(EXTRA_INITIATIVE_ID);
        initiativeTextView.setText(initiativeId);

        //action bar title
        //getActionBar().setTitle(initiativeId);

        //Initialize List View
        //
        adapter = new DeliverablesAdapter(this, getDeliverableArrayList(initiativeId));// 1. pass context and data to the custom adapter
        ListView listView = (ListView) findViewById(R.id.deliverables_listView); // 2. Get ListView from activity_main.xml
        listView.setAdapter(adapter); // 3. setListAdapter
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

            case R.id.action_send_email:
                String to = null;
                String cc = null;
                String subject = null;
                String emailText = null;

                //prepare email parameters
                //
                to = getString(R.string.emailTo);
                cc = "";
                subject = getString(R.string.emailSubject);
                DeliverableTextReporter deliverableTextReporter = new DeliverableTextReporter(getApplicationContext());
                emailText = deliverableTextReporter.getLateStatusDeliverablesText(this.initiativeId);

                EmailChannel emailChannel= new EmailChannel();
                emailChannel.callEmailApp(this, to, cc, subject, emailText);

                return true;

            case R.id.initiative_report:

                Intent intent = new Intent(DeliverablesActivity.this, DeliverablesReportActivity.class);
                intent.putExtra(DeliverablesReportActivity.EXTRA_INITIATIVE_ID, this.initiativeId);
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
        deliverableVoList = deliverableDAO.selectWorkItemsByInitiativeId(initiativeId);

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
