package br.com.ca.asap.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.com.ca.asap.report.InitiativeReport;
import br.com.ca.asap.vo.InitiativeReportVo;
import br.com.ca.asap.vo.InitiativeVo;
import br.com.ca.shareview.R;

/**
 * InitiativeAdapter
 *
 * @author Rodrigo Carvalho
 */
public class InitiativeAdapter extends ArrayAdapter<InitiativeVo> {

        private final Context context;
        private final ArrayList<InitiativeVo> itemsArrayList;

        public InitiativeAdapter(Context context, ArrayList<InitiativeVo> itemsArrayList) {

            super(context, R.layout.row_initiatives_listview, itemsArrayList);

            this.context = context;
            this.itemsArrayList = itemsArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. Get rowInitiativesView from inflater
            View rowInitiativesView = inflater.inflate(R.layout.row_initiatives_listview, parent, false);

            // 3. Get the two text view from the rowInitiativesView
            TextView titleView = (TextView) rowInitiativesView.findViewById(R.id.titleTextView);
            TextView descriptionView = (TextView) rowInitiativesView.findViewById(R.id.descriptionTextView);
            TextView idView = (TextView) rowInitiativesView.findViewById(R.id.initiativeIdTextView);
            /* TODO removed verify if is to return or delete
            TextView totalView = (TextView) rowInitiativesView.findViewById(R.id.totalNumValueTextView1);
            TextView onTimeView = (TextView) rowInitiativesView.findViewById(R.id.onTimeValueTextView3);
            TextView lateView = (TextView) rowInitiativesView.findViewById(R.id.lateNumValuetextView1);
            */

            /* TODO removed verify if is to return or delete
            InitiativeReport initiativeReport = new InitiativeReport();
            InitiativeReportVo initiativeReportVo = initiativeReport.getInitiativeReportData(context, itemsArrayList.get(position).getInitiativeTitle());
            */

            // 4. Set the text for textView
            titleView.setText(itemsArrayList.get(position).getInitiativeTitle());
            descriptionView.setText(itemsArrayList.get(position).getInitiativeDescription());
            idView.setText(itemsArrayList.get(position).getInitiativeId());
            /* TODO removed verify if is to return or delete
            totalView.setText(String.valueOf(initiativeReportVo.getTotalNum()));
            onTimeView.setText(String.valueOf(initiativeReportVo.getOnTimeNum()));
            lateView.setText(String.valueOf(initiativeReportVo.getLateNum()));
            */

            // 5. return rowInitiativesView
            return rowInitiativesView;
        }
}