package br.com.ca.blueocean.hiveservices;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URLEncoder;

import br.com.ca.blueocean.network.DeviceNotConnectedException;
import br.com.ca.blueocean.network.HttpServiceRequester;
import br.com.ca.blueocean.network.InternetDefaultServer;
import br.com.ca.blueocean.vo.MessageVo;

/**
 * Hive
 *
 * This class implements a hive service request, and encapsulates the steps from connection to return.
 *
 * BEWARE: Hive Services uses network connection and must be called from a non UI Thread.
 *
 * @author Rodrigo Carvalho
 */
public class HiveDeleteDeliverableById {

    //App context
    Context context;

    /**
     * Constructor
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public HiveDeleteDeliverableById(Context context){
        this.context = context;
    }

    /**
     * deleteDeliverableById
     *
     * @return
     */
    public String deleteDeliverableById(String deliverableId)throws DeviceNotConnectedException, HiveUnexpectedReturnException, Exception {

        String url;
        String serviceReturn;

        try {
            //prepare URL
            url = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/deleteDeliverable?deliverableId=" + URLEncoder.encode(deliverableId, "UTF-8");

            //execute rest call
            HttpServiceRequester httpServiceRequester = new HttpServiceRequester(context);

            serviceReturn = httpServiceRequester.executeHttpGetRequest(url);

            // if unexpected return throws exception
            if (serviceReturn == null) {
                throw new HiveUnexpectedReturnException();
            }

        } catch (DeviceNotConnectedException e){
            throw e;

        } catch (Exception e){
            throw e;
        }

        return serviceReturn;
    }
}
