package br.com.ca.blueocean.hiveservices;

import android.content.Context;

import com.google.gson.Gson;

import java.net.URLEncoder;

import br.com.ca.blueocean.network.DeviceNotConnectedException;
import br.com.ca.blueocean.network.HttpServiceRequester;
import br.com.ca.blueocean.network.InternetDefaultServer;
import br.com.ca.blueocean.vo.DeliverableVo;

/**
 * Hive
 *
 * This class implements a hive service request, and encapsulates the steps from connection to return.
 *
 * BEWARE: Hive Services uses network connection and must be called from a non UI Thread.
 *
 * @author Rodrigo Carvalho
 */
public class HiveResetDeliverablePriority {

    //App context
    Context context;

    /**
     * Constructor
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public HiveResetDeliverablePriority(Context context){
        this.context = context;
    }

    /**
     * resetDeliverablePriority
     *
     * @return
     */
    public String resetDeliverablePriority(DeliverableVo deliverableVo)throws DeviceNotConnectedException, HiveUnexpectedReturnException, Exception {

        String url;
        String serviceReturn;

        Gson gson = new Gson();
        String jasonDeliverableVo = null;

        jasonDeliverableVo = gson.toJson(deliverableVo);


        try {
            //prepare URL
            url = "http://" +  InternetDefaultServer.getDefaultServer() + "/AsapServer/resetDeliverablePriority?jasonDeliverableVo=" + URLEncoder.encode(jasonDeliverableVo, "UTF-8");

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
