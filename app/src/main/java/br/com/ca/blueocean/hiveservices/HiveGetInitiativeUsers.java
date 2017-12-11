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
import br.com.ca.blueocean.vo.InitiativeVo;
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
public class HiveGetInitiativeUsers {

    //App context
    Context context;

    /**
     * Constructor
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public HiveGetInitiativeUsers(Context context){
        this.context = context;
    }

    /**
     * getInitiativeUsers
     *
     * @return
     */
    public ArrayList<UserVo> getInitiativeUsers(String initiativeId){
        String serviceReturn;
        List<UserVo> userVoList = null;
        ArrayList<UserVo> userVoArrayList = null;
        Gson gson;

        try {

            //prepare URL
            String urlGetInitiativeUsers = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/getInitiativeUsersByInitiativeId?initiativeId=" + URLEncoder.encode(initiativeId, "UTF-8");

            //execute rest call
            HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);

            serviceReturn = httpServiceRequester.executeHttpGetRequest(urlGetInitiativeUsers);

            //deserialize generic type for List of MessageVo
            gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
            Type userVoListType = new TypeToken<List<UserVo>>(){}.getType(); //this is necessary because we are deserializing a generic class type
            userVoList = gson.fromJson(serviceReturn, userVoListType);

            userVoArrayList = new ArrayList<UserVo>(userVoList);

            /*
            userVoList = new ArrayList<UserVo>();
            //fill userVoArrayList for list adapter initialization
            userVoList.add(new UserVo(1, "Hive01", "test@hotmail.com", "",true));
            userVoList.add(new UserVo(1, "Hive02", "test@hotmail.com", "",true));
             */

        } catch (DeviceNotConnectedException e){
            return null;
        }  catch (Exception e){
            return null;
        }

        return userVoArrayList;
    }
}
