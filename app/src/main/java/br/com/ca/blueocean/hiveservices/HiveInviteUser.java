package br.com.ca.blueocean.hiveservices;

import android.content.Context;

import com.google.gson.Gson;

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
 * @author Rodrigo Carvalho
 */
public class HiveInviteUser {

    //App context
    Context context;

    //possible returned states of network query login
    public final static int NOT_CONNECTED = 0;
    public final static int SUCCESS = 1;
    public final static int ERROR = 2;

    public final static int INVITE_POSTPONED = 3;
    public final static int UNKNOWN_INITIATIVE = 4;

    /**
     * Constructor
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public HiveInviteUser(Context context){
        this.context = context;
    }

    /**
     * hiveInviteUser
     *
     * @return
     */
    public Integer hiveInviteUser(String email, String initiativeId){

        String url;
        String serviceReturn;
        String jsonMessageVo;
        Gson gson = new Gson();

        try {
            //prepare URL
            //TODO: change to the right method name
            url = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/inviteUser?email=" + URLEncoder.encode(email, "UTF-8")  + "&" + "initiativeId=" + URLEncoder.encode(initiativeId, "UTF-8");;

            //execute rest call
            HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);

            serviceReturn = httpServiceRequester.executeHttpGetRequest(url);

            //TODO: check status return in the string serviceReturn

        } catch (DeviceNotConnectedException e){
            return 0;

        } catch (Exception e){

        }

        return 1; // TODO: retrun constants of
    }
}
