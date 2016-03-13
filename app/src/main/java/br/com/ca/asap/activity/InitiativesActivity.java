package br.com.ca.asap.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.ca.asap.adapter.InitiativeAdapter;
import br.com.ca.asap.database.InitiativeDAO;
import br.com.ca.asap.email.EmailChannel;
import br.com.ca.asap.vo.InitiativeVo;
import br.com.ca.shareview.R;

import static br.com.ca.shareview.R.*;

/*
 * Initiatives Activity
 * Show Current Initiatives list.
 */
public class InitiativesActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "INITIATIVE_ID";
    public final static String EXTRA_MESSAGE_01 = "INITIATIVES_ARRAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_initiatives);

        // 1. pass context and data to the custom adapter
        InitiativeAdapter adapter = new InitiativeAdapter(this, generateData());
        // 2. Get ListView from activity_main.xml
        ListView listView = (ListView) findViewById(R.id.list);
        // 3. setListAdapter
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                //Intent
                Intent intent = new Intent(InitiativesActivity.this, DeliverablesActivity.class);
                //Intent Parameter
                TextView initiativeTitle = (TextView) view.findViewById(R.id.titleTextView);
                intent.putExtra(EXTRA_MESSAGE, initiativeTitle.getText());
                //Start Intent
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initiatives, menu);

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
                //Intent
                //Intent intent = new Intent(InitiativesActivity.this, SendMessageActivity.class);
                Intent intent = new Intent(InitiativesActivity.this, SynchronizeMessagesActivity.class);
                //Start Intent
                startActivity(intent);



                return true;
            /*
            case R.id.action_settings:
                //call method or action
                return true;
            case id.action_report:
                Intent intent = new Intent(this, InitiativesReportActivity.class);
                //prepare initiatives names array
                String[] initiativesNamesArray = getInitiativeNamesArray();
                //save as extra intent parameter
                intent.putExtra(EXTRA_MESSAGE_01, initiativesNamesArray);
                //start activity
                startActivity(intent);
                return true;
            */
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList<InitiativeVo> generateData(){
        
        ArrayList<InitiativeVo> items = new ArrayList<>();
        Context context = getApplicationContext();
        List<InitiativeVo> initiativeVoList;

        InitiativeDAO initiativeDAO = new InitiativeDAO(context);
        initiativeVoList = initiativeDAO.selectInitiatives();

        //access initiative list via Iterator
        Iterator iterator = initiativeVoList.iterator();
        while(iterator.hasNext()){
            //get InitiativeVo
            InitiativeVo initiativeVo = (InitiativeVo) iterator.next();
            //add into ArrayList
            items.add(initiativeVo);
        }

        return items;
    }

    private String[] getInitiativeNamesArray() {

        Context context = getApplicationContext();
        List<InitiativeVo> initiativeVoList;

        InitiativeDAO initiativeDAO = new InitiativeDAO(context);
        initiativeVoList = initiativeDAO.selectInitiatives();
        String[] initiativesNamesArray;
        List<String> listOfString = new ArrayList<String>();

        //access initiative list via Iterator
        Iterator iterator = initiativeVoList.iterator();
        while(iterator.hasNext()){
            InitiativeVo initiativeVo = (InitiativeVo) iterator.next();
            listOfString.add(initiativeVo.getInitiativeTitle());
        }

        //add into ArrayList
        initiativesNamesArray = listOfString.toArray(new String[listOfString.size()]);

        return initiativesNamesArray;
    }
}
