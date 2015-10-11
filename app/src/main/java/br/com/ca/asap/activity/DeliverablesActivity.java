package br.com.ca.asap.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.ca.asap.adapter.DeliverablesAdapter;
import br.com.ca.asap.database.DeliverableDAO;
import br.com.ca.asap.vo.DeliverableVo;
import br.com.ca.shareview.R;

/*
 * Deliverables Activity
 * Show deliverables list associated with a identified Initiative.
 */
public class DeliverablesActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "INITIATIVE_ID";
    String initiativeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverables);

        Intent myIntent = getIntent(); // gets the previously created intent
        initiativeId = myIntent.getStringExtra(InitiativesActivity.EXTRA_MESSAGE);

        TextView initiativeTextView = (TextView) this.findViewById(R.id.initiativeTextView);
        initiativeTextView.setText(initiativeId);

        // 1. pass context and data to the custom adapter
        DeliverablesAdapter adapter = new DeliverablesAdapter(this, generateData(initiativeId));
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

        int id = item.getItemId();

        if (id == R.id.initiative_resume) {

            Intent intent = new Intent(DeliverablesActivity.this, InitiativeReportActivity.class);
            intent.putExtra(EXTRA_MESSAGE, initiativeId);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //
    // generateData
    // Generate DeliverableVo ArrayList for use with ListView Adapter
    //
    private ArrayList<DeliverableVo> generateData(String initiativeId){

        ArrayList<DeliverableVo> items = new ArrayList<>();
        Context context = getApplicationContext();
        List<DeliverableVo> deliverableVoList;

        DeliverableDAO deliverableDAO = new DeliverableDAO(context);
        deliverableVoList = deliverableDAO.selectWorkItemsByInitiativeId(initiativeId);

        //access initiative list via Iterator
        Iterator iterator = deliverableVoList.iterator();
        while(iterator.hasNext()){
            DeliverableVo deliverableVo = (DeliverableVo) iterator.next();
            //add into ArrayList
            items.add(deliverableVo);
        }

        return items;
    }
}
