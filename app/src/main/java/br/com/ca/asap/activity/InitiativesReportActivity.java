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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import br.com.ca.asap.database.DeliverableDAO;
import br.com.ca.asap.graph.InitiativesChart;
import br.com.ca.asap.vo.DeliverableVo;
import br.com.ca.asap.vo.InitiativeVo;
import br.com.ca.shareview.R;


/*
 * InitiativesReport Activity
 * Show Initiatives status report
 */
public class InitiativesReportActivity extends AppCompatActivity {

    //REPORT DATA
    Integer[] greenValues;
    Integer[] redValues;
    String[] initiativeNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiatives_report);

        Intent myIntent = getIntent(); // gets the previously created intent
        initiativeNames = myIntent.getStringArrayExtra(InitiativesActivity.EXTRA_MESSAGE_01);

        generateReportData();

        InitiativesChart initiativesChart = new InitiativesChart();

        View barChartView = initiativesChart.getInitiativesBarChart(greenValues, redValues, initiativeNames,this);

        //get bar chart view
        LinearLayout chartLinearLayout = (LinearLayout) this.findViewById(R.id.initiativesGraphLinearLayout);
        chartLinearLayout.addView(barChartView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initiatives_report, menu);
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
    // generateReportData
    // For each iniitiative, check for number of deliverables late and on time and set the respectives arrays lists
    //
    private void generateReportData() {
        ArrayList<DeliverableVo> items = new ArrayList<>();
        Context context = getApplicationContext();
        List<String> initiativesList;
        List<DeliverableVo> deliverableVoList;

        //count values
        int greenCount = 0;
        int redCount = 0;

        //values lists
        ArrayList<Integer> greenValuesList = new ArrayList<Integer>();
        ArrayList<Integer> redValuesList = new ArrayList<Integer>();

        //Deliverable DAO
        DeliverableDAO deliverableDAO = new DeliverableDAO(context);

        //Initiatives List
        initiativesList = new ArrayList<String>(Arrays.asList(initiativeNames));
        //access initiative list via Iterator
        Iterator iterator = initiativesList.iterator();

        //for each initiative
        while (iterator.hasNext()) {

            String initiative = (String) iterator.next();

            //read deliverables
            deliverableVoList = deliverableDAO.selectWorkItemsByInitiativeId(initiative);

            //count deliverables according to status

            Iterator iterator2 = deliverableVoList.iterator();
            while (iterator2.hasNext()){
                DeliverableVo deliverableVo = (DeliverableVo) iterator2.next();
                //if is late
                if (deliverableVo.getDeliverable_isLate().equals("true")){
                    redCount++;
                } else {
                    greenCount++;
                }
            }

            greenValuesList.add(greenCount);
            redValuesList.add(redCount);

            greenCount = 0;
            redCount = 0;

        }

        //add into ArrayList
        greenValues = greenValuesList.toArray(new Integer[greenValuesList.size()]);
        redValues = redValuesList.toArray(new Integer[redValuesList.size()]);
    }
}
