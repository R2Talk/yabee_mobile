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
import br.com.ca.blueocean.vo.InitiativeVo;

/**
 * Hive
 *
 * This class implements a hive service request, and encapsulates the steps from connection to return.
 *
 * BEWARE: Hive Services uses network connection and must be called from a non UI Thread.
 *
 * @auhor Rodrigo Carvalho
 */
public class HiveGetInitiatives {

    //App context
    Context context;

    /**
     * Constructor
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public HiveGetInitiatives(Context context){
        this.context = context;
    }

    /**
     * getInitiatives
     *
     * @return
     */
    public List<InitiativeVo> getInitiatives(){
        String url;
        String serviceReturn;
        List<InitiativeVo> initiativeVoList = null;
        Gson gson;

        //prepare URL
        String urlGetInitiativesString = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/getInitiatives";

        //execute rest call
        HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);

        try {
            serviceReturn = httpServiceRequester.executeHttpGetRequest(urlGetInitiativesString);

            //deserialize generic type for List of MessageVo
            gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
            Type initiativeListType = new TypeToken<List<InitiativeVo>>(){}.getType(); //this is necessary because we are deserializing a generic class type
            initiativeVoList = gson.fromJson(serviceReturn, initiativeListType);

        } catch (DeviceNotConnectedException e){
            return null;
        }

        return initiativeVoList;
    }
}
