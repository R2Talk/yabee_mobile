package br.com.ca.blueocean.share;

import android.content.Context;

import java.util.Iterator;
import java.util.List;

import br.com.ca.blueocean.database.DeliverableDAO;
import br.com.ca.blueocean.vo.DeliverableVo;
import br.com.ca.shareview.R;

/**
 * DeliverablesShareTextFormater
 *
 * Generate text with deliverable status report. Can be used as helper to prepare email content.
 *
 * @author Rodrigo Carvalho
 */
public class DeliverablesShareTextFormater {

    Context context = null;

    /**
     * Constructor
     *
     * @param context
     */
    public DeliverablesShareTextFormater(Context context) {
        this.context = context;
    }

    /**
     * prepareDeliverableShareText
     *
     * Prepare and return text report with late deliverables list
     *
     * @return
     */
    public String getDeliverablesTextReport(String initiativeId, String initiativeTitle){

        String text = "";

        List<DeliverableVo> deliverableVoList;


        DeliverableDAO deliverableDAO = new DeliverableDAO(context);
        deliverableVoList = deliverableDAO.selectDeliverablesByInitiativeId(initiativeId);

        //
        // Write Header
        //
        text = text + context.getString(R.string.emailHeader) + " " + initiativeTitle;
        text = text + "\n";
        text = text + "\n";

        //
        // Write Late Activities
        //
        text = text + context.getString(R.string.late);
        text = text + "\n";
        text = text + "\n";
        Iterator iterator = deliverableVoList.iterator();
        while(iterator.hasNext()){
            DeliverableVo deliverableVo = (DeliverableVo) iterator.next();
            if (deliverableVo.getDeliverable_isLate().equals("true")) {
                text = text + ">>>\n";
                text = text + deliverableVo.getTitle() + "\n\n";
                if (deliverableVo.getCurrentusername() != null) {text = text + context.getString(R.string.responsible) + " " + deliverableVo.getCurrentusername() + "\n";}
                if (deliverableVo.getDuedate() != null) {text = text + context.getString(R.string.date) + " " + deliverableVo.getDuedate() + "\n\n";}
                if (deliverableVo.getDescription() != null) {text = text + deliverableVo.getDescription() + "\n";}
                text = text + "<<<" + "\n\n";
            }
        }

        //
        //Write OnTime Activities
        //
        text = text + context.getString(R.string.onTime);
        text = text + "\n";
        text = text + "\n";
        Iterator iterator2  = deliverableVoList.iterator();
        while(iterator2.hasNext()){
            DeliverableVo deliverableVo = (DeliverableVo) iterator2.next();
            if (!deliverableVo.getDeliverable_isLate().equals("true")) {
                text = text + ">>>\n";
                text = text + deliverableVo.getTitle() + "\n\n";
                if (deliverableVo.getCurrentusername() != null) {text = text + context.getString(R.string.responsible) + " " + deliverableVo.getCurrentusername() + "\n";}
                if (deliverableVo.getDuedate() != null) {text = text + context.getString(R.string.date) + " " + deliverableVo.getDuedate() + "\n\n";}
                if (deliverableVo.getDescription() != null) {text = text + deliverableVo.getDescription() + "\n";}
                text = text + "<<<" + "\n\n";
            }
        }

        //
        // Write Footer
        //
        text = text + "\n";
        text = text + "\n";
        text = text + context.getString(R.string.emailFooter);

        return text;
    }
}
