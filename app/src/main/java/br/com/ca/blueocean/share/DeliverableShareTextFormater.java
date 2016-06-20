package br.com.ca.blueocean.share;

import android.content.Context;

import java.util.Iterator;
import java.util.List;

import br.com.ca.blueocean.database.DeliverableDAO;
import br.com.ca.blueocean.vo.DeliverableVo;
import br.com.ca.shareview.R;

/**
 * DeliverableShareTextFormater
 *
 * Generate text with deliverable status report. Can be used as helper to prepare email/send apps content.
 *
 * @author Rodrigo Carvalho
 */
public class DeliverableShareTextFormater {

    Context context = null;

    /**
     * Constructor
     *
     * @param context
     */
    public DeliverableShareTextFormater(Context context) {
        this.context = context;
    }

    /**
     * prepareDeliverableShareText
     *
     * Prepare and return text report with late deliverables list
     *
     * @return
     */
    public String prepareDeliverableShareText(String deliverableId){

        String text = "";

        DeliverableVo deliverableVo;


        DeliverableDAO deliverableDAO = new DeliverableDAO(context);
        deliverableVo = deliverableDAO.getDeliverableById(deliverableId);

        //
        // Write Header
        //
        text = text + deliverableVo.getTitle();
        text = text + "\n";
        text = text + "\n";

        //
        // Write Activities Details
        //
        if (deliverableVo.getCurrentusername() != null) {text = text + context.getString(R.string.responsible) + " " + deliverableVo.getCurrentusername() + "\n\n";}
        if (deliverableVo.getDuedate() != null) {text = text + context.getString(R.string.date) + " " + deliverableVo.getDuedate() + "\n\n";}
        if (deliverableVo.getDescription() != null) {text = text + deliverableVo.getDescription() + "\n";}

        //
        // Write Footer
        //
        text = text + "\n";
        text = text + context.getString(R.string.deliverableShareFooter);

        return text;
    }
}
