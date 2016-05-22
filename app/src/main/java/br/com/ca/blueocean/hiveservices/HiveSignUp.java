package br.com.ca.blueocean.hiveservices;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.net.URLEncoder;

import br.com.ca.blueocean.network.DeviceNotConnectedException;
import br.com.ca.blueocean.network.HttpServiceRequester;
import br.com.ca.blueocean.network.InternetDefaultServer;
import br.com.ca.blueocean.vo.UserVo;

/**
 * HiveSignUp
 *
 * This class implements a hive service request, and encapsulates the steps from connection to return.
 *
 * BEWARE: Hive Services uses network connection and must be called from a non UI Thread.
 *
 * @author Rodrigo Carvalho
 */
public class HiveSignUp {

    //App context
    Context context;
    //possible returned states of network query login
    public final int NOT_CONNECTED = 0;
    public final int SUCCESS = 1;
    public final int ERROR = 2;

    /**
     * hiveSignUp
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public HiveSignUp(Context context){
        this.context = context;
    }

    /**
     * sendMessage
     *
     * @return
     */
    public UserVo signUp(String name, String email, String pwd){

        String url;
        String serviceReturn;
        UserVo userVo;
        Gson gson = new Gson();

        try {
            //format URL
            String urlString = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/signup?name=" + URLEncoder.encode(name, "UTF-8") + "&" + "email=" + URLEncoder.encode(email, "UTF-8") + "&" + "password=" + URLEncoder.encode(pwd, "UTF-8");

            //execute rest call
            HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);

            serviceReturn = httpServiceRequester.executeHttpGetRequest(urlString);

            if(serviceReturn.equals("")){
                Log.d("hiveSignUp", "user already exists");
                return null;
            } else {
                //deserialize json UserVo
                gson = new Gson();
                userVo = gson.fromJson(serviceReturn, UserVo.class);
            }

        } catch (DeviceNotConnectedException e){
            Log.d("hiveSignUp", "device not connected");
            return null;
        } catch (Exception e){
            Log.d("hiveSignUp",e.getMessage());
            return null;
        }

        return userVo;
    }
}
