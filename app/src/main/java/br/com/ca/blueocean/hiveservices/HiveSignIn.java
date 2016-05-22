package br.com.ca.blueocean.hiveservices;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import br.com.ca.blueocean.network.DeviceNotConnectedException;
import br.com.ca.blueocean.network.HttpServiceRequester;
import br.com.ca.blueocean.network.InternetDefaultServer;
import br.com.ca.blueocean.vo.UserVo;

/**
 * HiveSignIn
 *
 * This class implements a hive service request, and encapsulates the steps from connection to return.
 *
 * Implements sign in service
 *
 * BEWARE: Hive Services uses network connection and must be called from a non UI Thread.
 *
 * @author Rodrigo Carvalho
 */
public class HiveSignIn {

    //App context
    Context context;

    /**
     * HiveSignIn
     *
     * Constructor receives app context object
     *
     * HIVE SERVICE INITIALIZATION
     *
     * @param context
     */
    public HiveSignIn(Context context){
        this.context = context;
    }

    /**
     * signIn
     *
     * HIVE SERVICE URL PREPARATION AND EXECUTION CALL
     *
     * @return
     */
    public UserVo signIn(String email, String pwd){

        UserVo userVo;
        Gson gson;
        String serviceReturn;

        try {
            //format request URL
            String urlString = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/signin?email=" + URLEncoder.encode(email, "UTF-8") + "&" + "password=" + URLEncoder.encode(pwd, "UTF-8");

            //execute rest call
            Log.d("HiveSignIn","call httpServiceRequester");
            HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);
            serviceReturn = httpServiceRequester.executeHttpGetRequest(urlString);

            //deserialize generic type for List of UserVo
            gson = new Gson();
            userVo = gson.fromJson(serviceReturn, UserVo.class);

        } catch (DeviceNotConnectedException e){
            Log.d("HiveSignIn", "device not connected");
            return null;
        } catch (UnsupportedEncodingException e){
            Log.d("HiveSignIn", "UnsupportedEncodingException");
            return null;
        }

        return userVo;
    }
}
