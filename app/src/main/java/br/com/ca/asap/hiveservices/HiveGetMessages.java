package br.com.ca.asap.hiveservices;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import br.com.ca.asap.network.DeviceNotConnectedException;
import br.com.ca.asap.network.HttpServiceRequester;
import br.com.ca.asap.network.InternetDefaultServer;
import br.com.ca.asap.vo.MessageVo;

/**
 * hiveGetAllMessages
 *
 * This class implements a hive service request, and encapsulates the steps from connection to return.
 *
 * @auhor Rodrigo Carvalho
 */
public class HiveGetMessages {

    //App context
    Context context;
    //possible returned states of network query login
    public final int NOT_CONNECTED = 0;
    public final int SUCCESS = 1;
    public final int ERROR = 2;

    /**
     * hiveGetAllMessages
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public HiveGetMessages(Context context){
        this.context = context;
    }

    /**
     * getMessages
     *
     * @return
     */
    public List<MessageVo> getMessages(){
        String url;
        String serviceReturn;
        List<MessageVo> messageVoList;
        Gson gson;

        Log.d("HiveGetMessages","getMessages");

        //prepare URL
        String urlGetMsgString = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/getMessages";

        //execute rest call
        HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);

        try {
            Log.d("hiveGetMessages","call httpServiceRequester");
            serviceReturn = httpServiceRequester.executeHttpGetRequest(urlGetMsgString);

            //deserialize generic type for List of MessageVo
            gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
            Type messagesListType = new TypeToken<List<MessageVo>>(){}.getType(); //this is necessary because we are deserializing a generic class type
            messageVoList = gson.fromJson(serviceReturn, messagesListType);

            //access initiative list via Iterator
            Iterator iterator = messageVoList.iterator();
            while(iterator.hasNext()){
                MessageVo messageVo = (MessageVo) iterator.next();
                Log.d("hiveGetAllMessages", messageVo.getText());
            }

        } catch (DeviceNotConnectedException e){
            Log.d("hiveGetMessages", "device not connected");
            return null;
        }

        return messageVoList;
    }
}
