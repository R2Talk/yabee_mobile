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
import br.com.ca.blueocean.vo.InitiativeVo;

/**
 * Hive
 *
 * This class implements a hive service request, and encapsulates the steps from connection to return.
 *
 * BEWARE: Hive Services uses network connection and must be called from a non UI Thread.
 *
 * @author Rodrigo Carvalho
 */
public class HiveCreateInitiative {

    //App context
    Context context;

    /**
     * Constructor
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public HiveCreateInitiative(Context context){
        this.context = context;
    }

    /**
     * createInitiative
     *
     * @return
     */
    public InitiativeVo createInitiative(String title, String description, String userId) throws DeviceNotConnectedException, HiveUnexpectedReturnException, Exception{

        String url = null;
        String serviceReturn = null;
        InitiativeVo initiativeVo = null;
        Gson gson = null;

        try {

            //prepare URL
            url = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/createInitiative?title=" + URLEncoder.encode(title, "UTF-8") + "&" + "description=" + URLEncoder.encode(description, "UTF-8") + "&" + "userId=" + userId;

            //execute rest call
            HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);

            serviceReturn = httpServiceRequester.executeHttpGetRequest(url);

            // if unexpected return throws exception
            if (serviceReturn == null) {
                throw new HiveUnexpectedReturnException();
            }

            //deserialize generic type for InitiativeVo
            gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
            Type initiativeVoType = new TypeToken<InitiativeVo>(){}.getType(); //this is necessary because we are deserializing a generic class type
            initiativeVo = gson.fromJson(serviceReturn, initiativeVoType);

        } catch (DeviceNotConnectedException e){
            throw e;

        } catch (Exception e){
            throw e;
        }

        return initiativeVo;
    }
}
