package br.com.ca.asap.hiveservices;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import br.com.ca.asap.network.DeviceNotConnectedException;
import br.com.ca.asap.network.HttpServiceRequester;
import br.com.ca.asap.network.InternetDefaultServer;
import br.com.ca.asap.vo.MessageVo;

/**
 * HiveSendMessage
 *
 * This class implements a hive service request, and encapsulates the steps from connection to return.
 *
 * @author Rodrigo Carvalho
 */
public class HiveSendMessage {

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
    public HiveSendMessage(Context context){
        this.context = context;
    }

    /**
     * sendMessage
     *
     * @return
     */
    public Boolean sendMessage(MessageVo messageVo){

        String url;
        String serviceReturn;
        String jsonMessageVo;
        Gson gson = new Gson();

        try {
            //prepare URL
            //  convert MessageVo to Jason string format
            //  send as get parameter the message in jason format
            //deserialize generic type for List of MessageVo
            Type messageVoType = new TypeToken<MessageVo>() {}.getType(); //this is necessary because we are deserializing a generic class type
            jsonMessageVo = gson.toJson(messageVo, messageVoType);
            url = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/sendMessage?msg=" + URLEncoder.encode(jsonMessageVo, "UTF-8");

            //execute rest call
            HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);

            serviceReturn = httpServiceRequester.executeHttpGetRequest(url);

            //TODO: check status return in the string serviceReturn

        } catch (DeviceNotConnectedException e){
            Log.d("hiveSendMessage", "device not connected");
            return false;
        } catch (Exception e){
            Log.d("hiveSendMessage",e.getMessage());
        }

        return true;
    }
}
