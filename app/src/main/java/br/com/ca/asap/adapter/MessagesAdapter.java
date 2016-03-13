package br.com.ca.asap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.ca.asap.vo.MessageVo;
import br.com.ca.shareview.R;

/**
 * MessagesAdapter
 *
 * Adapter used by List View that shows list of messages
 *
 * @author Rodrigo Carvalho
 */
public class MessagesAdapter extends ArrayAdapter<MessageVo> {
    private final Context context;
    private final ArrayList<MessageVo> itemsArrayList;

    /**
     * MessageAdapter
     *
     * Initializes the adapter with ArrayList<MessageVo> received as parameter
     *
     *
     * @param context
     * @param itemsArrayList
     */
    public MessagesAdapter(Context context, ArrayList<MessageVo> itemsArrayList) {

        //
        //initializes the adapter with the row view layout and the array list
        //
        super(context, R.layout.row_messages_listview, itemsArrayList);

        //
        //initialize local variables
        //
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }


    /**
     *
     * getView
     *
     * This method is called from the View that uses the adapter, and returns a prepared view for the correspondent position.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     *
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //
        // 1. Create inflater object
        //
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //
        // 2. Get rowMessagesView layout object using the inflater
        //
        View rowMessagesView = inflater.inflate(R.layout.row_messages_listview, parent, false);

        //
        // 3. Get the message text view object from the rowInitiativesView layout object
        //
        TextView messageView = (TextView) rowMessagesView.findViewById(R.id.messageTextView);

        //
        // 4. Set the text for textView
        //
        messageView.setText(itemsArrayList.get(position).getText());

        //
        // 5. return rowInitiativesView
        //
        return rowMessagesView;
    }
}
