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
 * hiveGetFilteredMessages
 *
 * This class implements a hive service request, and encapsulates the steps from connection to return.
 *
 * Requests a message list. It must respect filter parameters encapsulated into MessageFilter class.
 *
 * @auhor Rodrigo Carvalho
 */
public class HiveGetFilteredMessages {

    //App context
    Context context;
    //possible returned states of network query login
    public final int NOT_CONNECTED = 0;
    public final int SUCCESS = 1;
    public final int ERROR = 2;

    /**
     * hiveGetFilteredMessages
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public HiveGetFilteredMessages(Context context){
        this.context = context;
    }

    /**
     * getFilteredMessages
     *
     * @return
     */
    public List<MessageVo> getFilteredMessages(){

        //TODO: Receive MessageFilterVo as parameter

        String url;
        String serviceReturn;
        List<MessageVo> messageVoList;
        Gson gson;

        Log.d("HiveGetFilteredMessages","getFilteredMessages");

        //prepare URL
        //TODO: mount url request passing message filter value object as jason object : ?msg=" + URLEncoder.encode(jsonMessageFilterVo, "UTF-8");
        String urlGetMsgString = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/getFilteredMessages";

        //execute rest call
        HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);

        try {
            Log.d("hiveGetFilteredMessages","call httpServiceRequester");
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
