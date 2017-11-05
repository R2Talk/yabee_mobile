package br.com.ca.blueocean.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.ca.blueocean.adapter.InitiativeAdapter;
import br.com.ca.blueocean.database.InitiativeDAO;
import br.com.ca.blueocean.vo.InitiativeVo;
import br.com.ca.shareview.R;

import static br.com.ca.shareview.R.*;

/**
 * Initiatives Activity
 *
 * Show list of Initiatives.
 *
 * @author Rodrigo Carvalho
 */
public class InitiativesActivity extends AppCompatActivity {

    //used for identification of StartActivityForResults request
    public final int CREATE_INITIATIVE_INTENT_CALL = 0;

    //used to call intents with parameters
    Intent intent = null;
    Bundle extras = null;

    //ListView Adapter
    InitiativeAdapter adapter = null;
    ListView listView = null;

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_initiatives);

        // 1. pass context and data to the custom adapter
        adapter = new InitiativeAdapter(this, (ArrayList<InitiativeVo>) getInitiativesList());
        // 2. Get ListView from activity_main.xml
        listView = (ListView) findViewById(R.id.list);
        // 3. setListAdapter
        listView.setAdapter(adapter);

        // 4. set on list item click handler
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                /*
                //Intent
                Intent intent = new Intent(InitiativesActivity.this, DeliverablesActivity.class);
                //Intent Parameter
                TextView initiativeTitle = (TextView) view.findViewById(R.id.titleTextView); //in click event, view of list item is received as a parameter
                TextView initiativeId = (TextView) view.findViewById(R.id.initiativeIdTextView); //in click event, view of list item is received as a parameter

                Bundle extras = new Bundle();
                extras.putString(DeliverablesActivity.EXTRA_INITIATIVE_ID, initiativeId.getText().toString());
                extras.putString(DeliverablesActivity.EXTRA_INITIATIVE_TITLE, initiativeTitle.getText().toString());
                intent.putExtras(extras);
                //Start Intent
                startActivity(intent);
                */

                //Intent
                Intent intent = new Intent(InitiativesActivity.this, ShowDeliverablesActivity.class);
                //Intent Parameter
                TextView initiativeTitle = (TextView) view.findViewById(R.id.titleTextView); //in click event, view of list item is received as a parameter
                TextView initiativeId = (TextView) view.findViewById(R.id.initiativeIdTextView); //in click event, view of list item is received as a parameter

                Bundle extras = new Bundle();
                extras.putString(DeliverablesActivity.EXTRA_INITIATIVE_ID, initiativeId.getText().toString());
                extras.putString(DeliverablesActivity.EXTRA_INITIATIVE_TITLE, initiativeTitle.getText().toString());
                intent.putExtras(extras);
                //Start Intent
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(id.fab_new_initiative);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create initiative activity
                intent = new Intent(InitiativesActivity.this, CreateInitiativeActivity.class);
                //Start Intent for result
                startActivityForResult(intent, CREATE_INITIATIVE_INTENT_CALL);
            }
        });
    }

    /**
     * onCreateOptionsMenu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initiatives, menu);

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent = null;

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case id.action_synchronize_initiatives:
                //Intent
                intent = new Intent(InitiativesActivity.this, SynchronizeInitiativesActivity.class);
                //clear activity stack
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //Start Intent
                startActivity(intent);
                return true;
            /*
            case id.action_show_messages:
                //Intent
                intent = new Intent(InitiativesActivity.this, ShowMessagesActivity.class);
                //Start Intent
                startActivity(intent);
                return true;
            */

            case id.action_show_info:
                //Intent
                intent = new Intent(InitiativesActivity.this, ShowInfoActivity.class);
                //Start Intent
                startActivity(intent);
                return true;

            /*
            case id.action_show_my_priorities:

                //Intent
                //1. Set Intent parameter signaling that the user priorities must be shown
                intent = new Intent(InitiativesActivity.this, ShowPrioritizedDeliverablesActivity.class);
                //intent parameters
                extras = new Bundle();
                extras.putString(ShowPrioritizedDeliverablesActivity.EXTRA_PRIORITIES_FILTER, ShowPrioritizedDeliverablesActivity.USER_PRIORITIES);
                intent.putExtras(extras);
                //2. Start Intent
                startActivity(intent);
                return true;
            */

            case id.action_show_all_priorities:
                //Intent
                //1. Set Intent parameter signaling that all priorities must be shown
                intent = new Intent(InitiativesActivity.this, ShowPrioritizedDeliverablesActivity.class);
                //intent parameters
                extras = new Bundle();
                extras.putString(ShowPrioritizedDeliverablesActivity.EXTRA_PRIORITIES_FILTER, ShowPrioritizedDeliverablesActivity.ALL_PRIORITIES);
                intent.putExtras(extras);
                //2. Start Intent
                startActivity(intent);
                return true;

            case id.action_show_deliverables:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CREATE_INITIATIVE_INTENT_CALL) {

            if(resultCode == RESULT_OK){
                //Successful execution implies in initiative created in the cloud server and actualized in the local database
                //Refresh list view with the actualized local database
                refreshInitiativesListView();;
            }

            if (resultCode == RESULT_CANCELED) {
                //If there's no result
            }
        }
    }//onActivityResult

    /**
     * refreshInitiativesListView
     *
     */
    private void refreshInitiativesListView(){
        //reload content
        adapter.clear();
        adapter.addAll((ArrayList<InitiativeVo>) getInitiativesList());
        adapter.notifyDataSetChanged();
    }

    /**
     * getInitiativesList
     *
     * Prepare list of initiatives to be used in list adapter
     *
     * @return
     */
    private List<InitiativeVo> getInitiativesList(){
        
        //ArrayList<InitiativeVo> initiativeVoArrayList = new ArrayList<>();
        Context context = getApplicationContext();
        List<InitiativeVo> initiativeVoList;
        Intent intent = null;

        InitiativeDAO initiativeDAO = new InitiativeDAO(context);
        initiativeVoList = initiativeDAO.selectInitiatives();

        return initiativeVoList;
    }

}
