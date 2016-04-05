package br.com.ca.asap.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.ca.asap.hiveservices.HiveGetMessages;
import br.com.ca.asap.vo.MessageVo;
import br.com.ca.shareview.R;

/**
 * SynchronizeMessagesActivity
 *
 * This activity does the synchronization of received messages calling a hive server service.
 * obs: in manifest uses the androis:noHistory attribute.
 */
public class SynchronizeMessagesActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGES = "MESSAGES_ARRAY_LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronize_messages);

        Log.d("Synchronize", "onCreate");

        //start synchronization right after creation basic steps
        doMessagesSynchronize();
    }

    // Do messages synchronization, reading from hive cloud server
    private void doMessagesSynchronize(){

        Log.d("Synchronize", "onCreate");

        new DoAsyncMessagesSynchronize().execute("demo");
    }

    // Uses AsyncTask to create a task away from the main UI thread, and synchronize the data with CA Server.
    //
    private class DoAsyncMessagesSynchronize extends AsyncTask<String, Void, ArrayList<MessageVo>> {
        Resources res = getResources();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        @Override
        protected void onPreExecute() {
            //show progress bar view
            ProgressBar bar = (ProgressBar) findViewById(R.id.progressBarMessagesSynchronizing);
            bar.setVisibility(View.VISIBLE);
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

            Log.d("Synchronize","call HiveGetMessages");
            hiveGetMessages = new HiveGetMessages(context);

            messageVoList = hiveGetMessages.getMessages();

            //access initiative list via Iterator
            Iterator iterator = messageVoList.iterator();
            while(iterator.hasNext()){
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
            Gson gson = new Gson();
            String jsonMessages;

            //hide progress bar view
            ProgressBar bar = (ProgressBar) findViewById(R.id.progressBarMessagesSynchronizing);
            bar.setVisibility(View.GONE);

            //the result is sent as a ArrayList<MessageVo> by doInBackGround,

            //and call ShowMessagesActivity activity
            //serialize generic type for List of MessageVo
            Type messagesListType = new TypeToken<ArrayList<MessageVo>>() {}.getType(); //this is necessary because we are deserializing a generic class type
            jsonMessages = gson.toJson(result, messagesListType);

            Intent intent = new Intent(SynchronizeMessagesActivity.this, ShowMessagesActivity.class);
            intent.addFlags(
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(SynchronizeMessagesActivity.EXTRA_MESSAGES, jsonMessages);

            startActivity(intent);

        }
    }
}
