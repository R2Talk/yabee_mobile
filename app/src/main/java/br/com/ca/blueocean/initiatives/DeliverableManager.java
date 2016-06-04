package br.com.ca.blueocean.initiatives;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import br.com.ca.blueocean.database.DeliverableDAO;
import br.com.ca.blueocean.hiveservices.HiveDeleteDeliverableById;
import br.com.ca.blueocean.hiveservices.HiveUnexpectedReturnException;
import br.com.ca.blueocean.network.DeviceNotConnectedException;
import br.com.ca.blueocean.vo.DeliverableVo;

/**
 * DeliverableManager
 *
 * @author Rodrigo Carvalho
 */
public class DeliverableManager {

    Context context = null;

    //
    // Constructor
    //
    public DeliverableManager(Context context) {
        this.context = context;
    }

    /**
     * deleteDeliverable
     *
     * @param deliverableId
     */
    public void deleteDeliverableById(String deliverableId) throws DeviceNotConnectedException, Exception {

        //call HiveService
        HiveDeleteDeliverableById hiveDeleteDeliverableById = new HiveDeleteDeliverableById(context);

        try {
            hiveDeleteDeliverableById.deleteDeliverableById(deliverableId);

            //remove from local data base
            DeliverableDAO deliverableDAO = new DeliverableDAO(context);
            deliverableDAO.deleteDeliverableById(deliverableId);

        } catch (DeviceNotConnectedException e){
            throw e;
        } catch (HiveUnexpectedReturnException e) {
            throw e;
        } catch (Exception e){
            throw e;
        }

    }

}
