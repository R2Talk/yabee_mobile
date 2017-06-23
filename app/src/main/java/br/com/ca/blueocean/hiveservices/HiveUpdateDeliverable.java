package br.com.ca.blueocean.hiveservices;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URLEncoder;

import br.com.ca.blueocean.network.DeviceNotConnectedException;
import br.com.ca.blueocean.network.HttpServiceRequester;
import br.com.ca.blueocean.network.InternetDefaultServer;
import br.com.ca.blueocean.vo.DeliverableVo;
import br.com.ca.blueocean.vo.MessageVo;

/**
 * Hive
 *
 * This class implements a hive service request, and encapsulates the steps from connection to return.
 *
 * BEWARE: Hive Services uses network connection and must be called from a non UI Thread.
 *
 * @author Rodrigo Carvalho
 */
public class HiveUpdateDeliverable {

    //App context
    Context context;
    //possible returned states of network query login
    public final int NOT_CONNECTED = 0;
    public final int SUCCESS = 1;
    public final int ERROR = 2;

    /**
     * Constructor
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public HiveUpdateDeliverable(Context context){
        this.context = context;
    }

    /**
     * hiveUpdateDeliverable
     *
     * @return
     */
    public String hiveUpdateDeliverable(DeliverableVo deliverableVo){

        String url="";
        String serviceReturn="";
        String jsonDeliverableVo;
        Gson gson = new Gson();

        try {
            //prepare URL
            //  convert Vo to Jason string format
            //  send as get parameter the message in jason format
            //
            Type deliverableVoType = new TypeToken<DeliverableVo>() {}.getType(); //this is necessary because we are deserializing a generic class type
            jsonDeliverableVo = gson.toJson(deliverableVo, deliverableVoType);

            url = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/updateDeliverable?jasonDeliverableVo=" + URLEncoder.encode(jsonDeliverableVo, "UTF-8");

            //execute rest call
            HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);

            serviceReturn = httpServiceRequester.executeHttpGetRequest(url);

            //TODO: check status return in the string serviceReturn

        } catch (DeviceNotConnectedException e){
            return "error";

        } catch (Exception e){
            return "error";
        }

        return serviceReturn;
    }
}
