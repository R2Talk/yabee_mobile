package br.com.ca.blueocean.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * NetworkConnectionChecker
 *
 * Implements method for verify device Internet connection status
 *
 * @author Rodrigo Carvalho
 */
public class NetworkConnectionChecker {
    Context context;

    /**
     * NetworkConnectionChecker
     *
     * Constructor receives app context object
     *
     * @param context
     */
    public NetworkConnectionChecker(Context context){
        this.context = context;
    }

    /**
     * deviceIsConnected
     *
     * Verifies if the device is connected with the Internet.
     *
     * This method should be called before trying an Internet request.
     *
     * @return
     */
    public Boolean deviceIsConnected(){

        // get information about the network state using ConnectivityManager
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //check if the device has a valid internet connection
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

}
