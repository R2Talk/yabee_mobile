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
 * @author Rodrigo Carvalho
 */
public class HiveFinishDeliverableById {

    //App context
    Context context;

    /**
     * Constructor
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public HiveFinishDeliverableById(Context context){
        this.context = context;
    }

    /**
     * finishDeliverableById
     *
     * @return
     */
    public String finishDeliverableById(String deliverableId)throws DeviceNotConnectedException, HiveUnexpectedReturnException, Exception {

        String url;
        String serviceReturn;

        try {
            //prepare URL
            url = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/finishDeliverable?deliverableId=" + URLEncoder.encode(deliverableId, "UTF-8");

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
