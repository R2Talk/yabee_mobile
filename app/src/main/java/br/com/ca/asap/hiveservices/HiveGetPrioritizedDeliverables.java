package br.com.ca.asap.hiveservices;

import android.content.Context;

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
 * Hive
 *
 * This class implements a hive service request, and encapsulates the steps from connection to return.
 *
 * BEWARE: Hive Services uses network connection and must be called from a non UI Thread.
 *
 * @auhor Rodrigo Carvalho
 */
public class HiveGetPrioritizedDeliverables {

    //App context
    Context context;

    /**
     * Constructor
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public HiveGetPrioritizedDeliverables(Context context){
        this.context = context;
    }

    /**
     *
     *
     * @return
     */
    public List<MessageVo> getMessages(){ //TODO: need refactory for the correct method name and return value
        String url;
        String serviceReturn;
        List<MessageVo> messageVoList; //TODO: declare the expected return value
        Gson gson;


        //prepare URL
        //TODO: change for the right controller method and parameters
        String urlGetMsgString = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/getMessages";

        //execute rest call
        HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);

        try {
            serviceReturn = httpServiceRequester.executeHttpGetRequest(urlGetMsgString);

            //deserialize generic type for List of MessageVo
            gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
            //TODO: change for the right spring controller return
            Type messagesListType = new TypeToken<List<MessageVo>>(){}.getType(); //this is necessary because we are deserializing a generic class type
            messageVoList = gson.fromJson(serviceReturn, messagesListType);

            //access initiative list via Iterator
            //TODO: iterate the right type if the service returns a list
            Iterator iterator = messageVoList.iterator();
            while(iterator.hasNext()){
                MessageVo messageVo = (MessageVo) iterator.next();
            }

        } catch (DeviceNotConnectedException e){
            return null;
        }

        //TODO: change for the right return type
        return messageVoList;
    }
}
