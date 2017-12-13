package br.com.ca.blueocean.hiveservices;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import br.com.ca.blueocean.network.DeviceNotConnectedException;
import br.com.ca.blueocean.network.HttpServiceRequester;
import br.com.ca.blueocean.network.InternetDefaultServer;
import br.com.ca.blueocean.vo.UserVo;

/**
 * Hive
 *
 * This class implements a hive service request, and encapsulates the steps from connection to return.
 *
 * BEWARE: Hive Services uses network connection and must be called from a non UI Thread.
 *
 * @auhor Rodrigo Carvalho
 */
public class HiveRemoveUserFromInitiative {

    //App context
    Context context;

    /**
     * Constructor
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public HiveRemoveUserFromInitiative(Context context){
        this.context = context;
    }

    /**
     * removeUserFromInitiative
     *
     * @return
     */
    public void removeUserFromInitiative(String userId, String initiativeId){

        try {

            //prepare URL
            String urlRemoveUserFromInitiative = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/deleteUserFromInitiative?userId=" + URLEncoder.encode(userId, "UTF-8") + "&initiativeId=" + URLEncoder.encode(initiativeId, "UTF-8");

            //execute rest call
            HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);
            httpServiceRequester.executeHttpGetRequest(urlRemoveUserFromInitiative);


        } catch (DeviceNotConnectedException e){
            //TODO: implement axception handling and return error code
            return;
        }  catch (Exception e){
            return;
        }

        return;
    }
}
