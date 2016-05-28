package br.com.ca.blueocean.hiveservices;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URLEncoder;

import br.com.ca.blueocean.network.DeviceNotConnectedException;
import br.com.ca.blueocean.network.HttpServiceRequester;
import br.com.ca.blueocean.network.InternetDefaultServer;
import br.com.ca.blueocean.vo.DeliverableVo;
import br.com.ca.blueocean.vo.InitiativeVo;
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
public class HiveCreateDeliverable {

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
    public HiveCreateDeliverable(Context context){
        this.context = context;
    }

    /**
     * createDeliverable
     *
     * @return
     */
    public DeliverableVo createDeliverable(String title, String description, String date, String status, String userId, String initiativeId, String isPriority)throws DeviceNotConnectedException, HiveUnexpectedReturnException, Exception{

        String url = null;
        String serviceReturn = null;
        DeliverableVo deliverableVo = null;
        Gson gson = null;

        try {

            //prepare URL
            url = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/createDeliverable?title=" + URLEncoder.encode(title, "UTF-8") + "&" +
                    "description=" + URLEncoder.encode(description, "UTF-8") + "&" +
                    "date=" + URLEncoder.encode(date, "UTF-8") + "&" +
                    "status=" + URLEncoder.encode(status, "UTF-8") + "&" +
                    "userId=" + URLEncoder.encode(userId, "UTF-8") + "&" +
                    "initiativeId=" + URLEncoder.encode(initiativeId, "UTF-8") + "&" +
                    "isPriority=" + URLEncoder.encode(isPriority, "UTF-8");

            //execute rest call
            HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);

            serviceReturn = httpServiceRequester.executeHttpGetRequest(url);

            // if unexpected return throws exception
            if (serviceReturn == null) {
                throw new HiveUnexpectedReturnException();
            }

            //deserialize generic type for InitiativeVo
            gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
            Type deliverableVoType = new TypeToken<DeliverableVo>(){}.getType(); //this is necessary because we are deserializing a generic class type
            deliverableVo = gson.fromJson(serviceReturn, deliverableVoType);

        } catch (DeviceNotConnectedException e){
            throw e;

        } catch (Exception e){
            throw e;
        }

        return deliverableVo;
    }
}
