package br.com.ca.asap.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.ca.asap.vo.DeliverableVo;
import br.com.ca.shareview.R;

/**
 * DeliverablesAdapter
 *
 * Prepare view for each instance of deliverable that must be shown in the ListView
 *
 * @author Rodrigo Carvalho
 */
public class DeliverablesAdapter extends ArrayAdapter<DeliverableVo> {

        private final Context context;
        private final ArrayList<DeliverableVo> itemsArrayList;

        private String isDeliverableLate;

    /**
     * Constructor
     *
     * @param context
     * @param itemsArrayList
     */
        public DeliverablesAdapter(Context context, ArrayList<DeliverableVo> itemsArrayList) {
            super(context, R.layout.row_deliverables_listview, itemsArrayList);
            this.context = context;
            this.itemsArrayList = itemsArrayList;
        }

    /**
     * getView
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
            // 1. Create layout inflater
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. Inflate row view
            View rowDeliverableView = inflater.inflate(R.layout.row_deliverables_listview, parent, false);

            // 3. Get views from inflated row view
            TextView deliverableIdView = (TextView) rowDeliverableView.findViewById(R.id.deliverableIdTextView);
            TextView codeView = (TextView) rowDeliverableView.findViewById(R.id.deliverable_codeTextView);
            TextView titleView = (TextView) rowDeliverableView.findViewById(R.id.deliverable_titleTextView);
            TextView due_dateView = (TextView) rowDeliverableView.findViewById(R.id.deliverable_due_dateTextView);
            TextView responsibleView = (TextView) rowDeliverableView.findViewById(R.id.deliverable_responsibleTextView);
            RatingBar ratingBarView = (RatingBar) rowDeliverableView.findViewById(R.id.deliverable_ratingBar);
            ImageView imageView = (ImageView) rowDeliverableView.findViewById(R.id.alertImageView);
            CardView cardView = (CardView) rowDeliverableView.findViewById(R.id.card_view);

            // 4. Show late status
            isDeliverableLate = itemsArrayList.get(position).getDeliverable_isLate();
            if(isDeliverableLate.equals("true")) {
                //cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.yellow_card_color));
                //set resource for imageview
                imageView.setVisibility(ImageView.VISIBLE);
            } else {
                imageView.setVisibility(ImageView.INVISIBLE);
            }

            // 5. Set the deliverable information in the card child views
            //id
            deliverableIdView.setText(itemsArrayList.get(position).getIddeliverable());
            //code
            codeView.setText(itemsArrayList.get(position).getCode());
            //title
            titleView.setText(itemsArrayList.get(position).getTitle());
            //dur date
            due_dateView.setText(itemsArrayList.get(position).getDuedate());
            // responsible
            responsibleView.setText(itemsArrayList.get(position).getCurrentusername());
            //rating
            ratingBarView.setRating(Float.parseFloat(itemsArrayList.get(position).getRating()));

            // 6. return rowInitiativesView
            return rowDeliverableView;
     }
}