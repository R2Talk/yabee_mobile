package br.com.ca.blueocean.hiveservices;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.ca.blueocean.network.DeviceNotConnectedException;
import br.com.ca.blueocean.network.HttpServiceRequester;
import br.com.ca.blueocean.network.InternetDefaultServer;
import br.com.ca.blueocean.vo.DeliverableVo;

/**
 * Hive
 *
 * This class implements a hive service request, and encapsulates the steps from connection to return.
 *
 * BEWARE: Hive Services uses network connection and must be called from a non UI Thread.
 *
 * Use YaBee Context as parameter
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
    public List<DeliverableVo> getPrioritizedDeliverables(){
        String url;
        String serviceReturn;
        List<DeliverableVo> deliverableVoList = null;
        Gson gson = new Gson();

        try {
            //prepare URL
            String urlGetMsgString = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/getPrioritizedDeliverables";

            //execute rest call
            HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);

            serviceReturn = httpServiceRequester.executeHttpGetRequest(urlGetMsgString);

            //deserialize generic type for List of MessageVo
            gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
            Type deliverableListType = new TypeToken<List<DeliverableVo>>(){}.getType(); //this is necessary because we are deserializing a generic class type
            deliverableVoList = gson.fromJson(serviceReturn, deliverableListType);

        } catch (DeviceNotConnectedException e){
            return null;
        }

        return deliverableVoList;
    }
}
