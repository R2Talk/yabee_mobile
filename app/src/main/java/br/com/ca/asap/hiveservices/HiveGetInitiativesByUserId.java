package br.com.ca.asap.hiveservices;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.ca.asap.network.DeviceNotConnectedException;
import br.com.ca.asap.network.HttpServiceRequester;
import br.com.ca.asap.network.InternetDefaultServer;
import br.com.ca.asap.vo.InitiativeVo;

/**
 * Hive
 *
 * This class implements a hive service request, and encapsulates the steps from connection to return.
 *
 * BEWARE: Hive Services uses network connection and must be called from a non UI Thread.
 *
 * @auhor Rodrigo Carvalho
 */
public class HiveGetInitiativesByUserId {

    //App context
    Context context;

    /**
     * Constructor
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public HiveGetInitiativesByUserId(Context context){
        this.context = context;
    }

    /**
     * getInitiativesByUserId
     *
     * @return
     */
    public List<InitiativeVo> getInitiativesByUserId(String userId){
        String url;
        String serviceReturn;
        List<InitiativeVo> initiativeVoList = null;
        Gson gson;

        //prepare URL
        String urlInviteUser = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/getInitiativesByUserId"; //TODO: include user id parameter

        //execute rest call
        HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);

        try {
            serviceReturn = httpServiceRequester.executeHttpGetRequest(urlInviteUser);

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
