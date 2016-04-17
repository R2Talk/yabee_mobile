package br.com.ca.asap.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.ca.asap.adapter.MessagesAdapter;
import br.com.ca.asap.hiveservices.HiveGetMessages;
import br.com.ca.asap.vo.MessageVo;
import br.com.ca.shareview.R;

public class ShowMessagesActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_showmessages, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_messages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<MessageVo> messageVoList;
        String jasonMessages;
        Gson gson = new Gson();

        //
        // Floating Action Button from support android.library
        //
        // set OnClick listener to call send message activity
        //
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Wait: under construction.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
                //Intent
                //Intent intent = new Intent(InitiativesActivity.this, SendMessageActivity.class);
                Intent intent = new Intent(ShowMessagesActivity.this, SendMessageActivity.class);
                //Start Intent
                startActivity(intent);
            }
        });

        //Reads extra intent parameter with ArraList<MessageVo>
        //Intent myIntent = getIntent(); // gets the previously created intent

        //jasonMessages = myIntent.getStringExtra(SynchronizeMessagesActivity.EXTRA_MESSAGES);

        //deserialize generic type for List of MessageVo
        //Type messagesListType = new TypeToken<List<MessageVo>>() {}.getType(); //this is necessary because we are deserializing a generic class type
        //messageVoList = gson.fromJson(jasonMessages, messagesListType);

        //refresh messages reading from hive server and actualize list view
        new DoAsyncMessagesSynchronize().execute("demo");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_refresh_messsages:
                //refresh messages reading from hive server and actualize list view
                new DoAsyncMessagesSynchronize().execute("demo");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * refreshMessageListView
     *
     * Actualizes the activity List View that shows messages from users.
     *
     * @param messageVoList
     */
    private void refreshMessageListView(ArrayList<MessageVo> messageVoList){
        //
        // 1. pass context and data (ArrayList<MessageVo>) to the custom adapter
        //
        MessagesAdapter adapter = new MessagesAdapter(this, messageVoList);
        //
        // 2. Get ListView from activity_show_messages.xml layout
        //
        ListView listView = (ListView) findViewById(R.id.messages_listView);
        //
        // 3. setListAdapter
        //
        listView.setAdapter(adapter);
    }

    /**
     * DoAsyncMessagesSynchronize
     *
     * Uses AsyncTask to create a task away from the main UI thread, and synchronize the data with CA Server.
     */
    private class DoAsyncMessagesSynchronize extends AsyncTask<String, Void, ArrayList<MessageVo>> {
        Resources res = getResources();
        Context context = getApplicationContext();

        final ProgressDialog progressDialog = new ProgressDialog(ShowMessagesActivity.this,
                R.style.AppTheme_Dark_Dialog);

        @Override
        protected void onPreExecute() {
            //show progress dialog
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(res.getString(R.string.synchronizing));
            progressDialog.show();
        }

        @Override
        protected ArrayList<MessageVo> doInBackground(String... params) {

            ArrayList<MessageVo> items = new ArrayList<>();
            Context context = getApplicationContext();

            HiveGetMessages hiveGetMessages;

            //
            // uses hive services to get all messages from server
            //
            List<MessageVo> messageVoList;

            Log.d("ShowMessagesActivity", "call HiveGetMessages");
            hiveGetMessages = new HiveGetMessages(context);

            messageVoList = hiveGetMessages.getMessages();

            //access initiative list via Iterator
            Iterator iterator = messageVoList.iterator();
            while (iterator.hasNext()) {
                //get InitiativeVo
                MessageVo messageVo = (MessageVo) iterator.next();
                //add into ArrayList
                items.add(messageVo);
            }

            return items;

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<MessageVo> result) {
            //close progress dialog
            progressDialog.dismiss();

            //parameter result is sent as a ArrayList<MessageVo> by doInBackGround,
            //refresh ListView
            refreshMessageListView(result);
        }

    }
}
