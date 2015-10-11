package br.com.ca.asap.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.ca.asap.vo.DeliverableVo;
import br.com.ca.shareview.R;

/**
 * DeliverablesAdapter
 */
public class DeliverablesAdapter extends ArrayAdapter<DeliverableVo> {

        private final Context context;
        private final ArrayList<DeliverableVo> itemsArrayList;
        private String isDeliverableLate = "false";

        public DeliverablesAdapter(Context context, ArrayList<DeliverableVo> itemsArrayList) {

            super(context, R.layout.row_deliverables_listview, itemsArrayList);

            this.context = context;
            this.itemsArrayList = itemsArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // 1. Create layout inflater
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. Inflate row view
            View rowDeliverableView = inflater.inflate(R.layout.row_deliverables_listview, parent, false);

            // 3. Get views from inflated row view
            TextView idView = (TextView) rowDeliverableView.findViewById(R.id.deliverable_idTextView);
            TextView titleView = (TextView) rowDeliverableView.findViewById(R.id.deliverable_titleTextView);
            TextView due_dateView = (TextView) rowDeliverableView.findViewById(R.id.deliverable_due_dateTextView);
            TextView responsibleView = (TextView) rowDeliverableView.findViewById(R.id.deliverable_responsibleTextView);
            RatingBar ratingBarView = (RatingBar) rowDeliverableView.findViewById(R.id.deliverable_ratingBar);

            // 4. Set the text for textView
            isDeliverableLate = itemsArrayList.get(position).getDeliverable_isLate();
            if(isDeliverableLate.equals("true")) {
                idView.setBackgroundColor(Color.RED);
                idView.setTextColor(Color.WHITE);
            }
            idView.setText(itemsArrayList.get(position).getDeliverable_id());
            titleView.setText(itemsArrayList.get(position).getDeliverable_title());
            due_dateView.setText(itemsArrayList.get(position).getDeliverable_due_date());
            responsibleView.setText(itemsArrayList.get(position).getDeliverable_responsible());
            ratingBarView.setRating(Float.parseFloat(itemsArrayList.get(position).getDeliverable_rating()));

            // 5. return rowInitiativesView
            return rowDeliverableView;
        }
}