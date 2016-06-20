package br.com.ca.blueocean.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.ca.blueocean.hiveservices.HiveSendMessage;
import br.com.ca.blueocean.users.UserManager;
import br.com.ca.blueocean.vo.MessageVo;
import br.com.ca.shareview.R;

/**
 * SendMessageActivity
 *
 * This activity allows editing and sending a text message
 *
 * @author Rodrigo Carvalho
 */
public class SendMessageActivity extends AppCompatActivity {

    /**
     * onCreate
     *
     * Create activity, set the layout and associate a click listener for the button present in the layout.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check for empty message
                EditText messageEditText = (EditText) findViewById(R.id.msgEditText);

                if (messageEditText.getText().toString().equals("")){
                    Resources res = getResources();
                    Snackbar.make(view, res.getString(R.string.empty_message_not_permitted), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {

                    //send message
                    String msgText = (String) (((EditText) findViewById(R.id.msgEditText)).getText()).toString();
                    new AsyncSendMessage().execute(String.valueOf(msgText));

                    setResult(Activity.RESULT_OK);
                    finish();
                }

            }
        });
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

        // Handle presses on the action bar items
        switch (item.getItemId()) {

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * AsyncSendMessage
     *
     * <p/>
     * Uses AsyncTask to create a task away from the main UI thread, and send message to rest server.
     */
    private class AsyncSendMessage extends AsyncTask<String, Void, Integer> {
        Resources res = getResources();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        //possible returned states of network query login
        public final int NOT_CONNECTED = 0;
        public final int SEND_MESSAGE_OK = 1;
        public final int SEND_MESSAGE_ERROR = 2;

        /**
         * onPreExecute
         * <p/>
         * Prepare environment before initiate the background execution.
         * Can be used to change a view element (as a progress bar) indicating that is running a background task.
         */
        @Override
        protected void onPreExecute() {
        }

        /**
         * doInBackgroud
         * <p/>
         * AsyncExecution. Executes in its own thread in background.
         * <p/>
         * If it is not demo user, query a REST service to check the state of the user. Returns true if it is a valid user.
         *
         * @param params
         * @return
         */
        @Override
        protected Integer doInBackground(String... params) { //TODO: check if is appropriate receive initiative and deliverable as parameter as message context

            // variable thats maintains return status for original thread
            int sendMessageStatus = SEND_MESSAGE_OK;

            //UserManager - used to get current user sending the message
            UserManager signManager = new UserManager(getApplicationContext());

            //message sent as parameter for the async class
            String msgText = (String) params[0];

            //prepare message vo
            MessageVo messageVo = new MessageVo();
            messageVo.setText(msgText);
            messageVo.setIdFromUser(signManager.getCurrentUser().getUserId());
            messageVo.setUser_idUser(3); //TODO: read from current message
            messageVo.setInitiative_idInitiative(31); //TODO: read from current initiative
            messageVo.setDeliverable_idDeliverable(99); //TODO: read from current deliverable

            //prepare hive service
            Context context = getApplicationContext();
            HiveSendMessage hiveSendMessage = new HiveSendMessage(context);

            //call give service to send message
            hiveSendMessage.sendMessage(messageVo);

            //return result of background thread execution
            return new Integer(sendMessageStatus);
        }

        /**
         * onPostExecute
         * <p/>
         * Executes in the original thread and receives the result of the background execution.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(Integer result) {
            Context context = getApplicationContext();

            if(result == SEND_MESSAGE_ERROR) {
                String text = res.getString(R.string.send_message_error);
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else if (result == NOT_CONNECTED) { //TODO: actualize according the hive service return or exceptions thrown
                String text = res.getString(R.string.device_not_connect);
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else { //...otherwise just shows message informing that the user is not valid
                //TODO: review error treatment
                /*
                UserManager signManager = new UserManager(getApplicationContext());  //retrieve current user
                UserVo userVo = signManager.getCurrentUser();
                String text = res.getString(R.string.send_message_ok);
                text = text + " -> " + userVo.getName();
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                */
            }
        }
    }
}
