package br.com.ca.blueocean.hiveservices;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URLEncoder;
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
        String serviceReturn;
        List<InitiativeVo> initiativeVoList = null;
        Gson gson;

        try {
            //prepare URL
            String urlGetInitiativeByUserId = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/getInitiativesByUserId?userId=" + URLEncoder.encode(userId, "UTF-8");

            //execute rest call
            HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);

            serviceReturn = httpServiceRequester.executeHttpGetRequest(urlGetInitiativeByUserId);

            //deserialize generic type for List of MessageVo
            gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
            Type initiativeListType = new TypeToken<List<InitiativeVo>>(){}.getType(); //this is necessary because we are deserializing a generic class type
            initiativeVoList = gson.fromJson(serviceReturn, initiativeListType);

        } catch (DeviceNotConnectedException e){
            return null;
        } catch (Exception e){
            return null;
        }

        return initiativeVoList;
    }
}
