package br.com.ca.asap.report;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import br.com.ca.asap.database.DeliverableDAO;
import br.com.ca.asap.vo.DeliverableVo;
import br.com.ca.asap.vo.InitiativeReportVo;

/**
 * InitiativeReport
 *
 * Query the state of the deliverables of an initiative and returns the summary in an InitiativeReportVo.
 *
 */
public class InitiativeReport {

    /**
     * Constructor
     *
     */
    public InitiativeReport(){
    }

    /**
     * getInitiativeReportData
     *
     * Receives the Context and the id of an initiative for querying the data base and return the summary of the initiative in an InitiativeReportVo.
     *
     * Uses DAO for querying the data base.
     *
     * @param context
     * @param initiativeId
     * @return
     */
    public InitiativeReportVo getInitiativeReportData(Context context, String initiativeId){

        double totalNum = 0;
        double onTimeNum = 0;
        double lateNum = 0;
        double lastDayNum = 0;
        //TODO: this concept must be reviewed
        int withPmoNum = 0;

        ArrayList<DeliverableVo> items = new ArrayList<>();
        List<DeliverableVo> deliverableVoList;
        InitiativeReportVo initiativeReportVo = null;

        DeliverableDAO deliverableDAO = new DeliverableDAO(context);
        deliverableVoList = deliverableDAO.selectWorkItemsByInitiativeId(initiativeId);

        //due date
        Date dueDate = null;
        //today
        Date today = new Date();
        //Date formatter
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        //Near
        int nextDays = 7;

        String formatToday;
        String formatDueDate;

        //access initiative list via Iterator
        Iterator iterator = deliverableVoList.iterator();
        while(iterator.hasNext()){

            //one more work item...
            totalNum++;

            //If late, count one more late work item...
            DeliverableVo deliverableVo = (DeliverableVo) iterator.next();

            //get due date
            try {
                dueDate = ft.parse(deliverableVo.getDeliverable_due_date());
            } catch (ParseException e) {
                Log.d("DeliverableVo", "Unparseable date");
            }

            //if late...
            if (deliverableVo.getDeliverable_isLate().equals("true")) {
                lateNum++;
            }

            formatToday = ft.format(today);
            formatDueDate = ft.format(dueDate);

            //if last day...
            if (formatDueDate.equals(formatToday)){
                lastDayNum++;
            }

            //if with PMO..
            if (isPmo(deliverableVo.getDeliverable_responsible())) {
                withPmoNum++;
            }
        }

        onTimeNum = totalNum - lateNum;

        initiativeReportVo = new InitiativeReportVo(initiativeId, totalNum, onTimeNum, lateNum, lastDayNum);

        return initiativeReportVo;
    }


    /**
     * Check if the responsible for the deliverable is an PMO.
     * TODO: Change of fixed data to the database to query information when they are present in the data model.
     *
     * @param name
     * @return
     */
    private Boolean isPmo(String name) {

        switch (name) {
            case ("RODRIGO CARVALHO DOS SANTOS"):
                return true;
        }
        return false;
    }
}
