package br.com.ca.asap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.ca.asap.adapter.MessagesAdapter;
import br.com.ca.asap.vo.MessageVo;
import br.com.ca.shareview.R;

public class ShowMessagesActivity extends AppCompatActivity {

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
                Snackbar.make(view, "Wait: under construction.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //Reads extra intent parameter with ArraList<MessageVo>
        Intent myIntent = getIntent(); // gets the previously created intent

        jasonMessages = myIntent.getStringExtra(SynchronizeMessagesActivity.EXTRA_MESSAGES);

        //deserialize generic type for List of MessageVo
        Type messagesListType = new TypeToken<List<MessageVo>>() {}.getType(); //this is necessary because we are deserializing a generic class type
        messageVoList = gson.fromJson(jasonMessages, messagesListType);

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
}
