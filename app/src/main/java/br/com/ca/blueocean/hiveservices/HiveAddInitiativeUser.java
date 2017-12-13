package br.com.ca.blueocean.hiveservices;

import android.content.Context;

import java.net.URLEncoder;

import br.com.ca.blueocean.network.DeviceNotConnectedException;
import br.com.ca.blueocean.network.HttpServiceRequester;
import br.com.ca.blueocean.network.InternetDefaultServer;

/**
 * Hive
 *
 * This class implements a hive service request, and encapsulates the steps from connection to return.
 *
 * BEWARE: Hive Services uses network connection and must be called from a non UI Thread.
 *
 * @auhor Rodrigo Carvalho
 */
public class HiveAddInitiativeUser {

    //App context
    Context context;

    /**
     * Constructor
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public HiveAddInitiativeUser(Context context){
        this.context = context;
    }

    /**
     * addInitiativeUser
     *
     * @return
     */
    public void addInitiativeUser(String userEmail, String initiativeId){

        try {

            //prepare URL
            String urlAddInitiativeUser = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/addUserToInitiative?userEmail=" + URLEncoder.encode(userEmail, "UTF-8") + "&initiativeId=" + URLEncoder.encode(initiativeId, "UTF-8");

            //execute rest call
            HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);
            httpServiceRequester.executeHttpGetRequest(urlAddInitiativeUser);


        } catch (DeviceNotConnectedException e){
            //TODO: implement axception handling and return error code
            return;
        }  catch (Exception e){
            return;
        }

        return;
    }
}
