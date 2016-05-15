package br.com.ca.asap.hiveservices;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URLEncoder;

import br.com.ca.asap.network.DeviceNotConnectedException;
import br.com.ca.asap.network.HttpServiceRequester;
import br.com.ca.asap.network.InternetDefaultServer;
import br.com.ca.asap.vo.MessageVo;

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
    public HiveInviteUser(Context context){
        this.context = context;
    }

    /**
     * hiveInviteUser
     *
     * @return
     */
    public Boolean hiveInviteUser(String email, String initiativeId){ //TODO: chnage for the right method name - return parameter mas signalize status of return (eeror, postponed, invited)

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
            return false;

        } catch (Exception e){

        }

        return true;
    }
}
