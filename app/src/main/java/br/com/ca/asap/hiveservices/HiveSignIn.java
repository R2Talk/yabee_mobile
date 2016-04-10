package br.com.ca.asap.hiveservices;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import br.com.ca.asap.network.DeviceNotConnectedException;
import br.com.ca.asap.network.HttpServiceRequester;
import br.com.ca.asap.network.InternetDefaultServer;
import br.com.ca.asap.vo.MessageVo;
import br.com.ca.asap.vo.UserVo;

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
    public UserVo signIn(String name, String pwd){
        String url;
        String serviceReturn;
        UserVo userVo;
        Gson gson;

        Log.d("HiveSignIn", "signIn");

        try {
            //format request URL
            String urlString = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/signin?name=" + URLEncoder.encode(name, "UTF-8") + "&" + "password=" + URLEncoder.encode(pwd, "UTF-8");

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
