package br.com.ca.asap.email;

import android.content.Context;

import java.util.Iterator;
import java.util.List;

import br.com.ca.asap.database.DeliverableDAO;
import br.com.ca.asap.vo.DeliverableVo;
import br.com.ca.shareview.R;

/**
 * DeliverableTextReporter
 *
 * Generate text with deliverable status report. Can be used as helper to prepare email content.
 *
 * @author Rodrigo Carvalho
 */
public class DeliverableTextReporter {

    Context context = null;

    /**
     * Constructor
     *
     * @param context
     */
    public DeliverableTextReporter(Context context) {
        this.context = context;
    }

    /**
     * getLateStatusDeliverablesText
     *
     * Prepare and return text report with late deliverables list
     *
     * @return
     */
    public String getLateStatusDeliverablesText(String initiativeId){

        String text = "";

        List<DeliverableVo> deliverableVoList;


        DeliverableDAO deliverableDAO = new DeliverableDAO(context);
        deliverableVoList = deliverableDAO.selectWorkItemsByInitiativeId(initiativeId);

        //
        // Write Header
        //
        text = text + context.getString(R.string.emailHeader) + " " + initiativeId;
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
                text = text + context.getString(R.string.title) + " " + deliverableVo.getTitle() + "\n";
                text = text + context.getString(R.string.responsible) + " " + deliverableVo.getIdresponsibleuser() + "\n";
                text = text + context.getString(R.string.date) + " " + deliverableVo.getDuedate() + "\n\n";
            }
        }

        //
        //TODO: remove commented code - late activities report only
        //
        //Write OnTime Activities
        //
        /*
        text = text + context.getString(R.string.onTime);
        text = text + "\n";
        text = text + "\n";
        Iterator iterator2  = deliverableVoList.iterator();
        while(iterator2.hasNext()){
            DeliverableVo deliverableVo = (DeliverableVo) iterator2.next();
            if (!deliverableVo.getDeliverable_isLate().equals("true")) {
                text = text + context.getString(R.string.title) + " " + deliverableVo.getTitle() + "\n";
                text = text + context.getString(R.string.responsible) + " " + deliverableVo.getIdresponsibleuser() + "\n";
                text = text + context.getString(R.string.date) + " " + deliverableVo.getDuedate() + "\n\n";
            }
        }
        */

        //
        // Write Footer
        //
        text = text + "\n";
        text = text + "\n";
        text = text + context.getString(R.string.emailFooter);

        return text;
    }
}
