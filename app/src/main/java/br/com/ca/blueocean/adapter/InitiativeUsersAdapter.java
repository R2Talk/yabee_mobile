package br.com.ca.blueocean.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.ca.blueocean.vo.InitiativeVo;
import br.com.ca.blueocean.vo.UserVo;
import br.com.ca.shareview.R;

/**
 * InitiativeUsersAdapter
 *
 * @author Rodrigo Carvalho
 */
public class InitiativeUsersAdapter extends ArrayAdapter<UserVo> {

        private final Context context;
        private final ArrayList<UserVo> itemsArrayList;

        public InitiativeUsersAdapter(Context context, ArrayList<UserVo> itemsArrayList) {

            super(context, R.layout.row_initiative_users_listview, itemsArrayList);

            this.context = context;
            this.itemsArrayList = itemsArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. Get rowInitiativesView from inflater
            View rowInitiativeUsersView = inflater.inflate(R.layout.row_initiative_users_listview, parent, false);

            // 3. Get the views from the rowInitiativesView
            TextView userNameTextView = (TextView) rowInitiativeUsersView.findViewById(R.id.userNameTextView);
            TextView userEmailTextView = (TextView) rowInitiativeUsersView.findViewById(R.id.userEmailTextView);
            ImageView deletePersonImageView = (ImageView) rowInitiativeUsersView.findViewById(R.id.deletePerson_imageView);

            // 4. Set the row views content
            userNameTextView.setText(itemsArrayList.get(position).getName());
            userEmailTextView.setText(itemsArrayList.get(position).getEmail());
            //BEAWARE: Here, an userVo object, representing the initiative user in this row, is saved into ImageView Tag
            //when the image receives a click, the handler can read the View Tag and discover which user is associated with the image clicked
            deletePersonImageView.setTag(itemsArrayList.get(position));
            // 5. return rowInitiativesView
            return rowInitiativeUsersView;
        }
}