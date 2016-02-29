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
import br.com.ca.asap.email.EmailChannel;
import br.com.ca.asap.vo.DeliverableVo;
import br.com.ca.shareview.R;

/*
 * Deliverables Activity
 * Show deliverables list associated with an identified Initiative.
 */
public class DeliverablesActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "INITIATIVE_ID";
    String initiativeId = null;
    DeliverablesAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverables);

        Intent myIntent = getIntent(); // gets the previously created intent
        TextView initiativeTextView = (TextView) this.findViewById(R.id.initiativeTextView);
        initiativeId = myIntent.getStringExtra(InitiativesActivity.EXTRA_MESSAGE);
        initiativeTextView.setText(initiativeId);

        //action bar title
        //getActionBar().setTitle(initiativeId);
        // 1. pass context and data to the custom adapter
        adapter = new DeliverablesAdapter(this, generateData(initiativeId));
        // 2. Get ListView from activity_main.xml
        ListView listView = (ListView) findViewById(R.id.deliverables_listView);
        // 3. setListAdapter
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deliverables, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_notification:
                String to = getString(R.string.emailTo);
                String cc = "";
                String subject = getString(R.string.emailSubject);
                String emailText;

                emailText = prepareReportEmailText();
                EmailChannel emailChannel= new EmailChannel();
                emailChannel.callEmailApp(this, to, cc, subject, emailText);

                return true;
            case R.id.initiative_resume:
                Intent intent = new Intent(DeliverablesActivity.this, InitiativeReportActivity.class);
                intent.putExtra(EXTRA_MESSAGE, initiativeId);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //
    // prepareReportEmailText
    //
    private String prepareReportEmailText(){
        String text = "";
        List<DeliverableVo> deliverableVoList;
        Context context = getApplicationContext();

        DeliverableDAO deliverableDAO = new DeliverableDAO(context);
        deliverableVoList = deliverableDAO.selectWorkItemsByInitiativeId(initiativeId);

        //
        // Write Header
        //
        text = text + getString(R.string.emailHeader) + " " + initiativeId;
        text = text + "\n";
        text = text + "\n";
        //
        // Write Late Activities
        //
        text = text + getString(R.string.late);
        text = text + "\n";
        text = text + "\n";
        Iterator iterator = deliverableVoList.iterator();
        while(iterator.hasNext()){
            DeliverableVo deliverableVo = (DeliverableVo) iterator.next();
            if (deliverableVo.getDeliverable_isLate().equals("true")) {
                text = text + getString(R.string.title) + " " + deliverableVo.getDeliverable_title() + "\n";
                text = text + getString(R.string.responsible) + " " + deliverableVo.getDeliverable_responsible() + "\n";
                text = text + getString(R.string.date) + " " + deliverableVo.getDeliverable_due_date() + "\n\n";
            }
        }
        //
        //Write OnTime Activities
        //
        text = text + getString(R.string.onTime);
        text = text + "\n";
        text = text + "\n";
        Iterator iterator2  = deliverableVoList.iterator();
        while(iterator2.hasNext()){
            DeliverableVo deliverableVo = (DeliverableVo) iterator2.next();
            if (!deliverableVo.getDeliverable_isLate().equals("true")) {
                text = text + getString(R.string.title) + " " + deliverableVo.getDeliverable_title() + "\n";
                text = text + getString(R.string.responsible) + " " + deliverableVo.getDeliverable_responsible() + "\n";
                text = text + getString(R.string.date) + " " + deliverableVo.getDeliverable_due_date() + "\n\n";
            }
        }
        //
        // Write Footer
        //
        text = text + "\n";
        text = text + "\n";
        text = text + getString(R.string.emailFooter);

        return text;
    }


    //
    // generateData
    // Generate DeliverableVo ArrayList for use with ListView Adapter
    //
    private ArrayList<DeliverableVo> generateData(String initiativeId){

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
