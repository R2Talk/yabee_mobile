package br.com.ca.asap.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.design.widget.Snackbar;
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
import br.com.ca.asap.user.SignManager;
import br.com.ca.asap.vo.InitiativeVo;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_initiatives);

        // 1. pass context and data to the custom adapter
        InitiativeAdapter adapter = new InitiativeAdapter(this, (ArrayList<InitiativeVo>) getInitiativesList());
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
                TextView initiativeTitle = (TextView) view.findViewById(R.id.titleTextView); //view list item is received as a parameter
                TextView initiativeId = (TextView) view.findViewById(R.id.initiativeIdTextView); //view list item is received as a parameter

                Bundle extras = new Bundle();
                extras.putString(DeliverablesActivity.EXTRA_INITIATIVE_ID, initiativeId.getText().toString());
                extras.putString(DeliverablesActivity.EXTRA_INITIATIVE_TITLE, initiativeTitle.getText().toString());
                intent.putExtras(extras);
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


        Toast toast = null;
        Intent intent = null;

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            
            case id.action_show_messages:
                //Intent
                //Intent intent = new Intent(InitiativesActivity.this, SendMessageActivity.class);
                intent = new Intent(InitiativesActivity.this, ShowMessagesActivity.class);
                //Start Intent
                startActivity(intent);
                return true;

            case id.action_synch_initiatives:

                //TODO: call synchronization activity
                toast = Toast.makeText(getApplicationContext(), "SYNC", Toast.LENGTH_SHORT);
                toast.show();

                return true;

            case id.action_signout:
                SignManager signManager = new SignManager(getApplicationContext());
                signManager.signOut();

                //Intent for SignIn activity
                intent = new Intent(InitiativesActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //clear activity stack to return do signin activity
                //Start Intent
                startActivity(intent);

                return true;
            
            default:
                return super.onOptionsItemSelected(item);
        }
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

        InitiativeDAO initiativeDAO = new InitiativeDAO(context);
        initiativeVoList = initiativeDAO.selectInitiatives();

        return initiativeVoList;
    }

}
