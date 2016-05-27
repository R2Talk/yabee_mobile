package br.com.ca.blueocean.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.com.ca.blueocean.vo.InitiativeVo;
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
            TextView titleTextView = (TextView) rowInitiativesView.findViewById(R.id.titleTextView);
            TextView initiativeIdTextView = (TextView) rowInitiativesView.findViewById(R.id.initiativeIdTextView);

            // 4. Set the text for textView
            titleTextView.setText(itemsArrayList.get(position).getInitiativeTitle());
            initiativeIdTextView.setText(itemsArrayList.get(position).getInitiativeId());

            // 5. return rowInitiativesView
            return rowInitiativesView;
        }
}