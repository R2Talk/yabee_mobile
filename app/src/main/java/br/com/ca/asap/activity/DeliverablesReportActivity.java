package br.com.ca.asap.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import br.com.ca.asap.database.DeliverableDAO;
import br.com.ca.asap.graph.DeliverablesChart;
import br.com.ca.asap.vo.DeliverableVo;
import br.com.ca.shareview.R;

/**
 * DeliverablesReportActivity
 *
 * Show Initiative Deliverables Pie Chart and status report
 *
 * @author Rodrigo Carvalho
 */
public class DeliverablesReportActivity extends AppCompatActivity {

    public final static String EXTRA_INITIATIVE_ID = "INITIATIVE_ID"; //expected value to the activity initialization

    private String initiativeId = null;
    private double totalNum = 0;
    private double onTimeNum = 0;
    private double lateNum = 0;
    private double lastDayNum = 0;

    //TODO: this concept must be reviewed
    private int pendingRequestNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiative_report);

        Intent myIntent = getIntent(); // gets the previously created intent
        initiativeId = myIntent.getStringExtra(EXTRA_INITIATIVE_ID);

        //SET INITIATIVE NAME
        TextView initiativeReportTextView = (TextView) this.findViewById(R.id.initiativeReportTextView);
        initiativeReportTextView.setText(initiativeId);

        //ACTUALIZE REPORT
        generateReportData();

        //SET TOTAL OF WORK ITEMS
        TextView totalValueTextView = (TextView) this.findViewById(R.id.totalValueTextView);
        totalValueTextView.setText(String.valueOf(totalNum));

        //SET TOTAL OF LATE WORK ITEMS
        TextView lateValueTextView = (TextView) this.findViewById(R.id.lateValueTextView);
        lateValueTextView.setText(String.valueOf(lateNum));

        //SET TOTAL OF LAST DAY WORK ITEMS
        TextView lastDayValueTextView = (TextView) this.findViewById(R.id.lastDayValueTextView);
        lastDayValueTextView.setText(String.valueOf(lastDayNum));


        //SET CHART

        DeliverablesChart deliverablesChart = new DeliverablesChart();
        //prepare data set
        double[] values = {onTimeNum, lateNum};
        String names[] = {this.getResources().getString(R.string.onTime), this.getResources().getString(R.string.late)};

        View pieChartView = deliverablesChart.getDeliverablesPieChart(values, names, this);
        //get pie chart view
        LinearLayout chartLinearLayout = (LinearLayout) this.findViewById(R.id.deliverablesGraphLinearLayout);
        chartLinearLayout.addView(pieChartView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initiative_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //
    // Check the number of total of work items and number of late work items
    //
    private void generateReportData(){
        ArrayList<DeliverableVo> items = new ArrayList<>();
        Context context = getApplicationContext();
        List<DeliverableVo> deliverableVoList;

        DeliverableDAO deliverableDAO = new DeliverableDAO(context);
        deliverableVoList = deliverableDAO.selectDeliverablesByInitiativeId(initiativeId);

        //due date
        Date dueDate = null;
        //today
        Date today = new Date();
        //Date formatter
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        //Near
        int nextDays = 7;

        String formatToday;
        String formatDueDate;

        //access initiative list via Iterator
        Iterator iterator = deliverableVoList.iterator();
        while(iterator.hasNext()){

            //count deliverable...
            totalNum++;

            //If late, count one more late work item...
            DeliverableVo deliverableVo = (DeliverableVo) iterator.next();

            //get due date
            try {
                dueDate = ft.parse(deliverableVo.getDuedate());
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

            //if with pending request..
            if (hasPendingRequest(deliverableVo.getIdresponsibleuser())) {
                pendingRequestNum++;
            }
        }

        onTimeNum = totalNum - lateNum;
    }

    //
    // hasPendingRequest
    // Test function
    // TODO : need refactoring for definition of the concept of pending request
    //
    private Boolean hasPendingRequest(String name) {

        switch (name) {
            case ("RODRIGO CARVALHO DOS SANTOS"):
                return true;
        }
        return false;
    }
}
